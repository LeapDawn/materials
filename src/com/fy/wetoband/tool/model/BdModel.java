package com.fy.wetoband.tool.model;


public class BdModel implements java.io.Serializable {


	private String modelId;
	private String description;
	
	public BdModel() {
	}

	public BdModel(String description) {
		super();
		this.description = description;
	}

	public BdModel(String modelId, String description) {
		this.modelId = modelId;
		this.description = description;
	}

	public String getModelId() {
		return this.modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "BdModel [modelId=" + modelId + ", description=" + description
				+ "]";
	}

}