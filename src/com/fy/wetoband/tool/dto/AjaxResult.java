/**
 * 
 */
package com.fy.wetoband.tool.dto;

/**
 * ajax请求返回结果
 */
public class AjaxResult<T> {
	
	private boolean state;   // 请求是否成功
	private T value;        // 返回对象
	
	public boolean isState() {
		return state;
	}


	public void setState(boolean state) {
		this.state = state;
	}


	public T getValue() {
		return value;
	}


	public void setValue(T value) {
		this.value = value;
	}

	public AjaxResult(boolean state, T result) {
		super();
		this.state = state;
		this.value = result;
	}
	
	@Override
	public String toString() {
		return "AjaxResult [state=" + state + ", value=" + value + "]";
	}
}
