package com.luoy.library.service;

import java.util.List;

import com.luoy.library.pojo.SingleBook;
import com.luoy.library.service.util.IBaseService;

public interface ISingleBookService extends IBaseService<SingleBook>{
	/**
	 * 根据图书id查询单本图书列表
	 * @createUser ying luo
	 * @createDate 2018年4月5日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param bookSetId 图书id
	 */
	List<SingleBook> getListByBookSetId(String bookSetId);
	
	/**
	 * 根据图书id，查询可借的单本图书列表（非馆藏，未被借出）
	 * @createUser ying luo
	 * @createDate 2018年4月21日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param bookSetId 图书id
	 * @return
	 */
	List<SingleBook> getUsefulListByBookSet(String bookSetId);
}
