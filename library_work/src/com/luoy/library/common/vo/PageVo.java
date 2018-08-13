package com.luoy.library.common.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 分页处理类
 *
 * @param <E> 分页处理类
 * @author hexin
 * @createDate 2018年1月23日
 */
public class PageVo<E> implements Serializable {

    /**
     * 数据
     */
    private List<E> dataList;

    /**
     * 数据数量
     */
    private Integer dataCount;

    /**
     * 页面数
     */
    private Integer pageCount;

    /**
     * 当前页号
     */
    private Integer pageNum;
    
    /**
     * 每页显示记录
     */
    private Integer pageSize;

    public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
     * 是否有下一页
     */
    private Boolean hasNextPage;

    /**
     * 下一页
     */
    private Integer nextPage;
    /**
     * 是否有前一页
     */
    private Boolean hasPrePage;

    /**
     * 上一页
     */
    private Integer prePage;

    public List<E> getDataList() {
        return dataList;
    }

    public void setDataList(List<E> dataList) {
        this.dataList = dataList;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Boolean getHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(Boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }

    public Boolean getHasPrePage() {
        return hasPrePage;
    }

    public void setHasPrePage(Boolean hasPrePage) {
        this.hasPrePage = hasPrePage;
    }

    public Integer getPrePage() {
        return prePage;
    }

    public void setPrePage(Integer prePage) {
        this.prePage = prePage;
    }

    public Integer getDataCount() {
        return dataCount;
    }

    public void setDataCount(Integer dataCount) {
        this.dataCount = dataCount;
    }
}

