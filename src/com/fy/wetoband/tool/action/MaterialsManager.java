package com.fy.wetoband.tool.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.fy.wetoband.bean.RunToolParam;
import com.fy.wetoband.tool.commons.ExeclException;
import com.fy.wetoband.tool.commons.RepeatException;
import com.fy.wetoband.tool.commons.StringUtil;
import com.fy.wetoband.tool.commons.ToolException;
import com.fy.wetoband.tool.dto.AjaxResult;
import com.fy.wetoband.tool.dto.PMaterials;
import com.fy.wetoband.tool.dto.PageModel;
import com.fy.wetoband.tool.manager.Tool;
import com.fy.wetoband.tool.service.MaterialsService;
import com.fy.wetoband.tool.service.PageLoaderService;
import com.fy.wetoband.tool.service.TypeService;

public class MaterialsManager extends Tool {

	@Override
	public void act(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String toolAction = request.getParameter("toolAction");
		if (!StringUtil.checkNotNull(toolAction)) {
			this.writeResult(response, new AjaxResult<Object>(false, "请求的工具Action是什么?"));
		}
		if (toolAction.equals("listMaterials")) {
			this.listMaterials(request, response);
		} else if(toolAction.equals("saveMaterials")){
			this.saveMaterials(request, response);
		} else if(toolAction.equals("updateMaterials")){
			this.updateMaterials(request, response);
		} else if(toolAction.equals("deleteMaterials")){
			this.deleteMaterials(request, response);
		} else if(toolAction.equals("getMaterials")){
			this.getMaterials(request, response);
		} else if(toolAction.equals("getSelection")){
			this.getSelection(request, response);
		} else if(toolAction.equals("importExecl")){
			this.importFromExcel(request, response);
		} else if(toolAction.equals("exportExecl")){
			this.ExportToExcel(request, response);
		} else if(toolAction.equals("saveType")){
			this.saveType(request, response);
		} else if(toolAction.equals("updateType")){
			this.updateType(request, response);
		} else if(toolAction.equals("deleteType")){
			this.deleteType(request, response);
		} else {
			this.writeResult(response, new AjaxResult<Object>(false, "请求的工具Action不存在, 请检查url"));
		}
	}

	/**
	 * 新增物料
	 */
	public void saveMaterials(HttpServletRequest request, HttpServletResponse response){
		
		PMaterials pma = this.getSaveMaterialsParms(request, response);
		if (pma == null) {
			return;
		}
		Connection conn = this.getConnectionByBViewID();
		MaterialsService materialsService = new MaterialsService(conn);
		boolean success = false;
		AjaxResult result = null;
		try {
			success = materialsService.save(pma);
			result = new AjaxResult<Object>(success, null);
		} catch (RepeatException e) {
			e.printStackTrace();
			result = new AjaxResult<Object>(false, "已存在相同规格与名称的物料");
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		this.writeResult(response, result);
	}
	
	/**
	 * 更新物料信息
	 */
	public void updateMaterials(HttpServletRequest request, HttpServletResponse response){
		
		PMaterials pma = this.getUpdateMaterialsParms(request, response);
		if (pma == null) {
			return;
		}
		String materialsId = request.getParameter("materialsId");
		if (!StringUtil.checkNotNull(materialsId)) {
			this.writeResult(response, new AjaxResult<Object>(false, "请检查必要参数是否填充完整"));
			return;
		}
		pma.setMaterialsId(materialsId);
		
		Connection conn = this.getConnectionByBViewID();
		MaterialsService materialsService = new MaterialsService(conn);
		boolean success = materialsService.update(pma);
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		this.writeResult(response, new AjaxResult<Object>(success, null));
	}
	
	/**
	 * 删除物料信息
	 */
	public void deleteMaterials(HttpServletRequest request, HttpServletResponse response){
		String materialsId = request.getParameter("materialsId");
		if (!StringUtil.checkNotNull(materialsId)) {
			this.writeResult(response, new AjaxResult<Object>(false, "请检查必要参数是否填充完整"));
			return;
		}
		
		Connection conn = this.getConnectionByBViewID();
		MaterialsService materialsService = new MaterialsService(conn);
		boolean success = materialsService.delete(materialsId);
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		this.writeResult(response, new AjaxResult<Object>(success, null));
	}
	
	/**
	 * 根据ID获取物料
	 */
	public void getMaterials(HttpServletRequest request, HttpServletResponse response){
		
		String materialsId = request.getParameter("materialsId");
		if (!StringUtil.checkNotNull(materialsId)) {
			this.writeResult(response, new AjaxResult<Object>(false, "请检查必要参数是否填充完整"));
			return;
		}
		
		Connection conn = this.getConnectionByBViewID();
		MaterialsService materialsService = new MaterialsService(conn);
		PMaterials pma = materialsService.getMaterialsByID(materialsId);
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pma == null) {
			this.writeResult(response, new AjaxResult<Object>(false, "查找失败"));
		} else {
			this.writeResult(response, new AjaxResult<Object>(true, pma));
		}
	}
	
