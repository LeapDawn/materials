package com.fy.wetoband.tool.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fy.wetoband.tool.commons.StringUtil;
import com.fy.wetoband.tool.model.BdCurrency;
import com.fy.wetoband.tool.model.BdMaterials;
import com.fy.wetoband.tool.model.BdModel;
import com.fy.wetoband.tool.model.BdSpec;
import com.fy.wetoband.tool.model.BdType;
import com.fy.wetoband.tool.model.BdUnit;

public class MaterialsDAO {
	
//	// 新增新物料,insert不是通过ID,而是通过description
//	public boolean saveByNames(Connection conn, BdMaterials materials) throws SQLException {
//		String sql = "insert into bd_materials(materials_id,description,type_id,unit_id,spec_id,"
//				+ "model_id,currency_id,source,maxstock,minstock,safestock,price,remark,isvalid) "
//				+ "values("
//				+ "'" + materials.getMaterialsId() + "',"
//				+ "'" + materials.getDescription() + "',"
//				+ "(SELECT t.type_id from bd_type t where t.description like '" + materials.getType().getDescription() + "'),"
//				+ "(SELECT u.unit_id from bd_unit u where u.description like '" + materials.getUnit().getDescription() + "'),"
//				+ "(SELECT s.spec_id from bd_spec s where s.description like '" + materials.getSpec().getDescription() + "'),"
//				+ "(SELECT m.model_id from bd_model m where m.description like '" + materials.getModel().getDescription() + "'),"
//				+ "(SELECT c.currency_id from bd_currency c where c.description like '" + materials.getCurrency().getDescription() + "'),"
//				+ "'" + materials.getSource() + "',"
//				+ materials.getMaxStock() + ","
//				+ materials.getMinStock() + ","
//				+ materials.getSafeStock() + ","
//				+ materials.getPrice() + ","
//				+ "'" + (materials.getRemark()!=null?materials.getRemark():"") + "'," 
//				+ "1);";
//		PreparedStatement pt = conn.prepareStatement(sql);
//		int rs = pt.executeUpdate();
//		if (pt != null) {
//			pt.close();
//		}
//		if (rs == 0) {
//			return false;
//		}
//		return true;
//	}
	
//	public Map<String, String> checkProperties(Connection conn, Map<String, String> map) throws SQLException {
//		String sql = "select "
//			+ " (select distinct currency_id from bd_currency where description like ?) as curreney_id,"
//			+ " (select distinct unit_id from bd_unit where description like ?) as unit_id,"
//			+ " (select distinct type_id from bd_type where description like ? and isvalid = 1) as type_id,"
//			+ " (select distinct spec_id from bd_spec where description like ?) as spec_id,"
//			+ " (select distinct model_id from bd_model where description like ?) as model_id,"
//			+ " (select distinct ma.materials_id from bd_materials ma "
//			+ " left join bd_spec sp on ma.spec_id=sp.spec_id "
//			+ " where ma.description like ? and ma.description like ?) as materials_id, "
//			+ " (select max(mid(m.materials_id, 7)) from bd_materials m) as makey";
//		PreparedStatement pt = conn.prepareStatement(sql);
//		pt.setString(1, map.get("curreney"));
//		pt.setString(2, map.get("unit"));
//		pt.setString(3, map.get("type"));
//		pt.setString(4, map.get("spec"));
//		pt.setString(5, map.get("model"));
//		pt.setString(6, map.get("description"));
//		pt.setString(7, map.get("spec"));
//		ResultSet rs = pt.executeQuery();
//		Map<String, String> resultMap = new HashMap<String, String>();
//		if(rs.next()) {
//			resultMap.put("curreney_id",rs.getString("curreney_id"));
//			resultMap.put("unit_id",rs.getString("unit_id"));
//			resultMap.put("type_id",rs.getString("type_id"));
//			resultMap.put("spec_id",rs.getString("spec_id"));
//			resultMap.put("model_id",rs.getString("model_id"));
//			resultMap.put("materials_id",rs.getString("materials_id"));
//			resultMap.put("maKey", rs.getString("makey"));
//		}
//		if (rs != null) {
//			rs.close();
//		}
//		if (pt != null) {
//			rs.close();
//		}
//		
//		return resultMap;
//	}
	
