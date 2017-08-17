package com.fy.wetoband.tool.dto;

public class ErrorMaterialObject {
	private String materialsId; // 物料ID
	private String description; // 物料名称
	private String source; // 物料来源
	private String maxStock; // 最大库存
	private String minStock; // 最小库存
	private String safeStock; // 安全库存
	private String price; // 价格
	private String remark; // 备注信息
	private String bdCurrency; // 货币单位
	private String bdType; // 类型
	private String bdSpec; // 规格
	private String bdModel; // 机型
	private String bdUnit; // 数量单位
	private String errorMsg; // 错误信息(导入失败时的提示信息)

	public String getMaterialsId() {
		return materialsId;
	}

	public void setMaterialsId(String materialsId) {
		this.materialsId = materialsId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getMaxStock() {
		return maxStock;
	}

	public void setMaxStock(String maxStock) {
		this.maxStock = maxStock;
	}

	public String getMinStock() {
		return minStock;
	}

	public void setMinStock(String minStock) {
		this.minStock = minStock;
	}

	public String getSafeStock() {
		return safeStock;
	}

	public void setSafeStock(String safeStock) {
		this.safeStock = safeStock;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBdCurrency() {
		return bdCurrency;
	}

	public void setBdCurrency(String bdCurrency) {
		this.bdCurrency = bdCurrency;
	}

	public String getBdType() {
		return bdType;
	}

	public void setBdType(String bdType) {
		this.bdType = bdType;
	}

	public String getBdSpec() {
		return bdSpec;
	}

	public void setBdSpec(String bdSpec) {
		this.bdSpec = bdSpec;
	}

	public String getBdModel() {
		return bdModel;
	}

	public void setBdModel(String bdModel) {
		this.bdModel = bdModel;
	}

	public String getBdUnit() {
		return bdUnit;
	}

	public void setBdUnit(String bdUnit) {
		this.bdUnit = bdUnit;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