	/**
	 * 获取物料列表数据
	 * 接受参数:typeId,key,currentPage,rows
	 */
	public void listMaterials(HttpServletRequest request, HttpServletResponse response){
		String typeId = request.getParameter("typeId");
		String key = request.getParameter("key");
		String currentPageStr = request.getParameter("currentPage");
		String rowsStr = request.getParameter("rows");
		String stockState = request.getParameter("stockState");
		int currentPage = 0;
		int rows = 0;
		if (StringUtil.checkNotNull(currentPageStr) && StringUtil.isInt(currentPageStr)){
			currentPage = Integer.valueOf(currentPageStr);
		}
		if (StringUtil.checkNotNull(rowsStr) && StringUtil.isInt(rowsStr)){
			rows = Integer.valueOf(rowsStr);
		}
		
		Connection conn = this.getConnectionByBViewID();
		MaterialsService materialService = new MaterialsService(conn);
		PageModel pageModel = materialService.list(typeId, key, currentPage, rows, stockState);
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		AjaxResult<Object> result = null; 
		if (pageModel == null) {
			result = new AjaxResult<Object>(false, "获取物料列表失败");
		} else {
			result = new AjaxResult<Object>(true, pageModel);
		}
		this.writeResult(response, result);
	}
	
	
	/**
	 * 获取下拉框选项
	 * 前端参数:target请求的下拉框对象(currency货币/unit量词/model型号/spec规格/type类型),description筛选条件
	 */
	public void getSelection(HttpServletRequest request, HttpServletResponse response){
		String target = request.getParameter("target");
		String description = request.getParameter("description");

		if (!StringUtil.checkNotNull(target)) {
			this.writeResult(response, new AjaxResult<Object>(false, "请检查必要参数是否填充完整"));
			return;
		}
		
		List<?> list = null;
		Connection conn = this.getConnectionByBViewID();
		PageLoaderService plService = new PageLoaderService(conn);
		if (target.equals("currency")) {
			list = plService.getCurrency(description);
		} else if (target.equals("unit")){
			list = plService.getUnit(description);
		} else if (target.equals("model")){
			list = plService.getModel(description);
		} else if (target.equals("spec")){
			list = plService.getSpec(description);
		} else if (target.equals("type")){
			list = plService.getType(description);
		} else {
			this.writeResult(response, new AjaxResult<Object>(false, "请检查必要参数是否正确"));
			return;
		}
		
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		AjaxResult<Object> result = null; 
		if (list == null) {
			result = new AjaxResult<Object>(false, "获取"+target+"选项失败");
		} else {
			result = new AjaxResult<Object>(true, list);
		}
		this.writeResult(response, result);
	}
	
