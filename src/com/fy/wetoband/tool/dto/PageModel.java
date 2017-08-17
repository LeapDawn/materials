package com.fy.wetoband.tool.dto;

import java.util.List;

public class PageModel {

	private int total; // 总记录数
	private int pages; // 总页数
	private int rows; // 每页记录数
	private int currentPage; // 当前页码
	private List<?> data;

	public PageModel() {

	}

	public PageModel(int total, int rows, int currentPage, List<?> data) {
		super();
		this.total = total > 0 ? total : 0;
		this.rows = rows > 0 ? rows : 10;
		this.pages = this.total % this.rows == 0 && this.total != 0 ? (this.total / this.rows)
				: (this.total / this.rows) + 1;
		this.currentPage = currentPage <= this.pages ? currentPage : this.pages;
		this.currentPage = this.currentPage > 0 ? this.currentPage : 1;
		this.data = data;
	}

	public PageModel(int total, int rows) {
		this.total = total > 0 ? total : 0;
		this.rows = rows > 0 ? rows : 10;
		pages = this.total % this.rows == 0 && this.total != 0 ? (this.total / this.rows)
				: (this.total / this.rows) + 1;
		this.currentPage = 1;
	}

	public PageModel(int total, int rows, int currentPage) {
		this.total = total > 0 ? total : 0;
		this.rows = rows > 0 ? rows : 10;
		this.pages = this.total % this.rows == 0 && this.total != 0 ? (this.total / this.rows)
				: (this.total / this.rows) + 1;
		this.currentPage = currentPage <= this.pages ? currentPage : this.pages;
		this.currentPage = this.currentPage > 0 ? this.currentPage : 1;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "PageModel [total=" + total + ", pages=" + pages + ", rows="
				+ rows + ", currentPage=" + currentPage + ", data=" + data
				+ "]";
	}

}
