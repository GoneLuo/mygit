package com.luoy.library.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.luoy.library.dao.ISingleBookDao;
import com.luoy.library.dao.util.BaseDaoImpl;
import com.luoy.library.pojo.SingleBook;

@Repository
public class SingleBookDaoImpl extends BaseDaoImpl<SingleBook> implements ISingleBookDao {

	@Override
	public List<SingleBook> queryListByParams(SingleBook singleBook) {
		//TODO: 完善或删除
		
		return this.query("from SingleBook", new Object[]{});
	}

	@Override
	public List<SingleBook> queryListByParams(Map<String, Object> params) {
		StringBuffer hql = new StringBuffer("from SingleBook where 1=1");
		
		List<Object> param = new ArrayList<>();
		for (String key: params.keySet()) {
			hql.append(" and " + key + "=?");
			param.add(params.get(key));
		}
		
		return this.query(hql.toString(), param.toArray());
	}

}
