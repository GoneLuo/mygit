package com.luoy.library.service;

import java.util.List;

import com.luoy.library.common.util.PageConfig;
import com.luoy.library.common.vo.BookSetVo;
import com.luoy.library.common.vo.PageVo;
import com.luoy.library.common.vo.TableVo;
import com.luoy.library.pojo.BookSet;
import com.luoy.library.service.util.IBaseService;

public interface IBookSetService extends IBaseService<BookSet> {
	/**
	 * 条件查询图书，带分页；支持layui.table
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
	TableVo<BookSetVo> getTableVoByParamsAndPage(BookSet bookSet, PageConfig pageConfig);
	
	/**
	 * 【首页】条件查询图书，带分页；支持首页图书eshore分页
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
	PageVo<BookSetVo> getPageVoByParamsAndPage(BookSetVo bookSetVo, PageConfig pageConfig);
	
	/**
	 * 增加图书，同时增加单本图书SingleBook
	 * @createUser ying luo
	 * @createDate 2018年4月9日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param bookSet
	 */
	void addBookSet(BookSet bookSet);
	
	/**
	 * 修改图书，同时修改单本图书SingleBook
	 * @createUser ying luo
	 * @createDate 2018年4月9日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param bookSet
	 */
	void changeBookSet(BookSet bookSet);
	
	/**
	 * 根据图书id，查询图书vo
	 * @createUser ying luo
	 * @createDate 2018年4月9日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param id
	 * @return
	 */
	BookSetVo getVoById(String id);
	
	/**
	 * 【图书管理页面】条件查询图书，带分页；layui.table
	 * @createUser ying luo
	 * @createDate 2018年4月9日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param bookSet 查询条件
	 * @param pageConfig 分页
	 * @return
	 */
	TableVo<BookSetVo> getTableVoByParamsAndPage(BookSetVo bookSetVo, PageConfig pageConfig);
	
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
	 * 查询指定分类（包括其所有子分类）下的图书，分页
	 * @createUser ying luo
	 * @createDate 2018年4月12日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param sortId 分类id
	 * @return
	 */
	PageVo<BookSetVo> queryListBySort(String sortId, PageConfig pageConfig);
	
	/**
	 * 通过editFlag判断是修改图书或增加图书，验证ISBN是否合法（即ISBN不能重复）
	 * @createUser ying luo
	 * @createDate 2018年4月21日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param isbn
	 * @param editFlag 修改图书或增加图书
	 * @return true:合法；false：不合法
	 */
	boolean valiISBN(String isbn, String editFlag);
	
	/**
	 * 根据isbn查找图书
	 * @createUser ying luo
	 * @createDate 2018年4月21日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param isbn
	 * @return
	 */
	BookSet getByIsbn(String isbn);
	
	/**
	 * 根据图书isbn和单本图书在图书中的序号，生成单本图书的条形码
	 * @createUser ying luo
	 * @createDate 2018年4月22日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param isbn 图书isbn
	 * @param no 单本图书在图书中的序号
	 * @return 单本图书的条形码
	 */
	String genericBarcode(String isbn, String no);
}
