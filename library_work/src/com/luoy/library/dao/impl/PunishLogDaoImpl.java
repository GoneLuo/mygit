package com.luoy.library.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.luoy.library.common.util.PageConfig;
import com.luoy.library.dao.IPunishLogDao;
import com.luoy.library.dao.util.BaseDaoImpl;
import com.luoy.library.pojo.PunishLog;

/**
 * 罚款记录dao
 * @author ying luo
 * @createDate 2018年4月16日
 */
@Repository
public class PunishLogDaoImpl extends BaseDaoImpl<PunishLog> implements IPunishLogDao {

	@Override
	public List<PunishLog> queryListByParamsAndPage(Map<String, Object> paramMap, PageConfig pageConfig) {
		StringBuffer hql = new StringBuffer("from PunishLog where 1=1");
		
		List<Object> params = new ArrayList<>();
		for (String key: paramMap.keySet()) {
			hql.append(" and " + key + " = ?");
			params.add(paramMap.get(key));
		}
		
		return this.queryPage(hql.toString(), pageConfig, params.toArray());
	}

	@Override
	public List<PunishLog> queryListByParams(Map<String, Object> paramMap) {
		StringBuffer hql = new StringBuffer("from PunishLog where 1=1");
		
		List<Object> params = new ArrayList<>();
		for (String key: paramMap.keySet()) {
			hql.append(" and " + key + " = ?");
			params.add(paramMap.get(key));
		}
		
		return this.query(hql.toString(), params.toArray());
	}

}
