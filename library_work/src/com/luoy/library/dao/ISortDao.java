package com.luoy.library.dao;

import java.util.List;
import java.util.Map;

import com.luoy.library.dao.util.IBaseDao;
import com.luoy.library.pojo.Sort;

/**
 * 图书分类dao接口
 * @author ying luo
 * @createDate 2018年4月9日
 */
public interface ISortDao extends IBaseDao<Sort> {
	
	/**
	 * 【单表】条件查询图书分类列表
	 * @createUser ying luo
	 * @createDate 2018年4月10日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param paramMap 查询条件
	 * @return
	 */
	List<Sort> queryListByParams(Map<String, Object> paramMap);

}
