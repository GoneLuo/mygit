package com.luoy.library.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luoy.library.common.util.ConstantsUtils;
import com.luoy.library.common.vo.TreeNodeVo;
import com.luoy.library.dao.ISortDao;
import com.luoy.library.dao.util.IBaseDao;
import com.luoy.library.pojo.Sort;
import com.luoy.library.service.IBookSetService;
import com.luoy.library.service.ISortService;
import com.luoy.library.service.util.BaseServiceImpl;

/**
 * 图书分类service
 * @author ying luo
 * @createDate 2018年4月9日
 */
@Service
@Transactional
public class SortServiceImpl extends BaseServiceImpl<Sort> implements ISortService {
	
	@Autowired
	private ISortDao sortDao;
	
	@Autowired
	private IBookSetService bookSetService;

	@Override
	public IBaseDao<Sort> getDao() {
		return (IBaseDao<Sort>)sortDao;
	}
	
	@Override
	public List<TreeNodeVo> getSortTree() {
		List<TreeNodeVo> nodeList = new ArrayList<>();
		
		Map<String, Object> params = new HashMap<>();
		List<Sort> sorts = sortDao.queryListByParams(params);
		if (sorts == null || sorts.isEmpty()) {
			return null;
		}
		TreeNodeVo node = null;
		for (Sort sort: sorts) {
			if (sort.getId().equals(ConstantsUtils.ROOT_SORT_ID)) { //总目录、根节点
				node = new TreeNodeVo();
				node.setName(sort.getSortName());
				node.setId(sort.getId());
				node.setParent(true);
				node.setOpen(true);
			} else {
				node = new TreeNodeVo();
				node.setName(sort.getSortName());
				node.setId(sort.getId());
				node.setParentId(sort.getParentId());
				node.setOpen(false);
				if (sort.getHasChild() != null && sort.getHasChild().equals(ConstantsUtils.JUDGE_YES)) {
					node.setParent(true);
				}
			}
			
			nodeList.add(node);
		}
		
		return nodeList;
	}

	@Override
	public String renameSort(String id, String name) {
		Sort sort = sortDao.get(id);
		if (sort != null) {
			sort.setSortName(name);
			sortDao.update(sort);
			
			return "修改成功";
		}
		
		return "该分类不存在";
	}

	@Override
	public Sort addSort(String sortName, String parentId) {
		Sort sort = new Sort();
		sort.setSortName(sortName);
		sort.setParentId(parentId);
		sort.setCreateTime(new Date());
		sort.setHasChild(ConstantsUtils.JUDGE_NOT);
		sortDao.save(sort);
		
		/**
		 * 父分类不是根节点时，修改其hasChild属性
		 */
		if (!parentId.equals("-1")) {
			Sort parentSort = sortDao.get(parentId);
			parentSort.setHasChild(ConstantsUtils.JUDGE_YES);
			sortDao.update(parentSort);
		}
		
		return sort;
	}

	@Override
	public boolean vailNameByInParent(Sort sort, String flag) {
		List<Sort> sorts = queryListByParentAndName(sort.getSortName(), sort.getParentId());
		if (sorts == null || sorts.isEmpty()) { //新名称没有被占用
			return true;
		}
		
		/**
		 * 1、修改前
		 * 新名称为原来的名称，没有修改，当然可用
		 * hibernate持久态对象修改名称时，对象update前数据库里的名称可能已经改变了
		 * 
		 * 2、新增分类后
		 * 有且只有一个该名称的分类，表示合法
		 */
		if (sorts.size() == 1 && sorts.get(0).getId().equals(sort.getId())) { 
			return true;
		}
		
		return false;
	}

	@Override
	public List<Sort> queryListByParentAndName(String sortName, String parentId) {
		Map<String, Object> params = new HashMap<>();
		params.put("sortName", sortName);
		params.put("parentId", parentId);
		
		return sortDao.queryListByParams(params);
	}

	@Override
	public void delSort(String id) {
		//TODO:删除节点时，该分类下的图书转移到其父分类下；判断父分类的hasChild属性
		Sort sort = sortDao.get(id);
		
		//1、该分类下的图书转移到其父分类下
		bookSetService.changeBookSort(sort.getId(), sort.getParentId());
		
		//2、删除分类
		sortDao.delete(sort);
		
		//3、判断父分类的hasChild属性是否需要修改
		Map<String, Object> params = new HashMap<>();
		params.put("parentId", sort.getParentId());
		List<Sort> childSort = sortDao.queryListByParams(params);
		if (childSort == null || childSort.isEmpty()) {
			Sort parentSort = sortDao.get(sort.getParentId());
			parentSort.setHasChild(ConstantsUtils.JUDGE_NOT);
			sortDao.update(parentSort);
		}
		
	}

	@Override
	public List<String> getAllCidByPid(String pid) {
		List<String> allCid = new ArrayList<>();
		
		/*
		 * 1、查询一级子节点
		 */
		List<Sort> childSort = null;
		Map<String, Object> params = new HashMap<>();
		if (StringUtils.isNoneBlank(pid)) {
			params.put("parentId", pid);
		}
		childSort = sortDao.queryListByParams(params);
		
		if (null == childSort || childSort.isEmpty()) {
			return null;
		}
		for (Sort sort: childSort) {
			/*
			 * 2、保存一级子节点id
			 */
			allCid.add(sort.getId());
			/*
			 * 3、保存一级子节点的所有子节点（递归）
			 */
			if (sort.getHasChild().equals(ConstantsUtils.JUDGE_YES)) {
				allCid.addAll(getAllCidByPid(sort.getId()));
			}
		}
		
		return allCid;
	}
}
