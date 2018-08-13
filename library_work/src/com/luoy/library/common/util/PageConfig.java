package com.luoy.library.common.util;

import java.io.Serializable;

public class PageConfig implements Serializable {

	private static final long serialVersionUID = 862394223766206144L;

	private int pageIndex;
	private int rowCount; // 总记录数

	private int pageSize = 10; // 每页显示记录

	private int pageNum = 1; // 当前页号

	private int pageCount; // 总页

	public PageConfig() {
		super();
	}

	public PageConfig(int rowCount, int pageSize, int pageNum, int pageCount,
			int currRowNum, int startIndex, int endIndex, int pageCode,
			int nextPage, int previousPage) {
		super();
		this.rowCount = rowCount;
		this.pageSize = pageSize;
		this.pageNum = pageNum;
		this.pageCount = pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount; // 计算总页
	}

	public int getPageNum() {
		if(pageNum<1)
			pageNum=pageIndex+1;
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageCount() {
	    return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

}

