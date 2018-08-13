package com.luoy.library.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luoy.library.common.util.ConstantsUtils;
import com.luoy.library.common.util.Result;
import com.luoy.library.common.vo.TreeNodeVo;
import com.luoy.library.pojo.Sort;
import com.luoy.library.service.ISortService;

/**
 * 图书分类controller
 * @author ying luo
 * @createDate 2018年4月10日
 */
@Controller
@RequestMapping("/sort/")
public class SortController {
	
	@Autowired
	private ISortService sortService;
	
	/*@RequestMapping("/test")
	public void test(String pid) {
		sortService.test(pid);
	}*/
	
	/**
	 * 获取图书分类z-tree树节点
	 * @createUser ying luo
	 * @createDate 2018年4月11日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @return
	 */
	@RequestMapping("/getSortTree")
	@ResponseBody
	public List<TreeNodeVo> getSortTree() {
		return sortService.getSortTree();
	}
	
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
	 * @return
	 */
	@RequestMapping("/admin/editName") 
	@ResponseBody
	public Result renameSort(String id, String name) {
		Result result = new Result<>();
		
		if (StringUtils.isBlank(id) || StringUtils.isBlank(name)) {
			result.setStatus(false);
			result.setMsg("参数错误");
			return result;
		}
		
		//重命名前想验证新名称name是否可用
		Sort sort = sortService.get(id);
		if (sort.getParentId() != null && !sortService.vailNameByInParent(sort, ConstantsUtils.EDIT)) {
			result.setStatus(false);
			result.setMsg("新名称：" + name + "在该父分类中已被使用，请重新设置！");
			return result;
		}
		
		result.setStatus(true);
		result.setMsg(sortService.renameSort(id, name));
		
		return result;
	}
	
	@RequestMapping("/admin/delSort")
	@ResponseBody
	public Result delSort(String id) {
		Result result = new Result<>();
		
		if (StringUtils.isBlank(id)) {
			result.setStatus(false);
			result.setMsg("参数错误");
			return result;
		}
		
		//TODO:删除节点时，该分类下的图书转移到其父分类下；判断父分类的hasChild属性
//		sortService.delete(id);
		sortService.delSort(id);
		result.setStatus(true);
		result.setMsg(ConstantsUtils.DEL_SUCCESS_MSG);
		
		return result;
	}
	
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
	 * @return 结果：成功失败、提示信息、成功时新增的分类
	 */
	@RequestMapping("/admin/addSort")
	@ResponseBody
	public Result<Sort> addSort(String sortName, String parentId) {
		Result<Sort> result = new Result<>();
		
		if (StringUtils.isBlank(sortName) || StringUtils.isBlank(parentId)) {
			result.setStatus(false);
			result.setMsg("参数错误");
			return result;
		}
		
		Sort sort = sortService.addSort(sortName, parentId);
		if (sort != null) {
			result.setStatus(true);
			result.setMsg(ConstantsUtils.SAVE_SUCCESS_MSG);
			result.setData(sort);
		} else {
			result.setStatus(false);
			result.setMsg(ConstantsUtils.SAVE_FAIL_MSG);
		}
		
		return result;
	}
	
	/**
	 * 新增分类后，判断指定分类的名称是否合法
	 * @createUser ying luo
	 * @createDate 2018年4月12日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param sort
	 * @return
	 */
	@RequestMapping("/admin/valiNameAfterAdd")
	@ResponseBody
	public Result valiNameAfterAdd(Sort sort) {
		Result result = new Result<>();
		
		if (sort == null || StringUtils.isBlank(sort.getSortName()) || StringUtils.isBlank(sort.getParentId())) {
			result.setStatus(false);
			result.setMsg(ConstantsUtils.PARAM_ERROR_MSG);
			return result;
		}
		if (!sortService.vailNameByInParent(sort, ConstantsUtils.ADD)) {
			result.setStatus(false);
			result.setMsg("新名称：" + sort.getSortName() + "在该父分类中已被使用，请重新设置！");
			return result;
		}
		
		result.setStatus(true);
		result.setMsg(ConstantsUtils.SUCCESS_MSG);
		
		return result;
	}
}
