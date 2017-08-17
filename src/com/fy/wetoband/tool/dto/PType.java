package com.fy.wetoband.tool.dto;

public class PType {

	private String typeId; // 类型ID
	private String superType; // 父类类型(上级)
	private String description; // 类型名称
	private int level;   // 所属层级

	public PType() {
		super();
		this.superType = "";
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getSuperType() {
		return superType;
	}

	public void setSuperType(String superDescription) {
		this.superType = superDescription != null ? superDescription : "";
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
