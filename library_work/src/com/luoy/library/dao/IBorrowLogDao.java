package com.luoy.library.dao;

import java.util.List;
import java.util.Map;

import com.luoy.library.common.util.PageConfig;
import com.luoy.library.common.vo.BorrowLogVo;
import com.luoy.library.dao.util.IBaseDao;
import com.luoy.library.pojo.BorrowLog;

public interface IBorrowLogDao extends IBaseDao<BorrowLog> {
	
	/**
	 * 单表条件查询借书记录，带分页
	 * @createUser ying luo
	 * @createDate 2018年4月5日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param pageConfig
	 * @param params 查询条件
	 * @return
	 */
	List<BorrowLog> queryListByParams(Map<String, Object> params, PageConfig pageConfig);
	
	/**
	 * 多表条件查询借阅记录列表，并分页
	 * @createUser ying luo
	 * @createDate 2018年4月7日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param borrowLogVo 查询条件
	 * @param pageConfig 
	 * @return
	 */
	List<BorrowLog> queryListByParams(BorrowLogVo borrowLogVo, PageConfig pageConfig);

}
