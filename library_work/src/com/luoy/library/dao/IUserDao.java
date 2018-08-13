package com.luoy.library.dao;

import java.util.List;
import java.util.Map;

import com.luoy.library.common.util.PageConfig;
import com.luoy.library.dao.util.IBaseDao;
import com.luoy.library.pojo.User;

public interface IUserDao extends IBaseDao<User> {
	
	/**
	 * 单表条件查询用户列表，带分页【准确/模糊混合查询】
	 * @createUser ying luo
	 * @createDate 2018年3月29日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param confirmParam 准确查询的属性条件
	 * @param likeParam 模糊查询的属性条件
	 * @param pageConfig 分页
	 * @return 用户列表
	 */
	List<User> queryUserBypParamMapAndPage(Map<String, Object> confirmParam, Map<String, Object> likeParam, PageConfig pageConfig);
	
	/**
	 * 单表条件查询用户列表，带分页【准确查询】
	 * @createUser ying luo
	 * @createDate 2018年3月29日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param paramMap 查询用户的属性条件
	 * @param pageConfig 分页
	 * @return 用户列表
	 */
	List<User> queryUserBypParamMapAndPage(Map<String, Object> paramMap, PageConfig pageConfig);
	
	/**
	 * 单表条件查询用户列表【准确查询】
	 * @createUser ying luo
	 * @createDate 2018年3月29日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param paramMap 查询用户的属性条件
	 * @return 用户列表
	 */
	List<User> queryUserBypParamMap(Map<String, Object> paramMap);
	
	/**
	 * 【多表】根据hql、查询参数和分页，条件查询用户列表
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
	List<User> queryListByHqlAndPage(String hql, Object[] params, PageConfig pageConfig);
	
}