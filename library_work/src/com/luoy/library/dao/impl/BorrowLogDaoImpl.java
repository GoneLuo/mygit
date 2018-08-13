package com.luoy.library.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.luoy.library.common.util.PageConfig;
import com.luoy.library.common.vo.BorrowLogVo;
import com.luoy.library.dao.IBorrowLogDao;
import com.luoy.library.dao.util.BaseDaoImpl;
import com.luoy.library.pojo.BorrowLog;

@Repository
public class BorrowLogDaoImpl extends BaseDaoImpl<BorrowLog> implements IBorrowLogDao {

	@Override
	public List<BorrowLog> queryListByParams(Map<String, Object> params, PageConfig pageConfig) {
		StringBuffer hql = new StringBuffer("from BorrowLog where 1=1");
		
		List<Object> paramList = new ArrayList<>();
		for (String key: params.keySet()) {
			hql.append(" and " + key + "=?");
			paramList.add(params.get(key));
		}
		
		return this.queryPage(hql.toString(), pageConfig, paramList.toArray());
	}

	@Override
	public List<BorrowLog> queryListByParams(BorrowLogVo borrowLogVo, PageConfig pageConfig) {
		StringBuffer hql = new StringBuffer("from BorrowLog where 1=1");
		
		List<Object> paramList = new ArrayList<>();
		//TODO:根据前端页面设计，查找borrowLogVo中对应的条件
		
		return this.queryPage(hql.toString(), pageConfig, paramList.toArray());
	}

}
