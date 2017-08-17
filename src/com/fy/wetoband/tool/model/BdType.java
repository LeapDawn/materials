package com.fy.wetoband.tool.model;

import java.util.Set;

public class BdType implements java.io.Serializable {

	private String typeId;   // 类型ID
	private BdType bdType;   // 父类类型(上级)
	private String description;  // 类型名称
	private int level;
	private Set<BdType> childTypes; // 子类类型(下级)
	
	public BdType() {
	}

	public BdType(String description) {
		super();
		this.description = description;
	}

	public BdType(String typeId, String description) {
		this.typeId = typeId;
		this.description = description;
	}
	
	
	public BdType(String typeId, String description, int level) {
		super();
		this.typeId = typeId;
		this.description = description;
		this.level = level;
	}

	public BdType(String typeId, BdType bdType, String description, int level) {
		super();
		this.typeId = typeId;
		this.bdType = bdType;
		this.description = description;
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getTypeId() {
		return this.typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public BdType getBdType() {
		return this.bdType;
	}

	public void setBdType(BdType bdType) {
		this.bdType = bdType;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Set<BdType> getChildTypes() {
		return childTypes;
	}

	public void setChildTypes(Set<BdType> childTypes) {
		this.childTypes = childTypes;
	}

	@Override
	public String toString() {
		return "BdType [typeId=" + typeId + ", bdType=" + bdType
				+ ", description=" + description + ", level=" + level + "]";
	}

}