package com.luoy.library.service.util;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import com.luoy.library.dao.util.IBaseDao;

/**
 * service基类，可以直接调用dao层的简单增删改查
 * 业务层的处理 业务层默认有访问数据库的能力 事务在这一层控制，所有的业务处理在这一层实现
 * @author ying luo
 * @createDate 2018年4月9日
 */
@Transactional
public abstract class BaseServiceImpl<T> {
	
	/**
	 * 获取数据访问对象
	 * @createUser ying luo
	 * @createDate 2018年4月9日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @return
	 */
	public abstract IBaseDao<T> getDao();
	
	public void save(T t) {
		getDao().save(t);
	}
	
	public void delete(T t) {
		getDao().delete(t);
	}
	
	public void delete(Serializable id) {
		getDao().delete(id);
	}
	
	public void update(T t) {
		getDao().update(t);
	}
	
	@Transactional(readOnly = true)
	public T get(Serializable id) {
		return getDao().get(id);
	}
}
