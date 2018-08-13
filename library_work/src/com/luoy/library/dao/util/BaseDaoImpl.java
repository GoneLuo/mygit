package com.luoy.library.dao.util;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.luoy.library.common.util.ConstantsUtils;
import com.luoy.library.common.util.PageConfig;
import com.luoy.library.common.util.PageList;

/**
 * 数据库访问基类
 * @author ying luo
 * @createDate 2018年3月28日
 * @param <T>
 */
@Repository
public abstract class BaseDaoImpl<T> {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	//泛型T的class，类似于Integer.class
	private Class<T> classT = null;
	
	public BaseDaoImpl() {
		
		/**
		 * 获取泛型T的class
		 */
		Type sType = getClass().getGenericSuperclass();
		Type[] generics = ((ParameterizedType) sType).getActualTypeArguments();
		classT = (Class<T>) (generics[0]);
	}
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public T get(Serializable id) {
		T t = (T) this.getSession().get(classT, id);
		return t;
	}
	
	public void save(T t) {
		this.getSession().save(t);
	}
	
	public void delete(T t) {
		this.getSession().delete(t);
	}
	
	public void delete(Serializable id) {
		T t = this.get(id);
		this.delete(t);
	}
	
	public void update(T t) {
		this.getSession().update(t);
	}
	
	/**
	 * 使用hql查询对象(任意条件)
	 * 
	 * @author LYZ
	 * @param hql
	 * @param params
	 * @return
	 */
	public T getPojo(String hql, Object[] params) {
		T t = null;
		Query query = getSession().createQuery(hql);
		int j = 0;
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				if (params[i] != null && !"".equals(params[i])) {
					query.setParameter(j, params[i]);
					j++;
				}
			}
		}
		List<T> list = query.list();
		if (list == null || list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}
	
	/**
	 * 查询带查询条
	 * 
	 * @param hql
	 * @param params
	 *            查询的条
	 * @return
	 */
	public List<T> query(String hql, Object[] params) {
		List<T> rows = null;
		
		/**
		 * org.hibernate.Query，参数从0开始
		 * javax.persistence.Query，参数从1开始
		 * 这里改成0
		 */
		int j = 0; 
		try {
			Query query = getSession().createQuery(hql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					if (params[i] != null && !"".equals(params[i])) {
						query.setParameter(j, params[i]);
						j++;
					}
				}
			}

			rows = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rows;
	}
	
	/**
	 * 查询总数带查询条
	 * 
	 * @param hql
	 * @param args
	 */
	public Integer queryCount(String hql, Object[] params) {
		int j = 0;
		Query query = getSession().createQuery(hql);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				if (params[i] != null && !"".equals(params[i])) {
					query.setParameter(j, params[i]);
					j++;
				}
			}
		}
		long count = (Long) query.uniqueResult();

		return (int)count;
	}
	
	/**
	 * 分页查询带查询条
	 * 
	 * @param hql
	 * @param pageConfig
	 *            分页通用
	 * @param params
	 *            查询的条
	 * @return
	 */
	public List<T> queryPage(String hql, PageConfig pageConfig, Object[] params) {
		if (pageConfig == null)
			pageConfig = new PageConfig();
		List<T> list = null;
		PageList<T> pageList = null;
		int currRowNum = (pageConfig.getPageNum() - 1)
				* pageConfig.getPageSize();
		int j = 0;
		try {
			Query query = getSession().createQuery(hql);

			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					if (params[i] != null && !"".equals(params[i])) {
						query.setParameter(j, params[i]);
						j++;
					}
				}
			}
			query.setFirstResult(currRowNum);
			query.setMaxResults(pageConfig.getPageSize()); // 每页显示多少页
			list = query.list();
			// 查询总记录数
			int rowCount = queryCount("select count(*) " + ConstantsUtils.hqlSubStr(hql), params);
			int pageCount = (rowCount + pageConfig.getPageSize() - 1)
					/ pageConfig.getPageSize();

			// 这里对pageConfig进行设置pageCount和total值，再把它设置给pageList，是因为在前后端分离部署的情况下，作为地址传递的参数pageConfig是无法从data或service层传回给controller层的，只有在前后端都部署在同个JVM的情况下，设置pageConfig的pageCount和total值才有用（才能作为地址传递的参数，在controller层读取到该值）。所以这里增加一个pageList来保存pageCount和total值，并使用return的方式来返回给controller层
			pageConfig.setRowCount(rowCount);
			pageConfig.setPageCount(pageCount);

			pageList = new PageList<T>(list, pageConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageList.getResulstList();
	}

}
