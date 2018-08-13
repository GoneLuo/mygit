package com.luoy.library.service;

import java.util.List;
import java.util.Map;

import com.luoy.library.common.util.PageConfig;
import com.luoy.library.common.vo.PunishLogVo;
import com.luoy.library.common.vo.TableVo;
import com.luoy.library.pojo.BorrowLog;
import com.luoy.library.pojo.PunishLog;
import com.luoy.library.service.util.IBaseService;

/**
 * 罚款记录service接口
 * @author ying luo
 * @createDate 2018年4月16日
 */
public interface IPunishLogService extends IBaseService<PunishLog> {
	/**
	 * 条件查询罚款记录列表vo，支持layui.table
	 * @createUser ying luo
	 * @createDate 2018年4月16日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param punishLogVo 查询条件
	 * @param pageConfig 分页
	 * @return
	 */
	TableVo<PunishLogVo> getListByParamsAndPage(PunishLogVo punishLogVo, PageConfig pageConfig);
	
	/**
	 * 根据借阅记录，生成或更新罚款记录
	 * @createUser ying luo
	 * @createDate 2018年4月16日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 *  
	 * @param borrowLog 借阅记录
	 */
	void createPunishLogByBorrowLog(BorrowLog borrowLog);
	
	/**
	 * 根据用户id，更新指定用户的违章记录
	 * @createUser ying luo
	 * @createDate 2018年4月17日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param userId 用户id
	 */
	void updateByUserId(String userId);
	
	/**
	 * 更新所有的违章记录
	 * @createUser ying luo
	 * @createDate 2018年4月20日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 *
	 */
	void updateAllPunish();
	
	/**
	 * 根据违章记录id，还款并还书
	 * @createUser ying luo
	 * @createDate 2018年4月20日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param punishLogId 违章记录id
	 */
	void returnDebtAndBook(String punishLogId);
	
	/**
	 * 根据借阅记录id，还款并还书
	 * @createUser ying luo
	 * @createDate 2018年4月20日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param borrowLogId 借阅记录id
	 * @return
	 */
	void returnDebtBook(String borrowLogId);
	
	/**
	 * 获取指定用户当前超期未还的图书
	 * @createUser ying luo
	 * @createDate 2018年4月20日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param userId
	 * @return
	 */
	Integer getCurCount(String userId);
}
