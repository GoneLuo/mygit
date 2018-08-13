package com.luoy.library.dao.util;

import java.io.Serializable;

/**
 * dao层基类接口，在dao层接口继承
 * @author ying luo
 * @createDate 2018年4月9日
 * @param <T>
 */
public interface IBaseDao<T> {
	
	void save(T t);
	
	void delete(T t);
	
	void delete(Serializable id);
	
	void update(T t);
	
	T get(Serializable id);
}
