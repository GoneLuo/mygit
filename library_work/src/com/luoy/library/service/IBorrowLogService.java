package com.luoy.library.service;

import java.util.Date;
import java.util.List;

import com.luoy.library.common.util.PageConfig;
import com.luoy.library.common.vo.BorrowLogVo;
import com.luoy.library.common.vo.TableVo;
import com.luoy.library.pojo.BorrowLog;
import com.luoy.library.service.util.IBaseService;

public interface IBorrowLogService extends IBaseService<BorrowLog> {
	
	/**
	 * 根据用户id和单本图书id，借书时新增一条借书记录
	 * @createUser ying luo
	 * @createDate 2018年4月5日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 *  
	 * @param userId 用户id
	 * @param singleBookId 单本图书id
	 */
	void addWhenBorrow(String userId, String singleBookId);
	
	/**
	 * 根据用户id和单本图书id，还书时修改借书记录
	 * @createUser ying luo
	 * @createDate 2018年4月5日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param borrowLog
	 */
	void changeWhenReturn(String userId, String singleBookId);
	
	/**
	 * 查询指定用户的所有借书记录，并返回其BorrowLogVo，支持layui.table渲染
	 * @createUser ying luo
	 * @createDate 2018年4月5日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param pageConfig
	 * @param userId
	 * @return
	 */
	TableVo<BorrowLogVo> getAllListByUserId(String userId, PageConfig pageConfig);
	
	/**
	 * 查询指定用户的未还书借阅记录 ，支持分页
	 * @createUser ying luo
	 * @createDate 2018年4月17日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param userId 用户id
	 * @param pageConfig 分页
	 * @return
	 */
	List<BorrowLog> getNoReturnList(String userId, PageConfig pageConfig);
	
	/**
	 * 查询所有的未还书记录
	 * @createUser ying luo
	 * @createDate 2018年4月20日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @return
	 */
	List<BorrowLog> getAllNoReturnList();
	
	/**
	 * 查询指定用户的当前借书记录，并返回其BorrowLogVo，支持layui.table渲染
	 * @createUser ying luo
	 * @createDate 2018年4月5日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param pageConfig
	 * @param userId
	 * @return
	 */
	TableVo<BorrowLogVo> getCurListByUserId(String userId, PageConfig pageConfig);
	
	/**
	 * 借书，指定用户和单本图书id
	 * @createUser ying luo
	 * @createDate 2018年4月5日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param userId 用户id
	 * @param singleBookId 单本图书id
	 */
	boolean borrowBook(String userId, String singleBookId);
	
	/**
	 * 还书，指定用户和单本图书id
	 * @createUser ying luo
	 * @createDate 2018年4月5日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param userId 用户id
	 * @param singleBookId 单本图书id
	 */
	boolean returnBook(String userId, String singleBookId);
	
	/**
	 * 借书时，获取期望还书时间
	 * 期望归还时间，往后顺延ConstantsUtils.BORROW_MONTH个月
	 * @createUser ying luo
	 * @createDate 2018年4月7日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @return
	 */
	Date getExpectReturnTime();
	
	/**
	 * 条件查询借阅记录，支持layui.table渲染
	 * @createUser ying luo
	 * @createDate 2018年4月7日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param pageConfig
	 * @param borrowLogVo 查询条件
	 * @return
	 */
	TableVo<BorrowLogVo> findListByParams(PageConfig pageConfig, BorrowLogVo borrowLogVo);
	
	/**
	 * 根据用户id和单本图书id，找到"未还"的借书记录
	 * @createUser ying luo
	 * @createDate 2018年4月7日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param userId 用户id
	 * @param singleBookId 单本图书id
	 * @return
	 */
	BorrowLog queryByUserAndSingleBook(String userId, String singleBookId);
	
}
