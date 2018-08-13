package com.luoy.library.service.util;

import java.io.Serializable;

/**
 * service基类接口，在service层接口继承
 * @author ying luo
 * @createDate 2018年4月9日
 */
public interface IBaseService<T> {

	void save(T t);
	
	void delete(T t);
	
	void delete(Serializable id);
	
	void update(T t);
	
	T get(Serializable id);
}