	/**
	 * 从execl文件中导入物料数据
	 */
	public void importFromExcel(HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		File upLoadFile = null;
		try {
			upLoadFile = getUpLoadFile(request, response);
		} catch (ExeclException e) {
			e.printStackTrace();
			this.writeResult(response, new AjaxResult<Object>(false, e.getMessage()));
		} catch (Exception e) {
			e.printStackTrace();
			this.writeResult(response, new AjaxResult<Object>(false, "上传文件失败"));
		}
		if (upLoadFile == null) {
			return;
		}
		
		
		Connection conn = this.getConnectionByBViewID();
		MaterialsService maService = new MaterialsService(conn);
		Map<String, Object> map = null;
		AjaxResult<Object> result = null; 
		try {
			map = maService.importFromFile(upLoadFile);
			result = new AjaxResult<Object>(true, map);
		} catch (ToolException e) {
			e.printStackTrace();
			result = new AjaxResult<Object>(false, e.getMessage());
		} finally{
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		this.writeResult(response, result);
	}
	
	/**
	 * 导出数据到execl文件中
	 */
	public void ExportToExcel(HttpServletRequest request, HttpServletResponse response){
		Connection conn = this.getConnectionByBViewID();
		MaterialsService maService = new MaterialsService(conn);
		File file = null;
		try {
			file = maService.ExportToExecl();
		} catch (ToolException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (file == null) {
			this.writeResult(response, new AjaxResult<Object>(false, "导出数据失败"));
		} else {
//			response.setContentType("application/x-msdownload");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + file.getName() + "\"");
			try {
				InputStream inputStream = new FileInputStream(file);
				ServletOutputStream ouputStream = response.getOutputStream();
				byte b[] = new byte[1024];
				int n;
				while ((n = inputStream.read(b)) != -1) {
					ouputStream.write(b, 0, n);
				}
				// 关闭流、释放资源
				ouputStream.close();
				inputStream.close();
			} catch(IOException e) {
				this.writeResult(response, new AjaxResult<Object>(false, "导出数据失败"));
			}
		}
	}
	
	/**
	 * 新增类别
	 */
	public void saveType(HttpServletRequest request, HttpServletResponse response) {
		String superType = request.getParameter("superType");
		String description = request.getParameter("description");
		if (!StringUtil.checkNotNull(description)) {
			this.writeResult(response, new AjaxResult<Object>(false, "类别名称不能为空"));
			return;
		}
		Connection conn = this.getConnectionByBViewID();
		TypeService typeService = new TypeService(conn);
		AjaxResult result = null;
		try {
			boolean success = typeService.add(description, superType);
			result = new AjaxResult<Object>(success, null);
		} catch (ToolException e) {
			e.printStackTrace();
			result = new AjaxResult<Object>(false, e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		this.writeResult(response, result);
	}
	
	/**
	 * 修改类别名称
	 */
	public void updateType(HttpServletRequest request, HttpServletResponse response) {
		String typeId = request.getParameter("typeId");
		String description = request.getParameter("description");
		if (!StringUtil.checkNotNull(description)) {
			this.writeResult(response, new AjaxResult<Object>(false, "类别名称不能为空"));
			return;
		}
		if (!StringUtil.checkNotNull(typeId)) {
			this.writeResult(response, new AjaxResult<Object>(false, "类别ID不能为空"));
			return;
		}
		Connection conn = this.getConnectionByBViewID();
		TypeService typeService = new TypeService(conn);
		AjaxResult result = null;
		try {
			boolean success = typeService.update(typeId, description);
			result = new AjaxResult<Object>(success, null);
		} catch (ToolException e) {
			e.printStackTrace();
			result = new AjaxResult<Object>(false, e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		this.writeResult(response, result);
	}
	
	/**
	 * 删除类别
	 */
	public void deleteType(HttpServletRequest request, HttpServletResponse response) {
		String typeId = request.getParameter("typeId");
		if (!StringUtil.checkNotNull(typeId)) {
			this.writeResult(response, new AjaxResult<Object>(false, "类别ID不能为空"));
			return;
		}
		Connection conn = this.getConnectionByBViewID();
		TypeService typeService = new TypeService(conn);
		AjaxResult result = null;
		try {
			boolean success = typeService.delete(typeId);
			result = new AjaxResult<Object>(success, null);
		} catch (ToolException e) {
			e.printStackTrace();
			result = new AjaxResult<Object>(false, e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		this.writeResult(response, result);
	}
	
	/* 获取上传的execl文件  */
	private File getUpLoadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			this.writeResult(response, new AjaxResult<Object>(false, "该请求不是multipart请求"));
			return null;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(3*1024*1024);    // 上传限制3M
		List<FileItem> items = upload.parseRequest(request);
		Iterator<FileItem> iterator = items.iterator();
		// 获取上传的文件
		FileItem uploadFileItem = null;
		while (iterator.hasNext()) {
			FileItem item = iterator.next();
			if (!item.isFormField()){
				uploadFileItem = item;
				break;
			}
		}
		if (uploadFileItem == null) {
			this.writeResult(response, new AjaxResult<Object>(false, "没有上传文件"));
			return null;
		}
		
		// 验证文件的格式
		String contentType = uploadFileItem.getContentType();
		if (!contentType.equals("application/vnd.ms-excel") && !contentType.equals("application/octet-stream")) {
			this.writeResult(response, new AjaxResult<Object>(false, "文件格式不正确"));
			return null;
		}
	
		File uploadFile = new File(request.getServletContext().getRealPath("/") + "temp");
		uploadFileItem.write(uploadFile);
		return uploadFile;
	}
	
	private PMaterials getUpdateMaterialsParms(HttpServletRequest request, HttpServletResponse response) {
		String description = request.getParameter("description");
		String source = request.getParameter("source");
		String maxStockStr = request.getParameter("maxStock");
		String minStockStr = request.getParameter("minStock");
		String safeStockStr = request.getParameter("safeStock");
		String priceStr = request.getParameter("price");
		String remark = request.getParameter("remark");
		String hasNewCurrencyStr = request.getParameter("hasNewCurrency");
		String bdCurrency = request.getParameter("bdCurrency");
		String hasNewTypeStr = request.getParameter("hasNewType");
		String bdType = request.getParameter("bdType");
		String superType = request.getParameter("superType");
		String hasNewSpecStr = request.getParameter("hasNewSpec");
		String bdSpec = request.getParameter("bdSpec");
		String hasNewModelStr = request.getParameter("hasNewModel");
		String bdModel = request.getParameter("bdModel");
		String hasNewUnitStr = request.getParameter("hasNewUnit");
		String bdUnit = request.getParameter("bdUnit");
		
		if (!StringUtil.isNum(maxStockStr) || !StringUtil.isNum(minStockStr) 
				|| !StringUtil.isNum(safeStockStr) || !StringUtil.isNum(priceStr)){
			this.writeResult(response, new AjaxResult<Object>(false, "请检查必要库存、价格等数据是否合法"));
			return null;
		}
		
		Double maxStock = Double.valueOf(maxStockStr);
		Double minStock = Double.valueOf(minStockStr);
		Double safeStock = Double.valueOf(safeStockStr);
		Double price = Double.valueOf(priceStr);
		
		PMaterials pma = new PMaterials(null, description, source, maxStock, minStock, 
				safeStock, price, remark, bdCurrency, bdType, bdSpec, bdModel, bdUnit);
		
		if (StringUtil.checkNotNull(hasNewCurrencyStr) && hasNewCurrencyStr.equals("true")){
				pma.setHasNewCurrency(true);
		}
		if (StringUtil.checkNotNull(hasNewModelStr) && hasNewModelStr.equals("true")){
			pma.setHasNewModel(true);
		}
		if (StringUtil.checkNotNull(hasNewSpecStr) && hasNewSpecStr.equals("true")){
			pma.setHasNewSpec(true);
		}
		if (StringUtil.checkNotNull(hasNewTypeStr) && hasNewTypeStr.equals("true")){
			pma.setHasNewType(true);
			pma.setSuperType(superType);
		}
		if (StringUtil.checkNotNull(hasNewUnitStr) && hasNewUnitStr.equals("true")){
			pma.setHasNewUnit(true);
		}
		return pma;
	}
	
	/* 获取物料对象操作参数  */
	private PMaterials getSaveMaterialsParms(HttpServletRequest request, HttpServletResponse response) {
		String description = request.getParameter("description");
		String source = request.getParameter("source");
		String maxStockStr = request.getParameter("maxStock");
		String minStockStr = request.getParameter("minStock");
		String safeStockStr = request.getParameter("safeStock");
		String priceStr = request.getParameter("price");
		String remark = request.getParameter("remark");
		String hasNewCurrencyStr = request.getParameter("hasNewCurrency");
		String bdCurrency = request.getParameter("bdCurrency");
		String hasNewTypeStr = request.getParameter("hasNewType");
		String bdType = request.getParameter("bdType");
		String superType = request.getParameter("superType");
		String hasNewSpecStr = request.getParameter("hasNewSpec");
		String bdSpec = request.getParameter("bdSpec");
		String hasNewModelStr = request.getParameter("hasNewModel");
		String bdModel = request.getParameter("bdModel");
		String hasNewUnitStr = request.getParameter("hasNewUnit");
		String bdUnit = request.getParameter("bdUnit");
		
		if (!StringUtil.checkNotNull(description) || !StringUtil.checkNotNull(minStockStr) 
				|| !StringUtil.checkNotNull(maxStockStr) || !StringUtil.checkNotNull(safeStockStr)
				|| !StringUtil.checkNotNull(priceStr) || !StringUtil.checkNotNull(bdCurrency)) {
			this.writeResult(response, new AjaxResult<Object>(false, "请检查必要参数是否填充完整"));
			return null;
		}
		
		if (!StringUtil.isNum(maxStockStr) || !StringUtil.isNum(minStockStr) 
				|| !StringUtil.isNum(safeStockStr) || !StringUtil.isNum(priceStr)){
			this.writeResult(response, new AjaxResult<Object>(false, "请检查必要库存、价格等数据是否合法"));
			return null;
		}
		
		Double maxStock = Double.valueOf(maxStockStr);
		Double minStock = Double.valueOf(minStockStr);
		Double safeStock = Double.valueOf(safeStockStr);
		Double price = Double.valueOf(priceStr);
		
		PMaterials pma = new PMaterials(null, description, source, maxStock, minStock, 
				safeStock, price, remark, bdCurrency, bdType, bdSpec, bdModel, bdUnit);
		
		if (StringUtil.checkNotNull(hasNewCurrencyStr) && hasNewCurrencyStr.equals("true")){
				pma.setHasNewCurrency(true);
		}
		if (StringUtil.checkNotNull(hasNewModelStr) && hasNewModelStr.equals("true")){
			pma.setHasNewModel(true);
		}
		if (StringUtil.checkNotNull(hasNewSpecStr) && hasNewSpecStr.equals("true")){
			pma.setHasNewSpec(true);
		}
		if (StringUtil.checkNotNull(hasNewTypeStr) && hasNewTypeStr.equals("true")){
			pma.setHasNewType(true);
			pma.setSuperType(superType);
		}
		if (StringUtil.checkNotNull(hasNewUnitStr) && hasNewUnitStr.equals("true")){
			pma.setHasNewUnit(true);
		}
		return pma;
	}
	
	/* 返回结果给前端 */
	private void writeResult(HttpServletResponse response, AjaxResult result) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		try {
			Writer writer = response.getWriter();
			Object value = result.getValue();
			writer.write(JSONObject.fromObject(result).toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<Object, Object> toolMain(RunToolParam arg0) {
		return null;
	}

	@Override
	public Map<Object, Object> toolMain(HttpServletRequest arg0,
			HttpServletResponse arg1) {
		return null;
	}

}
