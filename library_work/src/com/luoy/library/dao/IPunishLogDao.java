package com.luoy.library.dao;

import java.util.List;
import java.util.Map;

import com.luoy.library.common.util.PageConfig;
import com.luoy.library.dao.util.IBaseDao;
import com.luoy.library.pojo.PunishLog;

/**
 * 罚款记录dao接口
 * @author ying luo
 * @createDate 2018年4月16日
 */
public interface IPunishLogDao extends IBaseDao<PunishLog> {

	/**
	 * 【单表】条件查询罚款记录列表，带分页
	 * @createUser ying luo
	 * @createDate 2018年4月16日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param paramMap 查询条件
	 * @param pageConfig 分页
	 * @return
	 */
	List<PunishLog> queryListByParamsAndPage(Map<String, Object> paramMap, PageConfig pageConfig);
	
	/**
	 * 【单表】条件查询罚款记录列表，不带分页
	 * @createUser ying luo
	 * @createDate 2018年4月16日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param paramMap 查询条件
	 * @return
	 */
	List<PunishLog> queryListByParams(Map<String, Object> paramMap);
}
