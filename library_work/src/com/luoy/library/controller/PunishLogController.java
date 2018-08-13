package com.luoy.library.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.luoy.library.common.util.ConstantsUtils;
import com.luoy.library.common.util.PageConfig;
import com.luoy.library.common.util.Result;
import com.luoy.library.common.vo.PunishLogVo;
import com.luoy.library.common.vo.TableVo;
import com.luoy.library.common.vo.UserVo;
import com.luoy.library.pojo.PunishLog;
import com.luoy.library.service.IPunishLogService;

/**
 * 罚款记录controller
 * @author ying luo
 * @createDate 2018年4月16日
 */
@Controller
@RequestMapping("/punishLog")
public class PunishLogController {
	@Autowired
	private IPunishLogService punishLogService;

	/**
	 * 条件查询当前用户的违章记录vo，支持layui.table
	 * @createUser ying luo
	 * @createDate 2018年4月17日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param session
	 * @param pageConfig 分页
	 * @param punishLogVo 查询条件
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/myList")
	TableVo<PunishLogVo> getCurList(HttpSession session, PageConfig pageConfig, PunishLogVo punishLogVo) {
		String userId = null;
		
		UserVo currentUser = (UserVo) session.getAttribute(ConstantsUtils.CURRENT_USER);
		if (currentUser != null) {
			userId = currentUser.getUserId();
		}
		
		punishLogVo.setUserId(userId); //查询条件加上当前用户
		
		return punishLogService.getListByParamsAndPage(punishLogVo, pageConfig);
	}
	
	/**
	 * 管理员条件查询所有用户的违章记录vo，支持layui.table
	 * @createUser ying luo
	 * @createDate 2018年4月17日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param session
	 * @param pageConfig 分页
	 * @param punishLogVo 查询条件
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/admin/allList")
	TableVo<PunishLogVo> getAllList(PageConfig pageConfig, PunishLogVo punishLogVo) {
		return punishLogService.getListByParamsAndPage(punishLogVo, pageConfig);
	}
	
	/**
	 * 根据违章记录id，还款并还书
	 * @createUser ying luo
	 * @createDate 2018年4月20日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param punishLogId 违章记录id
	 * @return
	 */
	@RequestMapping("/returnDebtAndBook")
	@ResponseBody
	public Result returnDebtAndBook(String punishLogId) {
		Result result = new Result<>();
		if (StringUtils.isBlank(punishLogId)) {
			result.setMsg("参数错误");
			result.setStatus(false);
			return result;
		}
		
		punishLogService.returnDebtAndBook(punishLogId);
		result.setMsg(ConstantsUtils.SUCCESS_MSG);
		result.setStatus(true);
		return result;
	}
	
	/**
	 * 根据借阅记录id，还款并还书
	 * @createUser ying luo
	 * @createDate 2018年4月20日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param borrowLogId 借阅记录id
	 * @return
	 */
	@RequestMapping("/returnDebtBook")
	@ResponseBody
	public Result returnDebtBook(String borrowLogId) {
		Result result = new Result<>();
		if (StringUtils.isBlank(borrowLogId)) {
			result.setMsg("参数错误");
			result.setStatus(false);
			return result;
		}
		
		punishLogService.returnDebtBook(borrowLogId);
		result.setMsg(ConstantsUtils.SUCCESS_MSG);
		result.setStatus(true);
		return result;
	}
}
