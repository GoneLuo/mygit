package com.luoy.library.controller;

import java.io.IOException;
import java.util.List;

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
import com.luoy.library.common.vo.BookSetVo;
import com.luoy.library.common.vo.PageVo;
import com.luoy.library.common.vo.UserVo;
import com.luoy.library.pojo.User;
import com.luoy.library.pojo.UserInfo;
import com.luoy.library.service.IBookSetService;
import com.luoy.library.service.ISortService;
import com.luoy.library.service.IUserService;

@Controller
@RequestMapping
public class MainController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IBookSetService bookSetService;
	
	@RequestMapping("/test")
	public String test() {
		return "file";
	}
	
	/**
	 * 注册新用户页面
	 * @createUser ying luo
	 * @createDate 2018年4月15日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @return
	 */
	@RequestMapping("/register/toRegister")
	public String toRegister() {
		return "register";
	}
	
	/**
	 * 注册新用户
	 * @createUser ying luo
	 * @createDate 2018年4月15日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param user 新用户账号信息
	 * @param userInfo 新用户信息
	 * @return 登录页面
	 */
	@RequestMapping("/register")
	public ModelAndView register(User user, UserInfo userInfo) {
		ModelAndView mv = new ModelAndView();
		
		userService.registerUser(user, userInfo);
		mv.setViewName("login");
		
		return mv;
	}
	
	/**
	 * 验证用户名是否可用，即用户名未被占用
	 * @createUser ying luo
	 * @createDate 2018年4月15日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param userName 用户名
	 * @return true：可用；false：不可用，已被占用
	 */ 
	@RequestMapping("/register/valiUserName")
	@ResponseBody
	public Result valiUserName(String userName) {
		Result result = new Result<>();
		result.setStatus(userService.valiUserName(userName));
		
		return result;
	}
	
	/**
	 * 首页，同时条件查询图书，带分页
	 * @createUser ying luo
	 * @createDate 2018年4月9日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param pageConfig
	 * @param bookSetVo 查询条件
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView toIndex(PageConfig pageConfig, BookSetVo bookSetVo) {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("libraryHome");
		PageVo<BookSetVo> bookList = bookSetService.getPageVoByParamsAndPage(bookSetVo, pageConfig);
		mv.addObject("page", bookList);
		
		/**
		 * 返回查询条件
		 */
		String conditionType = null;
		String conditionContent = null;
		if (StringUtils.isNoneBlank(bookSetVo.getBookName())) {
			conditionType = "bookName";
			conditionContent = bookSetVo.getBookName();
		} else if (StringUtils.isNoneBlank(bookSetVo.getAuthor())) {
			conditionType = "author";
			conditionContent = bookSetVo.getAuthor();
		} else if (StringUtils.isNoneBlank(bookSetVo.getIsbn())) {
			conditionType = "isbn";
			conditionContent = bookSetVo.getIsbn();
		} else if (StringUtils.isNoneBlank(bookSetVo.getPress())) {
			conditionType = "press";
			conditionContent = bookSetVo.getPress();
		} else if (StringUtils.isNoneBlank(bookSetVo.getSortName())) {
			conditionType = "sortName";
			conditionContent = bookSetVo.getSortName();
		}
		mv.addObject("conditionType", conditionType);
		mv.addObject("conditionContent", conditionContent);
		
		return mv;
	}
	
	/**
	 * 跳转到登录页面
	 * @createUser ying luo
	 * @createDate 2018年3月29日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @return
	 */
	@RequestMapping({"", "/"})
	public String toLogin() {
		
		return "login";
	}
	
	/**
	 * 用户登录验证
	 * @createUser ying luo
	 * @createDate 2018年3月29日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/login")
	public String login(String userName, String password, HttpSession session) {
		
		if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
			return "login";
		}
		
		UserVo currentUser = userService.login(userName, password);
		if(currentUser != null) {
			session.setAttribute(ConstantsUtils.CURRENT_USER, currentUser);
			return "redirect:/index";
		} else {
			return "login";
		}
		
	}
	
	/**
	 * 退出登录
	 * @createUser ying luo
	 * @createDate 2018年4月5日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		if (session != null) {
			session.removeAttribute(ConstantsUtils.CURRENT_USER);
		}
		return "login";
	}
}
