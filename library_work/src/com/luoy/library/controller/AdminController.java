package com.luoy.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.luoy.library.service.IPunishLogService;

/**
 * 管理员页面跳转的controller
 * @author ying luo
 * @createDate 2018年4月6日
 */
@RequestMapping("/admin")
@Controller
public class AdminController {
	@Autowired
	private IPunishLogService punishLogService;
	
	@RequestMapping("/toBookAndSort")
	public String toBookAndSort() {
		return "/admin/bookAndSortManager";
	}
	
	/**
	 * 图书管理页面
	 * @createUser ying luo
	 * @createDate 2018年4月6日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @return
	 */
	@RequestMapping({"", "/", "/book"})
	public String toBookManager() {
		return "/admin/bookManager";
	}
	
	/**
	 * 用户借阅管理页面
	 * @createUser ying luo
	 * @createDate 2018年4月6日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @return
	 */
	@RequestMapping("/sort")
	public String toSortManager() {
		return "/admin/sortManager";
	}
	
	/**
	 * 用户借阅管理页面
	 * @createUser ying luo
	 * @createDate 2018年4月6日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @return
	 */
	@RequestMapping("/borrow")
	public String toBorrowManager() {
		return "/admin/borrowManager";
	}
	
	/**
	 * 用户违章管理页面
	 * 每次访问前更新一次所有用户的违章记录
	 * @createUser ying luo
	 * @createDate 2018年4月6日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @return
	 */
	@RequestMapping("/punish")
	public String toPunishManager() {
		punishLogService.updateAllPunish(); //更新所有用户的违章记录
		
		return "/admin/punishManager";
	}
	
	/**
	 * 跳转到用户管理页面
	 * @createUser ying luo
	 * @createDate 2018年3月29日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @return
	 */
	@RequestMapping("/user")
	public String toUserManager() {
		return "/admin/userManager";
	}
	

}
