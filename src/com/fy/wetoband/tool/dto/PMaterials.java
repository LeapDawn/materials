package com.fy.wetoband.tool.dto;

public class PMaterials {
	private String materialsId; // 物料ID
	private String description; // 物料名称
	private String source; // 物料来源
	private Double currentStock; // 库存
	private Double maxStock; // 最大库存
	private Double minStock; // 最小库存
	private Double safeStock; // 安全库存
	private Double price; // 价格
	private String texture; // 材质
	private String scantling; // 材质尺寸
	private Boolean isvalid; // 是否有效,true有效
	private String remark; // 备注信息
	private Boolean hasNewCurrency; // 是否新增货币单位,ture为新增
	private String bdCurrency; // 货币单位
	private Boolean hasNewType; // 是否新增类型,true为新增
	private String bdType; // 类型
	private String superType; // 父类类型名称(在新增类型的情况下该字段才有意义)
	private Boolean hasNewSpec; // 是否新增规格,true为新增
	private String bdSpec; // 规格
	private Boolean hasNewModel; // 是否新增型号,true为新增
	private String bdModel; // 机型
	private Boolean hasNewUnit; // 是否新增数量单位,true为新增
	private String bdUnit; // 数量单位
	private String errorMsg;  // 错误信息(导入失败时的提示信息)
	// 库存状态(在查询物料列表数据时作筛选)1.库存大于最高库存;2.库存低于最低库存;3.库存介于最低和安全库存
	//							  4.库存介于安全和最高库存;5.库存介于最低和最高库存
	private String stockState;   
	
	public PMaterials() {
		super();
		this.isvalid = true; // 默认为true
		this.hasNewCurrency = false;
		this.hasNewModel = false;
		this.hasNewSpec = false;
		this.hasNewType = false;
		this.hasNewUnit = false;
	}

	public PMaterials(String materialsId, String description, String source,
			Double maxStock, Double minStock, Double safeStock, Double price,
			String remark, String bdCurrency, String bdType, String bdSpec,
			String bdModel, String bdUnit) {
		super();
		this.materialsId = materialsId;
		this.description = description;
		this.source = source;
		this.maxStock = maxStock;
		this.minStock = minStock;
		this.safeStock = safeStock;
		this.price = price;
		this.remark = remark;
		this.bdCurrency = bdCurrency;
		this.bdType = bdType;
		this.bdSpec = bdSpec;
		this.bdModel = bdModel;
		this.bdUnit = bdUnit;
		this.isvalid = true;
		this.hasNewCurrency = false;
		this.hasNewModel = false;
		this.hasNewSpec = false;
		this.hasNewType = false;
		this.hasNewUnit = false;
	}

	public String getSuperType() {
		return superType;
	}

	public void setSuperType(String superType) {
		this.superType = superType;
	}

	public Boolean getHasNewCurrency() {
		return hasNewCurrency;
	}

	public void setHasNewCurrency(Boolean hasNewCurrency) {
		this.hasNewCurrency = hasNewCurrency;
	}

	public Boolean getHasNewType() {
		return hasNewType;
	}

	public void setHasNewType(Boolean hasNewType) {
		this.hasNewType = hasNewType;
	}

	public Boolean getHasNewSpec() {
		return hasNewSpec;
	}

	public void setHasNewSpec(Boolean hasNewSpec) {
		this.hasNewSpec = hasNewSpec;
	}

	public Boolean getHasNewModel() {
		return hasNewModel;
	}

	public void setHasNewModel(Boolean hasNewMoedl) {
		this.hasNewModel = hasNewMoedl;
	}

	public Boolean getHasNewUnit() {
		return hasNewUnit;
	}

	public void setHasNewUnit(Boolean hasNewUnit) {
		this.hasNewUnit = hasNewUnit;
	}

	public String getMaterialsId() {
		return materialsId;
	}

	public void setMaterialsId(String materialsId) {
		this.materialsId = materialsId;
	}

	public String getBdType() {
		return bdType;
	}

	public void setBdType(String bdType) {
		this.bdType = bdType;
	}

	public String getBdCurrency() {
		return bdCurrency;
	}

	public void setBdCurrency(String bdCurrency) {
		this.bdCurrency = bdCurrency;
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

	public Double getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(Double currentStock) {
		this.currentStock = currentStock;
	}

	public Double getMaxStock() {
		return maxStock;
	}

	public void setMaxStock(Double maxStock) {
		this.maxStock = maxStock;
	}

	public Double getMinStock() {
		return minStock;
	}

	public void setMinStock(Double minStock) {
		this.minStock = minStock;
	}

	public Double getSafeStock() {
		return safeStock;
	}

	public void setSafeStock(Double safeStock) {
		this.safeStock = safeStock;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getTexture() {
		return texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}

	public String getScantling() {
		return scantling;
	}

	public void setScantling(String scantling) {
		this.scantling = scantling;
	}

	public Boolean getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(Boolean isvalid) {
		this.isvalid = isvalid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getStockState() {
		return stockState;
	}

	public void setStockState(String stockState) {
		this.stockState = stockState;
	}

}
