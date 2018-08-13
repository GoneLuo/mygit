package com.luoy.library.service;

import java.util.List;

import com.luoy.library.common.vo.TreeNodeVo;
import com.luoy.library.pojo.Sort;
import com.luoy.library.service.util.IBaseService;

/**
 * 图书分类service接口
 * @author ying luo
 * @createDate 2018年4月9日
 */
public interface ISortService extends IBaseService<Sort> {
	
	/**
	 * 获取图书分类z-tree树节点
	 * @createUser ying luo
	 * @createDate 2018年4月10日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @return
	 */
	List<TreeNodeVo> getSortTree();
	
	/**
	 * 图书分类重命名
	 * @createUser ying luo
	 * @createDate 2018年4月11日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param id 图书分类id
	 * @param name 图书分类新名称
	 * @return 重命名结果提示信息
	 */
	String renameSort(String id, String name);

	/**
	 * 根据分类名称和父分类id，增加分类
	 * @createUser ying luo
	 * @createDate 2018年4月12日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param sortName 分类名称
	 * @param parentId 父分类id
	 * 
	 * @return 返回新增的分类
	 */
	Sort addSort(String sortName, String parentId);
	
	/**
	 * 同一父分类下，子分类的名称不可重复；
	 * 新增分类后或重命名分类前，判断指定分类的名称是否合法
	 * @createUser ying luo
	 * @createDate 2018年4月12日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param flag ConstantUtils.EDIT：修改、ConstantUtils.ADD：新增
	 * @param sort 图书分类
	 * @return true:其sortName不重复，合法；false：其sortName重复，不合法
	 */
	boolean vailNameByInParent(Sort sort, String flag);
	
	/**
	 * 根据父分类id和分类名称，查询分类列表
	 * @createUser ying luo
	 * @createDate 2018年4月12日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param sortName 分类名称
	 * @param parentId 父分类id
	 * @return 类列表
	 */
	List<Sort> queryListByParentAndName(String sortName, String parentId);
	
	/**
	 * 删除指定id的分类
	 * @createUser ying luo
	 * @createDate 2018年4月12日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param id
	 */
	void delSort(String id);
	
	/**
	 * 根据父分类id，递归查询所有子分类的id（多级）
	 * @createUser ying luo
	 * @createDate 2018年4月14日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param 父分类id
	 * @return
	 */
	List<String> getAllCidByPid(String pid);
}
