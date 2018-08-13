package com.luoy.library.controller;

import java.util.List;

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
import com.luoy.library.common.vo.TableVo;
import com.luoy.library.pojo.BookSet;
import com.luoy.library.pojo.SingleBook;
import com.luoy.library.service.IBookSetService;
import com.luoy.library.service.ISingleBookService;

@Controller
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	private IBookSetService bookSetService;
	
	@Autowired
	private ISingleBookService singleBookService;
	
	/**
	 * 跳转到分类浏览页面
	 * @createUser ying luo
	 * @createDate 2018年4月3日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @return
	 */
	@RequestMapping("/toLibarySort")
	public ModelAndView toLibarySort(String sortId, PageConfig pageConfig) {
		ModelAndView mv = new ModelAndView("librarySort");
		return mv;
	}
	
	/**
	 * 查询指定分类下的图书列表，支持ajax分页
	 * @createUser ying luo
	 * @createDate 2018年4月26日
	 * 
	 * @param sortId
	 * @param pageConfig
	 * @return
	 */
	@RequestMapping("/getListBySort")
	@ResponseBody
	public Result<PageVo<BookSetVo>> getListBySort(String sortId, PageConfig pageConfig) {
		Result<PageVo<BookSetVo>> result = new Result<>();
		
		PageVo<BookSetVo> bookSetVos = bookSetService.queryListBySort(sortId, pageConfig);
		result.setData(bookSetVos);
		result.setStatus(true);
		return result;
	}
	
	/**
	 * 指定图书详情页面
	 * @createUser ying luo
	 * @createDate 2018年4月5日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param bookSetId 图书id
	 * @return
	 */
	@RequestMapping("/bookDetail")
	public ModelAndView bookDetails(String bookSetId) {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("bookInfo");
		
		if (StringUtils.isNotBlank(bookSetId)) {
			BookSetVo bookSet = bookSetService.getVoById(bookSetId);
			mv.addObject("book", bookSet);
			List<SingleBook> singleBooks = singleBookService.getListByBookSetId(bookSetId);
			mv.addObject("singleBooks", singleBooks);
		}
		
		return mv;
	}
	
	/**
	 * 编辑图书信息页面
	 * bookSetId为空时，新增图书；否则，编辑该图书
	 * @createUser ying luo
	 * @createDate 2018年4月4日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 	
	 * @param bookSetId 图书id
	 * @return
	 */
	@RequestMapping("/admin/toEditInfo")
	public ModelAndView toEditBookInfo(String bookSetId) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/admin/editBookInfo");
		
		if (StringUtils.isBlank(bookSetId)) {
			mv.addObject("editFlag", ConstantsUtils.ADD); //新增
		} else {
			mv.addObject("editFlag", ConstantsUtils.EDIT); //编辑
			BookSetVo bookSet = bookSetService.getVoById(bookSetId);
			mv.addObject("bookSet", bookSet);
		}
		
		return mv;
	}
	
	/**
	 * 编辑图书，通过editFlag值判断是新增还是修改
	 * @createUser ying luo
	 * @createDate 2018年4月5日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param bookSet
	 * @param editFlag
	 */
	@RequestMapping("/admin/edit")
	@ResponseBody
	public Result editBook(BookSet bookSet, String editFlag) {
		Result result = new Result<>();
		if (null == bookSet || StringUtils.isBlank(editFlag)) {
			result.setMsg(ConstantsUtils.PARAM_ERROR_MSG);
			result.setStatus(false);
			return result;
		}
		
		if (editFlag.equals(ConstantsUtils.ADD)) {
			bookSetService.addBookSet(bookSet);
			result.setMsg(ConstantsUtils.SAVE_SUCCESS_MSG);
		} else if (editFlag.equals(ConstantsUtils.EDIT)) {
			bookSetService.changeBookSet(bookSet);
			result.setMsg(ConstantsUtils.EDIT_SUCCESS_MSG);
		}
		
		result.setStatus(true);
		return result;
	}
	
	/**
	 * 带分页的图书列表
	 * @createUser ying luo
	 * @createDate 2018年4月4日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param bookSet 查询条件
	 * @param pageConfig 分页
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/bookSetList")
	/*public TableVo<BookSetVo> serachBookSetList(BookSet bookSet, PageConfig pageConfig) {
		return bookSetService.getTableVoByParamsAndPage(bookSet, pageConfig);
	}*/
	public TableVo<BookSetVo> serachBookSetList(BookSetVo bookSetVo, PageConfig pageConfig) {
		return bookSetService.getTableVoByParamsAndPage(bookSetVo, pageConfig);
	}
	
	/**
	 * 逻辑删除图书，以免借阅记录、预约记录、处罚记录等缺少信息
	 * 逻辑删除后，该图书将不能被查询；图书详情页面也只能展示基本信息，不能借阅或预定
	 * @createUser ying luo
	 * @createDate 2018年4月21日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param bookSetId
	 * @return
	 */
	@RequestMapping("/admin/delBook")
	@ResponseBody
	public Result delBook(String bookSetId) {
		Result result = new Result<>();
		if (StringUtils.isBlank(bookSetId)) {
			result.setStatus(false);
			result.setMsg(ConstantsUtils.PARAM_ERROR_MSG);
			return result;
		}
		
		//不能删除当前被借阅的图书
		BookSet bookSet = bookSetService.get(bookSetId);
		if (bookSet.getCurBorrowCount() != null && bookSet.getCurBorrowCount() > 0) {
			result.setStatus(false);
			result.setMsg("不能删除当前被借阅的图书");
			return result;
		}
		
		bookSet.setStatus(ConstantsUtils.JUDGE_NOT);
		bookSetService.update(bookSet);
		
		result.setMsg(ConstantsUtils.DEL_SUCCESS_MSG);
		result.setStatus(true);
		return result;
	}
	
	/**
	 * 增加图书时，验证ISBN是否合法（即ISBN不能重复）
	 * @createUser ying luo
	 * @createDate 2018年4月21日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param isbn
	 * @param editFlag 修改图书或增加图书
	 * @return
	 */
	@RequestMapping("/admin/valiISBNWhenAdd")
	@ResponseBody
	public Result valiISBNWhenAdd(String isbn) {
		Result result = new Result<>();
		if (StringUtils.isBlank(isbn)) {
			result.setStatus(false);
			return result;
		}
		
		result.setStatus(bookSetService.valiISBN(isbn, ConstantsUtils.ADD));
		return result;
	}
	
	/**
	 * 编辑图书时，验证ISBN是否合法（即ISBN不能重复）
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
	@RequestMapping("/admin/valiISBNWhenEdit")
	@ResponseBody
	public Result valiISBNWhenEdit(String isbn) {
		Result result = new Result<>();
		if (StringUtils.isBlank(isbn)) {
			result.setStatus(false);
			return result;
		}
		
		result.setStatus(bookSetService.valiISBN(isbn, ConstantsUtils.EDIT));
		return result;
	}
	
}
