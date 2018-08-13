package com.luoy.library.dao;


import java.util.List;
import java.util.Map;

import com.luoy.library.common.util.PageConfig;
import com.luoy.library.dao.util.IBaseDao;
import com.luoy.library.pojo.BookSet;
import com.sun.webkit.PageCache;

public interface IBookSetDao extends IBaseDao<BookSet>{
	
	/**
	 * 【单表】条件查询图书列表
	 * @createUser ying luo
	 * @createDate 2018年4月4日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param bookSet 查询条件
	 * @param pageConfig 分页
	 * @return
	 */
	List<BookSet> queryListByParamsAndPage(BookSet bookSet, PageConfig pageConfig);
	
	/**
	 * 【单表】条件查询图书列表
	 * @createUser ying luo
	 * @createDate 2018年4月9日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param paramMap 查询条件
	 * @param pageConfig
	 * @return
	 */
	List<BookSet> queryListByParamsAndPage(Map<String, Object> paramMap, PageConfig pageConfig);
	
	/**
	 * 【多表】根据hql、查询参数和分页，条件查询图书列表
	 * ps:为了适应不同页面的查询条件，在service就封装hql
	 * @createUser ying luo
	 * @createDate 2018年4月9日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param hql
	 * @param params 查询参数
	 * @param pageConfig
	 * @return
	 */
	List<BookSet> queryListByHqlAndPage(String hql, Object[] params, PageConfig pageConfig);
	
	/**
	 * 将旧分类下的图书全部转移到新分类下
	 * @createUser ying luo
	 * @createDate 2018年4月12日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param oldSortId 旧分类id
	 * @param newSortId 新分类id
	 */
	void changeBookSort(String oldSortId, String newSortId);
	
	/**
	 * 根据分类id列表，查询图书列表，带分页
	 * @createUser ying luo
	 * @createDate 2018年4月14日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param sortIdList 分类id列表
	 * @return
	 */
	List<BookSet> queryListBySortList(List<String> sortIdList, PageConfig pageConfig);
	
}
