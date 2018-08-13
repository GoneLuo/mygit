package com.luoy.library.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.luoy.library.common.util.PageConfig;
import com.luoy.library.dao.IUserDao;
import com.luoy.library.dao.util.BaseDaoImpl;
import com.luoy.library.pojo.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {

	@Override
	public List<User> queryUserBypParamMapAndPage(Map<String, Object> paramMap, PageConfig pageConfig) {
		StringBuffer hql = new StringBuffer("from User where 1=1");
		
		List params = new ArrayList<>();
		for(String key: paramMap.keySet()) {
			hql.append(" and " + key + "= ?" );
			params.add(paramMap.get(key));
		}
		
		return this.queryPage(hql.toString(), pageConfig, params.toArray());
	}

	@Override
	public List<User> queryUserBypParamMap(Map<String, Object> paramMap) {
		StringBuffer hql = new StringBuffer("from User where 1=1");
		
		List params = new ArrayList<>();
		for(String key: paramMap.keySet()) {
			hql.append(" and " + key + "= ?" );
			params.add(paramMap.get(key));
		}
		
		return this.query(hql.toString(), params.toArray());
	}

	@Override
	public List<User> queryUserBypParamMapAndPage(Map<String, Object> confirmParam, Map<String, Object> likeParam,
			PageConfig pageConfig) {
		StringBuffer hql = new StringBuffer("from User where 1=1");
		
		List params = new ArrayList<>();
		for(String key: confirmParam.keySet()) {
			hql.append(" and " + key + "= ?" );
			params.add(confirmParam.get(key));
		}
		for(String key: likeParam.keySet()) {
			hql.append(" and " + key + "= ?" );
			params.add(likeParam.get(key));
		}
		
		return this.queryPage(hql.toString(), pageConfig, params.toArray());
	}
	
	@Override
	public List<User> queryListByHqlAndPage(String hql, Object[] params, PageConfig pageConfig) {
		return queryPage(hql, pageConfig, params);
	}
	
}
