package com.luoy.library.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.luoy.library.common.util.ConstantsUtils;
import com.luoy.library.common.util.PageConfig;
import com.luoy.library.dao.IBookSetDao;
import com.luoy.library.dao.util.BaseDaoImpl;
import com.luoy.library.pojo.BookSet;

import sun.swing.StringUIClientPropertyKey;

@Repository
public class BookSetDaoImpl extends BaseDaoImpl<BookSet> implements IBookSetDao {

	@Override
	public List<BookSet> queryListByParamsAndPage(BookSet bookSet, PageConfig pageConfig) {
		StringBuffer hql = new StringBuffer("from BookSet where 1=1");
		
		List<Object> params = new ArrayList<>();
		//TODO
		
		return this.queryPage(hql.toString(), pageConfig, params.toArray());
	}

	@Override
	public List<BookSet> queryListByParamsAndPage(Map<String, Object> paramMap, PageConfig pageConfig) {
		StringBuffer hql = new StringBuffer("from BookSet where 1=1");
		
		List<Object> params = new ArrayList<>();
		for (String key: paramMap.keySet()) {
			hql.append(" and " + key + " = ?");
			params.add(paramMap.get(key));
		}
		
		return this.queryPage(hql.toString(), pageConfig, params.toArray());
	}

	@Override
	public List<BookSet> queryListByHqlAndPage(String hql, Object[] params, PageConfig pageConfig) {
		return queryPage(hql, pageConfig, params);
	}

	@Override
	public void changeBookSort(String oldSortId, String newSortId) {
		if (StringUtils.isBlank(oldSortId) || StringUtils.isBlank(newSortId)) {
			return ;
		}
		
		String hql = "UPDATE BookSet SET sortId = ? WHERE sortId = ?";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, newSortId);
		query.setParameter(1, oldSortId);
		
		query.executeUpdate();
	}

	@Override
	public List<BookSet> queryListBySortList(List<String> sortIdList, PageConfig pageConfig) {
		StringBuffer hql = new StringBuffer("from BookSet");
		
		if (sortIdList != null && !sortIdList.isEmpty()) {
			String sortIds="";
			for (String sortId : sortIdList) {
				if(StringUtils.isNotEmpty(sortId)) {
					sortIds += "'"+sortId+"',";
				}
			}
			if (sortIds.contains(",")) {
				sortIds = sortIds.substring(0, sortIds.lastIndexOf(','));
			}
			
			hql.append(" where sortId in(" + sortIds + ")");
		}
		
		hql.append("and status = ?");
		
		return this.queryPage(hql.toString(), pageConfig, new Object[]{ConstantsUtils.JUDGE_YES});
	}

}
