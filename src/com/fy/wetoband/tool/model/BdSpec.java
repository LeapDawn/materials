package com.fy.wetoband.tool.model;

public class BdSpec implements java.io.Serializable {


	private String specId;    // 规格ID
	private String description;  // 规格名称

	public BdSpec() {
	}
	
	public BdSpec(String description) {
		super();
		this.description = description;
	}

	public BdSpec(String specId, String description) {
		this.specId = specId;
		this.description = description;
	}

	public String getSpecId() {
		return this.specId;
	}

	public void setSpecId(String specId) {
		this.specId = specId;
	}


	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "BdSpec [specId=" + specId + ", description=" + description
				+ "]";
	}

}