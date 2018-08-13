package com.luoy.library.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.luoy.library.dao.ISortDao;
import com.luoy.library.dao.util.BaseDaoImpl;
import com.luoy.library.pojo.Sort;

/**
 * 图书分类dao
 * @author ying luo
 * @createDate 2018年4月9日
 */
@Repository
public class SortDaoImpl extends BaseDaoImpl<Sort> implements ISortDao {

	@Override
	public List<Sort> queryListByParams(Map<String, Object> paramMap) {
		StringBuffer hql = new StringBuffer("from Sort where 1=1");
		
		List<Object> params = new ArrayList<>();
		if (paramMap != null) {
			for (String key: paramMap.keySet()) {
				params.add(paramMap.get(key));
				hql.append(" and " + key + " = ?");
			}
		}
		List<Sort> sorts = this.query(hql.toString(), params.toArray());
		return sorts;
	}

}
