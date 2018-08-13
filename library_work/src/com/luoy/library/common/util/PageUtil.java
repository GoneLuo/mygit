package com.luoy.library.common.util;

import java.util.List;

import com.luoy.library.common.vo.PageVo;

/**
 * 
 * @author hexin
 * @createDate 2018年1月23日
 * 分页工具类
 */
public final class PageUtil {
	
	private PageUtil() {
		
	}
	
	/**
	 * 
	 * @createUser hexin
	 * @createDate 2018年1月23日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param page  分页
	 * @param list  数据集合
	 * @return
	 * 返回分页集合
	 */
	public static PageVo getPageList(PageConfig page, List list) {
		PageVo pageVo = new PageVo();
		pageVo.setDataList(list);
		pageVo.setPageNum(page.getPageNum());
		pageVo.setPageSize(page.getPageSize());
		pageVo.setDataCount(page.getRowCount());
		pageVo.setPageCount(page.getPageCount());
		pageVo.setHasPrePage(page.getPageNum() > 1);
		if (pageVo.getHasPrePage()) {
			pageVo.setPrePage(page.getPageNum() - 1);
		}
		pageVo.setHasNextPage(page.getPageNum() < pageVo.getPageCount());
		if (pageVo.getHasNextPage()) {
			pageVo.setNextPage(page.getPageNum() + 1);
		}
		return pageVo;
	}
}