	public boolean save(Connection conn, BdMaterials materials) throws SQLException {
		String sql = "insert into bd_materials(materials_id,description,type_id,unit_id,spec_id,"
				+ "model_id,currency_id,source,maxstock,minstock,safestock,price,remark,isvalid) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,1)";
		PreparedStatement pt = conn.prepareStatement(sql);
		if (StringUtil.checkNotNull(materials.getMaterialsId())) {
			pt.setString(1, materials.getMaterialsId());
		} 
		if (StringUtil.checkNotNull(materials.getDescription())) {
			pt.setString(2, materials.getDescription());
		}
		if (StringUtil.checkNotNull(materials.getType().getTypeId())) {
			pt.setString(3, materials.getType().getTypeId());
		} else {
			pt.setNull(3, Types.CHAR);
		}
		if (StringUtil.checkNotNull(materials.getUnit().getUnitId())) {
			pt.setString(4, materials.getUnit().getUnitId());
		} else {
			pt.setNull(4, Types.CHAR);
		}
		if (StringUtil.checkNotNull(materials.getSpec().getSpecId())) {
			pt.setString(5, materials.getSpec().getSpecId());
		} else {
			pt.setNull(5, Types.CHAR);
		}
		if (StringUtil.checkNotNull(materials.getModel().getModelId())) {
			pt.setString(6, materials.getModel().getModelId());
		} else {
			pt.setNull(6, Types.CHAR);
		}
		if (StringUtil.checkNotNull(materials.getCurrency().getCurrencyId())) {
			pt.setString(7, materials.getCurrency().getCurrencyId());
		} 
		if (StringUtil.checkNotNull(materials.getSource())) {
			pt.setString(8, materials.getSource());
		} else {
			pt.setNull(8, Types.CHAR);
		}
		pt.setDouble(9, materials.getMaxStock());
		pt.setDouble(10, materials.getMinStock());
		pt.setDouble(11, materials.getSafeStock());
		pt.setDouble(12, materials.getPrice());
		
		if (StringUtil.checkNotNull(materials.getRemark())) {
			pt.setString(13, materials.getRemark());
		} else {
			pt.setNull(13, Types.VARCHAR);
		}
		
		int rs = pt.executeUpdate();
		if (pt != null) {
			pt.close();
		}
		if (rs == 0) {
			return false;
		}
		return true;
	}
	
	public List<BdMaterials> saveBatch(Connection conn, List<BdMaterials> list) throws SQLException {
		String sql = "insert into bd_materials(materials_id,description,type_id,unit_id,spec_id,"
				+ "model_id,currency_id,source,maxstock,minstock,safestock,price,remark,isvalid) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,1)";
		PreparedStatement pt = conn.prepareStatement(sql);
		for (BdMaterials materials : list) {
			if (StringUtil.checkNotNull(materials.getMaterialsId())) {
				pt.setString(1, materials.getMaterialsId());
			} 
			if (StringUtil.checkNotNull(materials.getDescription())) {
				pt.setString(2, materials.getDescription());
			}
			if (StringUtil.checkNotNull(materials.getType().getTypeId())) {
				pt.setString(3, materials.getType().getTypeId());
			} else {
				pt.setNull(3, Types.CHAR);
			}
			if (StringUtil.checkNotNull(materials.getUnit().getUnitId())) {
				pt.setString(4, materials.getUnit().getUnitId());
			} else {
				pt.setNull(4, Types.CHAR);
			}
			if (StringUtil.checkNotNull(materials.getSpec().getSpecId())) {
				pt.setString(5, materials.getSpec().getSpecId());
			} else {
				pt.setNull(5, Types.CHAR);
			}
			if (StringUtil.checkNotNull(materials.getModel().getModelId())) {
				pt.setString(6, materials.getModel().getModelId());
			} else {
				pt.setNull(6, Types.CHAR);
			}
			if (StringUtil.checkNotNull(materials.getCurrency().getCurrencyId())) {
				pt.setString(7, materials.getCurrency().getCurrencyId());
			} 
			if (StringUtil.checkNotNull(materials.getSource())) {
				pt.setString(8, materials.getSource());
			} else {
				pt.setNull(8, Types.CHAR);
			}
			
			pt.setDouble(9, materials.getMaxStock());
			pt.setDouble(10, materials.getMinStock());
			pt.setDouble(11, materials.getSafeStock());
			pt.setDouble(12, materials.getPrice());
			
			if (StringUtil.checkNotNull(materials.getRemark())) {
				pt.setString(13, materials.getRemark());
			} else {
				pt.setNull(13, Types.VARCHAR);
			}
			pt.addBatch();
		}
		int[] rs = pt.executeBatch();
		
		if (pt != null) {
			pt.close();
		}
		
		List<BdMaterials> errorList = new ArrayList<BdMaterials>();
		for (int i = 0; i < rs.length; i++) {
			if (rs[i] <= 0 ) {
				errorList.add(list.get(i));
			}
		}
		return errorList;
	}
	
