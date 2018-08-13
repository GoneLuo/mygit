package com.luoy.library.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luoy.library.common.util.ConstantsUtils;
import com.luoy.library.common.util.PageConfig;
import com.luoy.library.common.util.Result;
import com.luoy.library.common.vo.BorrowLogVo;
import com.luoy.library.common.vo.TableVo;
import com.luoy.library.common.vo.UserVo;
import com.luoy.library.pojo.BookSet;
import com.luoy.library.pojo.BorrowLog;
import com.luoy.library.pojo.SingleBook;
import com.luoy.library.pojo.User;
import com.luoy.library.service.IBookSetService;
import com.luoy.library.service.IBorrowLogService;
import com.luoy.library.service.ISingleBookService;
import com.luoy.library.service.IUserService;

import javafx.scene.control.TableView;

/**
 * 借书记录controller
 * @author ying luo
 * @createDate 2018年4月5日
 */
@Controller
@RequestMapping("/borrowLog")
public class BorrowLogController {
	
	@Autowired
	private IBorrowLogService borrowLogService;
	
	@Autowired
	private IUserService userService ;
	
	@Autowired
	private IBookSetService bookSetService;
	
	@Autowired
	private ISingleBookService singleBookService;
	
	/**
	 * 当前用户借书
	 * @createUser ying luo
	 * @createDate 2018年4月5日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param singleBookId 单本图书id
	 * @return
	 */
	@RequestMapping("/userBorrow")
	@ResponseBody
	public Result userBorrowBook(String singleBookId, HttpSession session) {
		Result result = new Result<>();
		
		String userId = null;
		try {
			UserVo currentUser = (UserVo) session.getAttribute(ConstantsUtils.CURRENT_USER);
			userId = currentUser.getUserId();
		} catch (Exception e) {
			result.setMsg("获取当前用户失败");
			return result;
		}
		
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(singleBookId)) {
			result.setMsg("参数错误");
			result.setStatus(false);
		} else if (!borrowLogService.borrowBook(userId, singleBookId)) {
			result.setMsg("借书出错");
			result.setStatus(false);
		} else {
			result.setMsg("借书成功，请在" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(borrowLogService.getExpectReturnTime()) + "前归还");
			result.setStatus(true);
		}
		return result;
	}
	
	/**
	 * 当前用户还书
	 * @createUser ying luo
	 * @createDate 2018年4月5日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param singleBookId 单本图书id
	 * @return
	 */
	@RequestMapping("/userReturn")
	@ResponseBody
	public Result userReturnBook(String singleBookId, HttpSession session) {
		Result result = new Result<>();
		
		String userId = null;
		try {
			UserVo currentUser = (UserVo) session.getAttribute(ConstantsUtils.CURRENT_USER);
			userId = currentUser.getUserId();
		} catch (Exception e) {
			result.setMsg("获取当前用户失败");
			return result;
		}
		
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(singleBookId)) {
			result.setMsg("参数错误");
			result.setStatus(false);
		} else if (!borrowLogService.returnBook(userId, singleBookId)) {
			result.setMsg("还书出错");
			result.setStatus(false);
		} else {
			result.setMsg("还书成功");
			result.setStatus(true);
		}
		return result;
	}
	
	/**
	 * 指定用户和单本图书，管理员还书
	 * @createUser ying luo
	 * @createDate 2018年4月5日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param userId 用户id
	 * @param singleBookId 单本图书id
	 * @return
	 */
	@RequestMapping("/adminReturn")
	@ResponseBody
	public Result adminReturnBook(String singleBookId, String userId) {
		Result result = new Result<>();
		
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(singleBookId)) {
			result.setMsg("参数错误");
			result.setStatus(false);
		} else if (!borrowLogService.returnBook(userId, singleBookId)) {
			result.setMsg("还书出错");
			result.setStatus(false);
		} else {
			result.setMsg("还书成功");
			result.setStatus(true);
		}
		return result;
	}
	
	/**
	 * 指定用户和单本图书，管理员借书
	 * @createUser ying luo
	 * @createDate 2018年4月5日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param userId 用户id
	 * @param singleBookId 单本图书id
	 * @return
	 */
	@RequestMapping("/adminBorrow")
	@ResponseBody
	public Result adminBorrowBook(String isbn, String userName) {
		Result result = new Result<>();
		
		if (StringUtils.isBlank(isbn) || StringUtils.isBlank(userName)) {
			result.setMsg("参数错误");
			result.setStatus(false);
			return result;
		} 
		
		//图书和用户是否存在前端做验证，这里只判断库存是否足够
		BookSet bookSet = bookSetService.getByIsbn(isbn);
		List<SingleBook> singleBooks = singleBookService.getUsefulListByBookSet(bookSet.getId());
		if (singleBooks == null || singleBooks.isEmpty()) {
			result.setMsg("当前图书库存不足，请重新选择");
			result.setStatus(false);
			return result;
		}
		SingleBook singleBook = singleBooks.get(0);
		User user = userService.getByUserName(userName);
		
		if(!borrowLogService.borrowBook(user.getId(), singleBook.getId())) {
			result.setMsg("借书出错");
			result.setStatus(false);
		} else {
			result.setMsg("借书成功，请在" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(borrowLogService.getExpectReturnTime()) + "前归还");
			result.setStatus(true);
		}
		return result;
	}
	
	/**
	 * 管理员借书时，判断指定用户名的用户是否存在
	 * @createUser ying luo
	 * @createDate 2018年4月21日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param userName
	 * @return
	 */
	@RequestMapping("/userIsExist")
	@ResponseBody
	public Result userIsExist(String userName) {
		Result result = new Result<>();
		
		if (StringUtils.isBlank(userName)) {
			result.setMsg("参数错误");
			result.setStatus(false);
			return result;
		} 
		
		User user =  userService.getByUserName(userName);
		if (null == user) {
			result.setMsg("该用户不存在");
			result.setStatus(false);
		} else {
			result.setMsg("该用户存在");
			result.setStatus(true);
		}
		
		return result;
	}
	
	/**
	 * 管理员借书时，判断指定isbn的图书是否存在
	 * @createUser ying luo
	 * @createDate 2018年4月21日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param isbn
	 * @return
	 */
	@RequestMapping("/bookIsExist")
	@ResponseBody
	public Result bookSetIsExist(String isbn) {
		Result result = new Result<>();
		
		if (StringUtils.isBlank(isbn)) {
			result.setMsg("参数错误");
			result.setStatus(false);
			return result;
		} 
		
		BookSet bookSet = bookSetService.getByIsbn(isbn);
		if (null == bookSet) {
			result.setMsg("该图书不存在");
			result.setStatus(false);
		} else {
			result.setMsg("该图书存在");
			result.setStatus(true);
		}
		
		return result;
	}
	
	/**
	 * 查询当前用户的当前借阅记录列表，支持layui.table渲染
	 * @createUser ying luo
	 * @createDate 2018年4月5日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param session
	 * @param pageConfig 
	 * @param borrowLog 查询条件
	 * @return 
	 */
	@RequestMapping("/myList")
	@ResponseBody
	public TableVo<BorrowLogVo> findMyBorrowLogs(HttpSession session, PageConfig pageConfig, BorrowLog borrowLog) {
		String userId = null;
		try {
			UserVo currentUser = (UserVo) session.getAttribute(ConstantsUtils.CURRENT_USER);
			userId = currentUser.getUserId();
		} catch (Exception e) {
			return null;
		}
		if (StringUtils.isBlank(userId)) {
			return null;
		}
		
		return borrowLogService.getCurListByUserId(userId, pageConfig);
	}
	
	/**
	 * 查询当前用户的借阅历史，支持layui.table渲染
	 * @createUser ying luo
	 * @createDate 2018年4月5日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param session
	 * @param pageConfig 
	 * @param borrowLog 查询条件
	 * @return 
	 */
	@RequestMapping("/myHistory")
	@ResponseBody
	public TableVo<BorrowLogVo> findMyBorrowLogHistory(HttpSession session, PageConfig pageConfig, BorrowLog borrowLog) {
		String userId = null;
		try {
			UserVo currentUser = (UserVo) session.getAttribute(ConstantsUtils.CURRENT_USER);
			userId = currentUser.getUserId();
		} catch (Exception e) {
			return null;
		}
		if (StringUtils.isBlank(userId)) {
			return null;
		}
		
		return borrowLogService.getAllListByUserId(userId, pageConfig);
	}
	
	/**
	 * 管理员条件查询所有的借阅记录，支持layui.table渲染
	 * @createUser ying luo
	 * @createDate 2018年4月7日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param pageConfig
	 * @param borrowLogVo 查询条件
	 * @return 
	 */
	@RequestMapping("/list")
	@ResponseBody
	TableVo<BorrowLogVo> findAllBorrowLog(PageConfig pageConfig, BorrowLogVo borrowLogVo) {
		return borrowLogService.findListByParams(pageConfig, borrowLogVo);
	}

}
