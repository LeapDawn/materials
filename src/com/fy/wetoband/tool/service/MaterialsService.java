package com.fy.wetoband.tool.service;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.fy.wetoband.tool.commons.ExeclException;
import com.fy.wetoband.tool.commons.ExeclUtil;
import com.fy.wetoband.tool.commons.IDGenerator;
import com.fy.wetoband.tool.commons.ImprotReady;
import com.fy.wetoband.tool.commons.MaterialsExeclField;
import com.fy.wetoband.tool.commons.RepeatException;
import com.fy.wetoband.tool.commons.StringUtil;
import com.fy.wetoband.tool.commons.ToolException;
import com.fy.wetoband.tool.commons.TypeTree;
import com.fy.wetoband.tool.dao.CurrencyDAO;
import com.fy.wetoband.tool.dao.MaterialsDAO;
import com.fy.wetoband.tool.dao.ModelDAO;
import com.fy.wetoband.tool.dao.SpecDAO;
import com.fy.wetoband.tool.dao.TypeDAO;
import com.fy.wetoband.tool.dao.UnitDAO;
import com.fy.wetoband.tool.dto.ErrorMaterialObject;
import com.fy.wetoband.tool.dto.PMaterials;
import com.fy.wetoband.tool.dto.PageModel;
import com.fy.wetoband.tool.model.BdCurrency;
import com.fy.wetoband.tool.model.BdMaterials;
import com.fy.wetoband.tool.model.BdModel;
import com.fy.wetoband.tool.model.BdSpec;
import com.fy.wetoband.tool.model.BdType;
import com.fy.wetoband.tool.model.BdUnit;

public class MaterialsService {

	private Connection conn;
	private MaterialsDAO materialsDAO = new MaterialsDAO();
	private TypeDAO typeDAO = new TypeDAO();
	private ModelDAO modelDAO = new ModelDAO();
	private UnitDAO unitDAO = new UnitDAO();
	private SpecDAO specDAO = new SpecDAO();
	private CurrencyDAO currencyDAO = new CurrencyDAO();
	
	
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public MaterialsService() {
		super();
	}

	public MaterialsService(Connection conn) {
		super();
		this.conn = conn;
	}

