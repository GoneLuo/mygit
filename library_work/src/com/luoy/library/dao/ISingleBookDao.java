package com.luoy.library.dao;

import java.util.List;
import java.util.Map;

import com.luoy.library.dao.util.IBaseDao;
import com.luoy.library.pojo.SingleBook;

public interface ISingleBookDao extends IBaseDao<SingleBook> {
	
	/**
	 * 条件查询单本图书列表
	 * @createUser ying luo
	 * @createDate 2018年4月5日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param singleBook 查询条件
	 * @return
	 */
	List<SingleBook> queryListByParams(SingleBook singleBook);
	
	/**
	 * 条件查询单本图书列表
	 * @createUser ying luo
	 * @createDate 2018年4月5日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param params 查询条件
	 * @return
	 */
	List<SingleBook> queryListByParams(Map<String, Object> params);
	
}
