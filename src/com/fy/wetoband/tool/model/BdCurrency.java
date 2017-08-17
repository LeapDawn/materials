package com.fy.wetoband.tool.model;


public class BdCurrency implements java.io.Serializable {


	private String currencyId;    // 货币ID
	private String description;   // 货币名称


	public BdCurrency() {
	}

	public BdCurrency(String description) {
		super();
		this.description = description;
	}

	public BdCurrency(String currencyId, String description) {
		this.currencyId = currencyId;
		this.description = description;
	}

	public String getCurrencyId() {
		return this.currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "BdCurrency [currencyId=" + currencyId + ", description="
				+ description + "]";
	}
	
}