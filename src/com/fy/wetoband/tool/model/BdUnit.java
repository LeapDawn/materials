package com.fy.wetoband.tool.model;

public class BdUnit implements java.io.Serializable {

	private String unitId;
	private String description;

	public BdUnit() {

	}

	public BdUnit(String unitId, String description) {
		this.unitId = unitId;
		this.description = description;
	}

	public BdUnit(String description) {
		super();
		this.description = description;
	}

	public String getUnitId() {
		return this.unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "BdUnit [unitId=" + unitId + ", description=" + description
				+ "]";
	}

}