	/**
	 * 新增物料信息
	 * 业务流程:1.判断该物料是否已经存在(名称+规格确定唯一物料)
	 * 		  2.对可能需要新增规格,单位,机型等记录进行处理
	 * 		  3.新增物料
	 * @param pma
	 * @return
	 * @throws RepeatException
	 */
	public boolean save(PMaterials pma) throws RepeatException {
		try {
			TypeTree.init(conn);
		} catch (ToolException e2) {
			e2.printStackTrace();
			TypeTree.setHasInited(false);
		}
		IDGenerator.startInitRecord(conn);
		BdMaterials ma = changeToPOJO(pma);
		boolean success = false;
		try {
			Map<String, String> exists = materialsDAO.isExists(conn, ma);
			if (exists.get("materials_id") != null) {
				throw new RepeatException("已存在相同规格与名称的物料");
			}
			conn.setAutoCommit(false);
				if (newFieldHandle(pma, ma)){
					String typeId = ma.getType().getTypeId();
					boolean cp = false;
					if (typeId != null) {
						String topId = TypeTree.getTopTypeID(conn, typeId);
						if (topId != null && "TT0002".equals(topId)) {
							ma.setMaterialsId(IDGenerator.getMaCPID(typeId));
							cp = true;
						}
					}
					if (!cp) {
						ma.setMaterialsId(IDGenerator.getMaID(typeId));
					}
					success = materialsDAO.save(conn, ma);
					conn.commit();
				}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			success = false;
		} catch (ToolException e) {
			e.printStackTrace();
			TypeTree.setHasInited(false);
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return success;
	}
	
	/**
	 * 更新
	 * @param pma
	 * @return
	 * @throws RepeatException
	 */
	public boolean update(PMaterials pma){
		try {
			TypeTree.init(conn);
		} catch (ToolException e2) {
			e2.printStackTrace();
			TypeTree.setHasInited(false);
		}
		IDGenerator.startInitRecord(conn);
		BdMaterials ma = changeToPOJO(pma);
		boolean success = false;
		try {
			conn.setAutoCommit(false);
			newFieldHandle(pma, ma);
			success = materialsDAO.update(conn, ma);
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			success = false;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return success;
	}
	
	/**
	 * 删除
	 * @param maID
	 * @return
	 */
	public boolean delete(String maID){
		boolean success = false;
		String[] ids = maID.split(",");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ids.length; i++) {
			sb.append("'" + ids[i] + "',");
		}
		sb.deleteCharAt(sb.length() - 1);
		try {
			success = materialsDAO.delete(conn, sb.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}
	
	/**
	 * 根据ID查询
	 * @param materialID
	 * @return
	 */
	public PMaterials getMaterialsByID(String materialID) {
		BdMaterials ma = null;
		try {
			ma = materialsDAO.getById(conn, materialID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (ma == null) {
			return null;
		} else {
			return changeToPageModel(ma);
		}
	}
	
	/**
	 * 获取物料列表数据
	 * @param typeID 所属类型
	 * @param key    查询关键字
	 * @param currentPage  页码
	 * @param rows   每页记录数
	 * @param stockState 库存筛选条件
	 * @return
	 */
	public PageModel list(String typeID, String key, int currentPage, int rows, String stockState) {
		try {
			String childIDS = TypeTree.getChildIDS(conn, typeID);
			int total = materialsDAO.count(conn, childIDS, key, stockState);
			PageModel pageModel = new PageModel(total, rows, currentPage);
			List<BdMaterials> list = materialsDAO.list(conn, childIDS, key, pageModel.getCurrentPage(), pageModel.getRows(), stockState);
			List<PMaterials> plist = new ArrayList<PMaterials>();
			for (BdMaterials materials : list) {
				plist.add(changeToPageModel(materials));
			}
			pageModel.setData(plist);
			return pageModel;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ToolException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 从 execl文件中导入物料信息
	 * 1.导入初始化(获取表中已有物料,规格,货币,类别,单位,型号)
	 * 2.筛选execl文件中获取的信息,找出需新增的信息
	 * 3.批量插入数据库
	 * @param file execl文件
	 * @return
	 * @throws ToolException
	 */
	public Map<String, Object> importFromFile(File file) throws ToolException {
		try {
			TypeTree.init(conn);
		} catch (ToolException e2) {
			e2.printStackTrace();
			TypeTree.setHasInited(false);
		}
		IDGenerator.startInitRecord(conn);
		// 读取execl文件
		ExeclUtil execlUtil = new ExeclUtil();
		execlUtil.setModelArray(MaterialsExeclField.getExeclModelArray());
		execlUtil.setNumberArray(MaterialsExeclField.getExeclNumArray());
		try {
			execlUtil.readExecl(file);
		} catch (ExeclException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ToolException("文件读取失败,请重试导入文件");
		}
		
		List<Map<String, Object>> bodyList = execlUtil.getBodyList();
		List<Object> errorList = new ArrayList<Object>();
		// 获取表中已有数据并处理,为导入做准备
		ImprotReady ready = new ImprotReady(conn);
		try {
			ready.init();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("导入预操作初始化失败,请重试导入文件");
		}
		
		// 表中已有的数据
		Map<String, String> currencyMap = ready.getCurrencyMap();
		Map<String, String> modelMap = ready.getModelMap();
		Map<String, String> specMap = ready.getSpecMap();
		Map<String, String> typeMap = ready.getTypeMap();
		Map<String, String> unitMap = ready.getUnitMap();
		Set<String> materialsSet = ready.getMaterialsSet();
		
		// 存放需要新增的数据的容器
		List<BdSpec> newSpec = new ArrayList<BdSpec>();
		List<BdModel> newModel = new ArrayList<BdModel>();
		List<BdCurrency> newCurrency = new ArrayList<BdCurrency>();
		List<BdUnit> newUnit = new ArrayList<BdUnit>();
		List<BdMaterials> newBdMaterials = new ArrayList<BdMaterials>();
		
		// 该execl文件中已经加入newBdMaterials的物料(物料名称+规格)
		Set<String> repeatBdMaterialsFromExecl = new HashSet<String>();
		
		// 对execl表中的数据进行处理
		for (Map<String, Object> map : bodyList) {
			BdMaterials materials = handleExeclMap(errorList, map);
			if (materials == null) {
				continue;
			}
			
			if (materialsSet.contains(materials.getDescription()+materials.getSpec().getDescription())){
				ErrorMaterialObject errorObj = this.changeToErrorObj(map);
				errorObj.setErrorMsg("已存在相同名称和规格的物料");
				errorList.add(errorObj);
				continue;
			}
			
			if (repeatBdMaterialsFromExecl.contains(materials.getDescription()+materials.getSpec().getDescription())) {
				ErrorMaterialObject errorObj = this.changeToErrorObj(map);
				errorObj.setErrorMsg("该execl文件中已经导入相同名称和规格的物料");
				errorList.add(errorObj);
				continue;
			}
			
			if (typeMap.get(materials.getType().getDescription()) == null) {
				ErrorMaterialObject errorObj = this.changeToErrorObj(map);
				errorObj.setErrorMsg("该类别不存在,请在页面上新增该类别后再导入");
				errorList.add(errorObj);
				continue;
			} else {
				BdType type = materials.getType();
				type.setTypeId(typeMap.get(materials.getType().getDescription()));
			}
			
			if (currencyMap.get(materials.getCurrency().getDescription()) == null) {
				BdCurrency currency = materials.getCurrency();
				currency.setCurrencyId(IDGenerator.getCurrencyID());
				newCurrency.add(currency);
				currencyMap.put(currency.getDescription(), currency.getCurrencyId());
			} else {
				BdCurrency currency = materials.getCurrency();
				currency.setCurrencyId(currencyMap.get(materials.getCurrency().getDescription()));
			}
			
			if (modelMap.get(materials.getModel().getDescription()) == null) {
				BdModel model = materials.getModel();
				model.setModelId(IDGenerator.getModelID());
				newModel.add(model);
				modelMap.put(model.getDescription(), model.getModelId());
			} else {
				BdModel model = materials.getModel();
				model.setModelId(modelMap.get(materials.getModel().getDescription()));
			}
			
			if (specMap.get(materials.getSpec().getDescription()) == null) {
				BdSpec spec = materials.getSpec();
				spec.setSpecId(IDGenerator.getSpecID());
				newSpec.add(spec);
				specMap.put(spec.getDescription(), spec.getSpecId());
			} else {
				BdSpec spec = materials.getSpec();
				spec.setSpecId(specMap.get(materials.getSpec().getDescription()));
			}
			
			if (unitMap.get(materials.getUnit().getDescription()) == null) {
				BdUnit unit = materials.getUnit();
				unit.setUnitId(IDGenerator.getUnitID());
				newUnit.add(unit);
				unitMap.put(unit.getDescription(), unit.getUnitId());
			} else {
				BdUnit unit = materials.getUnit();
				unit.setUnitId(unitMap.get(materials.getUnit().getDescription()));
			}
			
			//TODO
			String typeId = materials.getType().getTypeId();
			boolean cp = false;
			if (typeId != null) {
				String topId = TypeTree.getTopTypeID(conn, typeId);
				if (topId != null && "TT0002".equals(topId)) {
					materials.setMaterialsId(IDGenerator.getMaCPID(typeId));
					cp = true;
				}
			}
			if (!cp) {
				materials.setMaterialsId(IDGenerator.getMaID(typeId));
			}
//			materials.setMaterialsId(IDGenerator.getMaID(materials.getType().getTypeId()));
			newBdMaterials.add(materials);
			repeatBdMaterialsFromExecl.add((materials.getDescription()!=null?materials.getDescription():"")
					+ (materials.getSpec()!=null?materials.getSpec().getDescription():""));
		}
		// 批量新增
		List<BdMaterials> insertResult = null;
		try {
			conn.setAutoCommit(false);
			currencyDAO.saveBatch(conn, newCurrency);
			specDAO.saveBatch(conn, newSpec);
			modelDAO.saveBatch(conn, newModel);
			unitDAO.saveBatch(conn, newUnit);
			insertResult = materialsDAO.saveBatch(conn, newBdMaterials);
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			IDGenerator.setHasInited(false);
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new ToolException("导入数据失败,请重试");
		}
		
		
		for (BdMaterials materials : insertResult) {
			PMaterials pma = changeToPageModel(materials);
			pma.setErrorMsg("新增时发生未知错误,请重新导入或者在页面上进行添加");
			errorList.add(pma);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", bodyList.size());
		map.put("error", errorList.size());
		map.put("errorList", errorList);
		return map;
	}
	
	/**
	 * 导出物料数据到execl文件中
	 * @return
	 * @throws ToolException
	 */
	public File ExportToExecl() throws ToolException {
		List<Map<String, Object>> list = null;
		try {
			list = generateExeclMap(materialsDAO.getAll(conn));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ToolException("导出数据失败");
		}
		File file = new File("materials.xls");
		ExeclUtil execlUtil = new ExeclUtil();
		execlUtil.setHeadArray(MaterialsExeclField.getExeclHeadArray());
		execlUtil.setModelArray(MaterialsExeclField.getExeclNumArray());
		execlUtil.setBodyList(list);
		try {
			execlUtil.writeExecl(file, "物料清单");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ToolException("导出数据失败");
		}
		return file;
	}
	
	/**
	 * 将BdMaterials对象集合整理成将要写入execl的map集合
	 * @param materials
	 * @return
	 */
	private List<Map<String, Object>> generateExeclMap(List<BdMaterials> materials){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (BdMaterials ma : materials) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(MaterialsExeclField.MATERIALSID, ma.getMaterialsId());
			map.put(MaterialsExeclField.DESRCIPTION, ma.getDescription());
			map.put(MaterialsExeclField.TYPE, ma.getType().getDescription());
			map.put(MaterialsExeclField.UNIT, ma.getUnit().getDescription());
			map.put(MaterialsExeclField.SPEC, ma.getSpec().getDescription());
			map.put(MaterialsExeclField.MODEL, ma.getModel().getDescription());
			map.put(MaterialsExeclField.SOURCE, ma.getSource());
			map.put(MaterialsExeclField.CURRENTSTOCK, ma.getCurrentStock());
			map.put(MaterialsExeclField.MINSTOCK, ma.getMinStock());
			map.put(MaterialsExeclField.MAXSTOCK, ma.getMaxStock());
			map.put(MaterialsExeclField.SAFESTOCK, ma.getSafeStock());
			map.put(MaterialsExeclField.PRICE, ma.getPrice());
			map.put(MaterialsExeclField.CURRENCY, ma.getCurrency().getDescription());
			map.put(MaterialsExeclField.REMARK, ma.getRemark());
			list.add(map);
		}
		return list;
	}
	
	
	/**
	 * 将从execl中读出的数据整理成BdMaterials对象并进行校验，不合法的存进errorList中
	 * @param errorList 错误列表，将错误的数据存进改集合
	 * @param map 一条列表记录
	 * @return
	 */
	private BdMaterials handleExeclMap(List<Object> errorList, Map<String, Object> map) {
		BdMaterials material = new BdMaterials();
		material.setType(new BdType((String)map.get(MaterialsExeclField.TYPE)));
		material.setUnit(new BdUnit((String)map.get(MaterialsExeclField.UNIT)));
		material.setSpec(new BdSpec((String)map.get(MaterialsExeclField.SPEC)));
		material.setModel(new BdModel((String)map.get(MaterialsExeclField.MODEL)));
		material.setRemark((String)map.get(MaterialsExeclField.REMARK));
		material.setSource((String)map.get(MaterialsExeclField.SOURCE));
		material.setDescription((String)map.get(MaterialsExeclField.DESRCIPTION));
		material.setCurrency(new BdCurrency((String)map.get(MaterialsExeclField.CURRENCY)));
		String description = (String)map.get(MaterialsExeclField.DESRCIPTION);
		String currency = (String)map.get(MaterialsExeclField.CURRENCY);
		String maxStockStr = (String)map.get(MaterialsExeclField.MAXSTOCK);
		String minStockStr = (String)map.get(MaterialsExeclField.MINSTOCK);
		String safeStockStr = (String)map.get(MaterialsExeclField.SAFESTOCK);
		String priceStr = (String)map.get(MaterialsExeclField.PRICE);
		if (!StringUtil.checkNotNull(description)) {
			ErrorMaterialObject errorObj = this.changeToErrorObj(map);
			errorObj.setErrorMsg("物料名称不能为空");
			errorList.add(errorObj);
			return null;
		}
		if (!StringUtil.checkNotNull(maxStockStr)) {
			ErrorMaterialObject errorObj = this.changeToErrorObj(map);
			errorObj.setErrorMsg("最大库存不能为空");
			errorList.add(errorObj);
			return null;
		}
		if (!StringUtil.checkNotNull(minStockStr)) {
			ErrorMaterialObject errorObj = this.changeToErrorObj(map);
			errorObj.setErrorMsg("最低库存不能为空");
			errorList.add(errorObj);
			return null;
		}
		if (!StringUtil.checkNotNull(safeStockStr)) {
			ErrorMaterialObject errorObj = this.changeToErrorObj(map);
			errorObj.setErrorMsg("安全库存不能为空");
			errorList.add(errorObj);
			return null;
		}
		if (!StringUtil.checkNotNull(priceStr)) {
			ErrorMaterialObject errorObj = this.changeToErrorObj(map);
			errorObj.setErrorMsg("参考价格不能为空");
			errorList.add(errorObj);
			return null;
		}
		if (!StringUtil.checkNotNull(currency)) {
			ErrorMaterialObject errorObj = this.changeToErrorObj(map);
			errorObj.setErrorMsg("货币不能为空");
			errorList.add(errorObj);
			return null;
		}
		
		if (!StringUtil.isNum(maxStockStr)) {
			ErrorMaterialObject errorObj = this.changeToErrorObj(map);
			errorObj.setErrorMsg("最高库存" + maxStockStr + "不合法，必须为数字");
			errorList.add(errorObj);
			return null;
		}
		if (!StringUtil.isNum(minStockStr)) {
			ErrorMaterialObject errorObj = this.changeToErrorObj(map);
			errorObj.setErrorMsg("最低库存" + minStockStr + "不合法，必须为数字");
			errorList.add(errorObj);
			return null;
		}
		if (!StringUtil.isNum(safeStockStr)) {
			ErrorMaterialObject errorObj = this.changeToErrorObj(map);
			errorObj.setErrorMsg("安全库存" + safeStockStr + "不合法，必须为数字");
			errorList.add(errorObj);
			return null;
		}
		if (!StringUtil.isNum(priceStr)) {
			ErrorMaterialObject errorObj = this.changeToErrorObj(map);
			errorObj.setErrorMsg("参考价格" + priceStr + "不合法，必须为数字");
			errorList.add(errorObj);
			return null;
		}
		
		material.setDescription(description);
		material.setCurrency(new BdCurrency(currency));
			Double maxStock = Double.valueOf(maxStockStr);
			Double minStock = Double.valueOf(minStockStr);
			Double safeStock = Double.valueOf(safeStockStr);
			Double priceStock = Double.valueOf(priceStr);
			material.setMinStock(minStock);
			material.setMaxStock(maxStock);
			material.setSafeStock(safeStock);
			material.setPrice(priceStock);
			return material;
	}
	
	
	/**
	 * 处理新增规格,型号等信息
	 * @param pma
	 * @param ma
	 * @throws SQLException
	 */
	private boolean newFieldHandle(PMaterials pma, BdMaterials ma) throws SQLException{
		boolean result = true;
		if (pma.getHasNewCurrency()) {
			String currencyID = currencyDAO.isExists(conn, pma.getBdCurrency());
			if (currencyID == null) {
				currencyID = IDGenerator.getCurrencyID();
				result = currencyDAO.save(conn, currencyID, pma.getBdCurrency());
			}
			ma.getCurrency().setCurrencyId(currencyID);
		}
		if (pma.getHasNewModel()) {
			String modelID = modelDAO.isExists(conn, pma.getBdModel());
			if (modelID == null) {
				modelID = IDGenerator.getModelID();
				result = modelDAO.save(conn, modelID, pma.getBdModel());
			}
			ma.getModel().setModelId(modelID);
		}
		if (pma.getHasNewType()) {
			String TypeID = typeDAO.isExists(conn, pma.getBdType());
			if (TypeID == null) {
				TypeID = IDGenerator.getTypeID();
				result = typeDAO.save(conn, TypeID, pma.getBdType(), pma.getSuperType());
				if (result) {
					BdType type = new BdType(TypeID, new BdType(pma.getBdType(), null), null, 0);
					TypeTree.setHasInited(false);
				}
			}
			ma.getType().setTypeId(TypeID);
		}
		if (pma.getHasNewUnit()) {
			String unitID = unitDAO.isExists(conn, pma.getBdUnit());
			if (unitID == null) {
				unitID = IDGenerator.getUnitID();
				result = unitDAO.save(conn, unitID, pma.getBdUnit());
			}
			ma.getUnit().setUnitId(unitID);
		}
		String specID = specDAO.isExists(conn, pma.getBdSpec());
		if (specID == null) {
			specID = IDGenerator.getSpecID();
			result = specDAO.save(conn, specID, pma.getBdSpec());
		}
		ma.getSpec().setSpecId(specID);
		return result;
	}
	
	private BdMaterials changeToPOJO(PMaterials pma) {
		BdMaterials ma = new BdMaterials();
		BeanUtils.copyProperties(pma, ma);
		ma.setType(new BdType(pma.getBdType(), null));
		ma.setCurrency(new BdCurrency(pma.getBdCurrency(), null));
		ma.setSpec(new BdSpec(pma.getBdSpec(), null));
		ma.setModel(new BdModel(pma.getBdModel(), null));
		ma.setUnit(new BdUnit(pma.getBdUnit(), null));
		return ma;
	}
	
	private PMaterials changeToPageModel(BdMaterials ma) {
		PMaterials pma = new PMaterials();
		BeanUtils.copyProperties(ma, pma);
		pma.setBdCurrency(ma.getCurrency().getDescription());
		pma.setBdModel(ma.getModel().getDescription());
		pma.setBdSpec(ma.getSpec().getDescription());
		pma.setBdType(ma.getType().getDescription());
		pma.setBdUnit(ma.getUnit().getDescription());
		return pma;
	}
	
	private ErrorMaterialObject changeToErrorObj(Map<String, Object> map) {
		ErrorMaterialObject errorObj = new ErrorMaterialObject();
		
		errorObj.setBdType((String)map.get(MaterialsExeclField.TYPE));
		errorObj.setBdUnit((String)map.get(MaterialsExeclField.UNIT));
		errorObj.setBdSpec((String)map.get(MaterialsExeclField.SPEC));
		errorObj.setBdModel((String)map.get(MaterialsExeclField.MODEL));
		errorObj.setRemark((String)map.get(MaterialsExeclField.REMARK));
		errorObj.setSource((String)map.get(MaterialsExeclField.SOURCE));
		errorObj.setDescription((String)map.get(MaterialsExeclField.DESRCIPTION));
		errorObj.setBdCurrency((String)map.get(MaterialsExeclField.CURRENCY));
		errorObj.setDescription((String)map.get(MaterialsExeclField.DESRCIPTION)); 
		errorObj.setMaxStock((String)map.get(MaterialsExeclField.MAXSTOCK));
		errorObj.setMinStock((String)map.get(MaterialsExeclField.MINSTOCK));
		errorObj.setSafeStock((String)map.get(MaterialsExeclField.SAFESTOCK));
		errorObj.setPrice((String)map.get(MaterialsExeclField.PRICE));
		return errorObj;
	}
}
