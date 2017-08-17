package com.fy.wetoband.tool.model;


public class BdMaterials implements java.io.Serializable {


	private String materialsId;    // 物料ID
	private BdType Type;         // 类型
	private BdCurrency Currency; // 货币
	private BdSpec Spec;         // 规格
	private BdModel Model;       // 型号
	private BdUnit Unit;         // 数量单位
	private String description;    // 物料名称
	private String source;         // 物料来源
	private Double currentStock;   // 库存
	private Double maxStock;       // 最大库存
	private Double minStock;       // 最小库存
	private Double safeStock;      // 安全库存
	private Double price;          // 价格
	private String texture;        // 材质
	private String scantling;      // 材质尺寸
	private Boolean isvalid;       // 是否有效,true有效
	private String remark;         // 备注信息
	
	public BdMaterials() {
		super();
	}
	
	public BdMaterials(BdType bdType, String description) {
		super();
		this.Type = bdType;
		this.description = description;
	}

	public String getMaterialsId() {
		return materialsId;
	}
	public void setMaterialsId(String materialsId) {
		this.materialsId = materialsId;
	}
	public BdType getType() {
		return Type;
	}
	public void setType(BdType bdType) {
		this.Type = bdType;
	}
	public BdCurrency getCurrency() {
		return Currency;
	}
	public void setCurrency(BdCurrency bdCurrency) {
		this.Currency = bdCurrency;
	}
	public BdSpec getSpec() {
		return Spec;
	}
	public void setSpec(BdSpec bdSpec) {
		this.Spec = bdSpec;
	}
	public BdModel getModel() {
		return Model;
	}
	public void setModel(BdModel bdModel) {
		this.Model = bdModel;
	}
	public BdUnit getUnit() {
		return Unit;
	}
	public void setUnit(BdUnit bdUnit) {
		this.Unit = bdUnit;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description){
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

	@Override
	public String toString() {
		return "BdMaterials [materialsId=" + materialsId + ", bdType=" + Type
				+ ", bdCurrency=" + Currency + ", bdSpec=" + Spec
				+ ", bdModel=" + Model + ", bdUnit=" + Unit
				+ ", description=" + description + ", source=" + source
				+ ", CurrentStock=" + currentStock + ", maxStock=" + maxStock
				+ ", minStock=" + minStock + ", safeStock=" + safeStock
				+ ", price=" + price + ", texture=" + texture + ", scantling="
				+ scantling + ", isvalid=" + isvalid + ", remark=" + remark
				+ "]";
	}
}