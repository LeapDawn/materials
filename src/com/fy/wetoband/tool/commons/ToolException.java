package com.fy.wetoband.tool.commons;

public class ToolException extends Exception {
	
	private String message;
	
	public ToolException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