	public boolean update(Connection conn, BdMaterials materials) throws SQLException {
		StringBuffer str = new StringBuffer("update bd_materials ma set ");

		if (StringUtil.checkNotNull(materials.getDescription())) {
			str.append("ma.description = '" + materials.getDescription() + "',");
		} 
		if (StringUtil.checkNotNull(materials.getType().getTypeId())) {
			str.append("ma.type_id = '" + materials.getType().getTypeId() + "',");
		} else if("".equals(materials.getType().getTypeId())){
			str.append("ma.type_id = null,");
		}
		if (StringUtil.checkNotNull(materials.getUnit().getUnitId())) {
			str.append("ma.unit_id = '" + materials.getUnit().getUnitId() + "',");
		} else if("".equals(materials.getUnit().getUnitId())){
			str.append("ma.unit_id = null,");
		}
		if (StringUtil.checkNotNull(materials.getSpec().getSpecId())) {
			str.append("ma.spec_id = '" + materials.getSpec().getSpecId() + "',");
		} else if("".equals(materials.getSpec().getSpecId())){
			str.append("ma.spec_id = null,");
		}
		if (StringUtil.checkNotNull(materials.getModel().getModelId())) {
			str.append("ma.model_id = '" + materials.getModel().getModelId() + "',");
		} else if("".equals(materials.getModel().getModelId())){
			str.append("ma.model_id = null,");
		}
		if (StringUtil.checkNotNull(materials.getCurrency().getCurrencyId())) {
			str.append("ma.currency_id = '" + materials.getCurrency().getCurrencyId() + "',");
		}
		if (StringUtil.checkNotNull(materials.getSource())) {
			str.append("ma.source = '" + materials.getSource() + "',");
		}
		if (materials.getMaxStock() != null) {
			str.append("ma.maxstock = " + materials.getMaxStock() + ",");
		}
		if (materials.getMinStock() != null) {
			str.append("ma.minstock = " + materials.getMinStock() + ",");
		}
		if (materials.getSafeStock() != null) {
			str.append("ma.safestock = " + materials.getSafeStock() + ",");
		}
		if (materials.getMaxStock() != null) {
			str.append("ma.maxstock = " + materials.getMaxStock() + ",");
		}
		if (materials.getPrice() != null) {
			str.append("ma.price = " + materials.getPrice() + ",");
		}
		if (materials.getRemark() != null) {
			str.append("ma.remark = '" + materials.getRemark() + "',");
		}else {
			str.append("ma.remark = null,");
		}
		
		
		str.deleteCharAt(str.length() - 1);
		str.append(" where ma.materials_id like '" + materials.getMaterialsId() + "';");
		String sql = str.toString();
		
		PreparedStatement pt = conn.prepareStatement(sql);
		int rs = pt.executeUpdate();
		if (pt != null) {
			pt.close();
		}
		if (rs == 0) {
			return false;
		}
		
		return true;
	}
	
	
	public boolean delete(Connection conn, String materialsIds) throws SQLException {
		String sql = "update bd_materials set isvalid = 0 where materials_id in (" + materialsIds + ")";
		PreparedStatement pt = conn.prepareStatement(sql);
		int rs = pt.executeUpdate();
		if (pt != null) {
			pt.close();
		}
		if (rs == 0) {
			return false;
		}
		
		return true;
	}
	
