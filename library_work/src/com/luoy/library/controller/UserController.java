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
import com.luoy.library.pojo.User;
import com.luoy.library.pojo.UserInfo;
import com.luoy.library.service.IPunishLogService;
import com.luoy.library.service.IUserService;

@RequestMapping("/user")
@Controller
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IPunishLogService punishLogService;
	
	@RequestMapping("/test")
	public void test() {
		User u = new User();
		userService.save(u);
		String id = u.getId();
		User user = userService.get(id);
		user.setUserName("update3");
		userService.update(user);
		userService.delete(u.getId());
		
		String end;
	}
	
	/**
	 * 修改当前用户信息页面
	 * @createUser ying luo
	 * @createDate 2018年4月15日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @return
	 */
	@RequestMapping("/toEditMyInfo")
	public ModelAndView toEditMyInfo(HttpSession session) {
		String userId = null;
		UserVo currentUser = (UserVo) session.getAttribute(ConstantsUtils.CURRENT_USER);
		if (currentUser != null) {
			userId = currentUser.getUserId();
		}
		
		ModelAndView mv = new ModelAndView("/user/editInfo");
		UserVo userVo = userService.getUserVoById(userId);
		mv.addObject("user", userVo);
		
		return mv;
	}
	
	@RequestMapping("/editInfo")
	public void editInfo(UserVo userVo) {
		userService.changeUserVo(userVo);
	}
	
	/**
	 * 我的图书馆
	 * @createUser ying luo
	 * @createDate 2018年4月3日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @return
	 */
	@RequestMapping("/toPersonalLibrary")
	public ModelAndView toPersonalLibrary(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		
		String userId = null;
		try {
			UserVo currentUser = (UserVo) session.getAttribute(ConstantsUtils.CURRENT_USER);
			userId = currentUser.getUserId();
		} catch (Exception e) {
			mv.setViewName("login");
			return mv;
		}
		
		if (StringUtils.isNoneBlank(userId)) {
			mv.setViewName("/user/personalLibrary");
			
			UserVo userVo = userService.getUserVoById(userId);
			mv.addObject("userVo", userVo);
			
			Integer punishCount = punishLogService.getCurCount(userId);
			mv.addObject("punishCount", punishCount);
			
			//TODO 更新当前用户的预定信息
			
		}
		
		return mv;
	}
	
	/**
	 * 当前借阅
	 * @createUser ying luo
	 * @createDate 2018年4月3日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @return
	 */
	@RequestMapping("/toCurrentBorrowList")
	public String toCurrentBorrowList() {
		return "/user/currentBorrowList";
	}
	
	/**
	 * 借阅历史
	 * @createUser ying luo
	 * @createDate 2018年4月3日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @return
	 */
	@RequestMapping("/toBorrowHistory")
	public String toBorrowHistory() {
		return "/user/borrowHistory";
	}
	
	/**
	 * 预约信息
	 * @createUser ying luo
	 * @createDate 2018年4月3日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @return
	 */
	@RequestMapping("/toOrderList")
	public String toOrderList() {
		return "/user/orderList";
	}
	
	/**
	 * 当前用户-违章缴款页面
	 * 每次访问更新一次该用户的违章记录
	 * @createUser ying luo
	 * @createDate 2018年4月16日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param session
	 * @param pageConfig
	 * @param punishLogVo
	 * @return
	 */
	@RequestMapping("/toPunishLog")
	public String toPunishLog(HttpSession session) {
		String userId = null;
		
		UserVo currentUser = (UserVo) session.getAttribute(ConstantsUtils.CURRENT_USER);
		if (currentUser != null) {
			userId = currentUser.getUserId();
		}
		punishLogService.updateByUserId(userId); //更新当前用户的违章记录
		
		return "/user/punishLog";
	}

	/**
	 * 分页条件查询所有用户，封装到适应layui.table的TableVo并返回
	 * @createUser ying luo
	 * @createDate 2018年3月29日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param pageConfig 分页
	 * @param userVo UserVo，保存查询条件
	 * @return 适应layui.table的TableVo
	 */
	@ResponseBody
	@RequestMapping("/admin/searchUser")
	public TableVo<UserVo> searchUser(PageConfig pageConfig, UserVo userVo) {
		return userService.findUserByParams(pageConfig, userVo);
	}
	
	/**
	 * 注销用户账号
	 * @createUser ying luo
	 * @createDate 2018年3月29日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 *
	 * @param 用户id
	 */
	@ResponseBody
	@RequestMapping("/admin/destoryUser")
	public Result destoryUser(String id) {
		Result result = new Result<>();
		
		if (StringUtils.isNoneBlank(id)) {
			userService.setUseful(id, ConstantsUtils.USELESS);
			result.setStatus(true);
			result.setMsg("成功注销");
		} else {
			result.setStatus(false);
			result.setMsg("参数错误");
		}
		
		return result;
	}
	
	/**
	 * 恢复用户账号
	 * @createUser ying luo
	 * @createDate 2018年3月29日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 *
	 * @param 用户id
	 */
	@ResponseBody
	@RequestMapping("/admin/recoveryUser")
	public Result recoveryUser(String id) {
		Result result = new Result<>();
		
		if (StringUtils.isNoneBlank(id)) {
			userService.setUseful(id, ConstantsUtils.USEFUL);
			result.setStatus(true);
			result.setMsg("成功恢复");
		} else {
			result.setStatus(false);
			result.setMsg("参数错误");
		}
		
		return result;
	}
}