	public BdMaterials getById(Connection conn, String materialID) throws SQLException {
		String sql = "select ma.materials_id, ma.description, ma.type_id, t.description as type_description,"
				+ " ma.unit_id, u.description as unit_description, ma.spec_id, sp.description as spec_description,"
				+ " ma.model_id, m.description as model_description, ma.currency_id, c.description as currency_description,"
				+ " ma.source, ma.maxstock, ma.minstock, ma.safestock, st.warehouse_amout as currentstock, ma.price, ma.remark"
				+ " from bd_materials ma "
				+ " left join bd_currency c on c.currency_id = ma.currency_id "
				+ " left join bd_model m on m.model_id = ma.model_id "
				+ " left join bd_spec sp on sp.spec_id = ma.spec_id "
				+ " left join bd_type t on t.type_id = ma.type_id "
				+ " left join bd_unit u on u.unit_id = ma.unit_id "
				+ " left join wh_stock st on ma.materials_id = st.materials_id "
				+ " where (ma.isvalid=1 or ma.isvalid is null)  and ma.materials_id like '" + materialID + "'";
	
		PreparedStatement pt = conn.prepareStatement(sql);
		ResultSet rs = pt.executeQuery();
		BdMaterials materials = null;
		if (rs.next()) {
			materials = new BdMaterials();
			materials.setMaterialsId(rs.getString("materials_id"));
			materials.setDescription(rs.getString("description"));
			materials.setType(new BdType(rs.getString("type_id"), rs.getString("type_description")));
			materials.setCurrency(new BdCurrency(rs.getString("currency_id"), rs.getString("currency_description")));
			materials.setSpec(new BdSpec(rs.getString("spec_id"), rs.getString("spec_description")));
			materials.setModel(new BdModel(rs.getString("model_id"), rs.getString("model_description")));
			materials.setUnit(new BdUnit(rs.getString("unit_id"), rs.getString("unit_description")));
			materials.setSource(rs.getString("source"));
			materials.setCurrentStock(rs.getDouble("currentstock"));
			materials.setMaxStock(rs.getDouble("maxstock"));
			materials.setMinStock(rs.getDouble("minstock"));
			materials.setSafeStock(rs.getDouble("safestock"));
			materials.setPrice(rs.getDouble("price"));
			materials.setRemark(rs.getString("remark"));
		}
		if (rs!= null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		return materials;
	}
	
	public List<BdMaterials> list(Connection conn, String typeIds, String key, int page, int rows, boolean limit, String stockState) throws SQLException {
		String sql = "select ma.materials_id, ma.description, ma.type_id, t.description as type_description,"
				+ " ma.unit_id, u.description as unit_description, ma.spec_id, sp.description as spec_description,"
				+ " ma.model_id, m.description as model_description, ma.currency_id, c.description as currency_description,"
				+ " ma.source, ma.maxstock, ma.minstock, ma.safestock, st.warehouse_amout as currentstock, ma.price, ma.remark"
				+ " from bd_materials ma "
				+ " left join bd_currency c on c.currency_id = ma.currency_id "
				+ " left join bd_model m on m.model_id = ma.model_id "
				+ " left join bd_spec sp on sp.spec_id = ma.spec_id "
				+ " left join bd_type t on t.type_id = ma.type_id "
				+ " left join bd_unit u on u.unit_id = ma.unit_id "
				+ " left join wh_stock st on ma.materials_id = st.materials_id "
				+ " where (ma.isvalid=1 or ma.isvalid is null) ";
	
		if (StringUtil.checkNotNull(typeIds)) {
			sql += " and ma.type_id in (" + typeIds.trim() +")";
		}
		if (StringUtil.checkNotNull(stockState)) {
			if ("1".equals(stockState)) {
				sql += " and st.warehouse_amout > ma.maxstock ";
			} else if ("2".equals(stockState)){
				sql += " and ma.minstock > 0 and (st.warehouse_amout < ma.minstock or st.warehouse_amout is null) ";
			} else if ("3".equals(stockState)){
				sql += " and ((st.warehouse_amout >= ma.minstock and st.warehouse_amout <= ma.safestock)"
						+ "or (st.warehouse_amout is null and ma.minstock =0))";
			} else if ("4".equals(stockState)){
				sql += " and st.warehouse_amout <= ma.maxstock and st.warehouse_amout >= ma.safestock ";
			} else if ("5".equals(stockState)){
				sql += " and ((st.warehouse_amout >= ma.minstock and st.warehouse_amout <= ma.maxstock)"
						+ " or (st.warehouse_amout is null and ma.minstock =0) )";
			}
		}
		
		// 判断是否包含过滤条件(关键字模糊查询)
		boolean filted = false;
		String temp = "";
		if (StringUtil.checkNotNull(key)) {
			filted = true;
			temp = "%" + key.trim() + "%";
			sql += " and (ma.description like ? or t.description like ? or sp.description like ? "
					+ "or m.description like ? or ma.source like ? or ma.materials_id like ?)";
		}
		// 是否分页
		if (limit) {
			sql += " limit " + (page-1)*rows + "," + rows;
		}
		PreparedStatement pt = conn.prepareStatement(sql);
		if (filted) {
			for (int i = 1; i <= 6; i++) {
				pt.setString(i, temp);
			}
		}
		ResultSet rs = pt.executeQuery();
		List<BdMaterials> list = new ArrayList<BdMaterials>();
		
		BdMaterials materials = null;
		while (rs.next()) {
			materials = new BdMaterials();
			materials.setMaterialsId(rs.getString("materials_id"));
			materials.setDescription(rs.getString("description"));
			materials.setType(new BdType(rs.getString("type_id"), rs.getString("type_description")));
			materials.setCurrency(new BdCurrency(rs.getString("currency_id"), rs.getString("currency_description")));
			materials.setSpec(new BdSpec(rs.getString("spec_id"), rs.getString("spec_description")));
			materials.setModel(new BdModel(rs.getString("model_id"), rs.getString("model_description")));
			materials.setUnit(new BdUnit(rs.getString("unit_id"), rs.getString("unit_description")));
			materials.setSource(rs.getString("source"));
			materials.setCurrentStock(rs.getDouble("currentstock"));
			materials.setMaxStock(rs.getDouble("maxstock"));
			materials.setMinStock(rs.getDouble("minstock"));
			materials.setSafeStock(rs.getDouble("safestock"));
			materials.setPrice(rs.getDouble("price"));
			materials.setRemark(rs.getString("remark"));
			list.add(materials);
		}
		if (rs!= null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		return list;
	}
	
	public List<BdMaterials> list(Connection conn, String typeId, String key, int page, int rows, String stockState) throws SQLException {
		return list(conn, typeId, key, page, rows, true, stockState);
	}
	
	public List<BdMaterials> getAll(Connection conn) throws SQLException {
		return list(conn, null, null, 0, 0, false, null);
	}
	
	public int count(Connection conn, String typeIds, String key, String stockState) throws SQLException {
		String sql = "select count(ma.materials_id) "
				+ " from bd_materials ma "
				+ " left join bd_model m on m.model_id = ma.model_id "
				+ " left join bd_spec sp on sp.spec_id = ma.spec_id "
				+ " left join bd_type t on t.type_id = ma.type_id "
				+ " left join wh_stock st on ma.materials_id = st.materials_id "
				+ " where (ma.isvalid=1 or ma.isvalid is null) ";
	
		if (StringUtil.checkNotNull(typeIds)) {
			sql += " and ma.type_id in (" + typeIds.trim() +")";
		}
		
		if (StringUtil.checkNotNull(stockState)) {
			if ("1".equals(stockState)) {
				sql += " and st.warehouse_amout > ma.maxstock ";
			} else if ("2".equals(stockState)){
				sql += " and ma.minstock > 0 and (st.warehouse_amout < ma.minstock or st.warehouse_amout is null) ";
			} else if ("3".equals(stockState)){
				sql += " and ((st.warehouse_amout >= ma.minstock and st.warehouse_amout <= ma.safestock)"
						+ "or (st.warehouse_amout is null and ma.minstock =0))";
			} else if ("4".equals(stockState)){
				sql += " and st.warehouse_amout <= ma.maxstock and st.warehouse_amout >= ma.safestock ";
			} else if ("5".equals(stockState)){
				sql += " and ((st.warehouse_amout >= ma.minstock and st.warehouse_amout <= ma.maxstock)"
						+ " or (st.warehouse_amout is null and ma.minstock =0) )";
			}
		}
		
		// 判断是否包含过滤条件(关键字模糊查询)
		boolean filted = false;
		String temp = "";
		if (StringUtil.checkNotNull(key)) {
			filted = true;
			temp = "%" + key.trim() + "%";
			sql += " and (ma.description like ? or t.description like ? or sp.description like ? "
					+ "or m.description like ? or ma.source like ?)";
		}
		
		PreparedStatement pt = conn.prepareStatement(sql);
		if (filted) {
			for (int i = 1; i <= 5; i++) {
				pt.setString(i, temp);
			}
		}
		ResultSet rs = pt.executeQuery();
		
		int count = 0;
		if (rs.next()) {
			count = rs.getInt(1);
		}
		if (rs!= null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		return count;
	}
	
	public Map<String, String> isExists(Connection conn, BdMaterials materials) throws SQLException {
		String sql = "select (select ma.materials_id from bd_materials ma "
					+ "where ma.description like ? and ma.spec_id like ? and (ma.isvalid=1 or ma.isvalid is null)),"
					+ "(select max(mid(m.materials_id, 7)) from bd_materials m)";
		PreparedStatement pt = conn.prepareStatement(sql);
		pt.setString(1, materials.getDescription());
		pt.setString(2, materials.getSpec().getSpecId());
		ResultSet rs = pt.executeQuery();
		Map<String, String> result = new HashMap<String, String>();
		if (rs.next()) {
			result.put("materials_id", rs.getString(1));
			result.put("max", rs.getString(2));
		}
		if (rs!= null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		
		return result;
	}
	
	public boolean isExistsByName(Connection conn, BdMaterials materials) throws SQLException {
		String sql = "select ma.materials_id from bd_materials ma "
					+ "left join bd_spec sp on sp.spec_id = ma.spec_id "
					+ "where ma.description like ? and sp.description like ? and (ma.isvalid=1 or ma.isvalid is null)";
		PreparedStatement pt = conn.prepareStatement(sql);
		pt.setString(1, materials.getDescription());
		pt.setString(2, materials.getSpec().getDescription());
		ResultSet rs = pt.executeQuery();
		boolean result = false;
		if (rs.next()) {
			result = true;
		}
		if (rs!= null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		
		return result;
	}
	
	public List<Map<String, String>> getAllDescriptionAndSpec(Connection conn) throws SQLException{
		String sql = "select ma.description as name, sp.description as spec from bd_materials ma "
					+ "left join bd_spec sp on sp.spec_id = ma.spec_id "
					+ "where (ma.isvalid=1 or ma.isvalid is null)";
		PreparedStatement pt = conn.prepareStatement(sql);
		ResultSet rs = pt.executeQuery();
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		while(rs.next()) {
			Map<String, String> map = new HashMap<String,String>();
			map.put("description", rs.getString("name"));
			map.put("spec", rs.getString("spec"));
			list.add(map);
		}
		
		if (rs!= null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		return list;
	}
	
	public Map<String, String> getAllMaxIDKey(Connection conn) throws SQLException {
		String sql = "select"
				+"(select max(mid(materials_id,7)) from bd_materials where materials_id like 'BM%' and length(materials_id)=12) as materials_id_key,"
				+"(select max(mid(type_id,3)) from bd_type where type_id like 'BT%') as type_id_key,"
				+"(select max(mid(unit_id,3)) from bd_unit where unit_id like 'BN%') as unit_id_key,"
				+"(select max(currency_id) from bd_currency) as currency_id_key,"
				+"(select max(mid(model_id,3)) from bd_model where model_id like 'MO%') as model_id_key,"
				+"(select max(mid(spec_id,3)) from bd_spec where spec_id like 'BS%') as spec_id_key";
		PreparedStatement pt = conn.prepareStatement(sql);
		ResultSet rs = pt.executeQuery();
		Map<String, String> map = new HashMap<String, String>();
		if(rs.next()) {
			map.put("materialsIdKey", rs.getString("materials_id_key"));
			map.put("typeIdKey", rs.getString("type_id_key"));
			map.put("unitIdKey", rs.getString("unit_id_key"));
			map.put("currencyIdKey", rs.getString("currency_id_key"));
			map.put("modelIdKey", rs.getString("model_id_key"));
			map.put("specIdKey", rs.getString("spec_id_key"));
		}
		if (rs!= null) {
			rs.close();
		}
		if (pt != null) {
			pt.close();
		}
		return map;
	}
}
