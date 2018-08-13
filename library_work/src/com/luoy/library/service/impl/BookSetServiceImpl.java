package com.luoy.library.service.impl;

import java.text.DecimalFormat;
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
import com.luoy.library.common.util.PageConfig;
import com.luoy.library.common.util.PageUtil;
import com.luoy.library.common.vo.BookSetVo;
import com.luoy.library.common.vo.PageVo;
import com.luoy.library.common.vo.TableVo;
import com.luoy.library.dao.IBookSetDao;
import com.luoy.library.dao.util.IBaseDao;
import com.luoy.library.pojo.BookSet;
import com.luoy.library.pojo.SingleBook;
import com.luoy.library.pojo.Sort;
import com.luoy.library.service.IBookSetService;
import com.luoy.library.service.ISingleBookService;
import com.luoy.library.service.ISortService;
import com.luoy.library.service.util.BaseServiceImpl;

@Service
@Transactional
public class BookSetServiceImpl extends BaseServiceImpl<BookSet> implements IBookSetService {
	
	@Autowired
	private IBookSetDao bookSetDao;
	
	@Autowired
	private ISingleBookService singleBookService;
	
	@Autowired
	private ISortService sortService;
	
	@Override
	public IBaseDao<BookSet> getDao() {
		return (IBaseDao<BookSet>)bookSetDao;
	}

	@Transactional
	@Override
	public void addBookSet(BookSet bookSet) {
		if (StringUtils.isBlank(bookSet.getId())) {
			bookSet.setId(null);
		}
		bookSet.setLeftCount(bookSet.getSum());
		bookSet.setPublishTime(new Date());
		bookSet.setBorrowedCount(0);
		bookSet.setCurBorrowCount(0);
		bookSet.setStatus(ConstantsUtils.JUDGE_YES);
		
		bookSetDao.save(bookSet);
		
		/**
		 * 对应增加单本图书
		 */
		if (bookSet.getSum() != null || bookSet.getSum().equals(0)) {
			for (int i = 0; i < bookSet.getSum(); i++) {
				SingleBook singleBook = new SingleBook();
				
				singleBook.setBarcode(genericBarcode(bookSet.getIsbn(), i + "")); 
				singleBook.setIsBorrowed(ConstantsUtils.NOT_BORROWED);
				if (0 == i) {
					//第一本图书要馆藏
					singleBook.setIsCollected(ConstantsUtils.IS_COLLECTED);
				} else {
					singleBook.setIsCollected(ConstantsUtils.NOT_COLLECTED);
				}
				singleBook.setBookSetId(bookSet.getId());
				
				singleBookService.save(singleBook);
			}
		}
	}

	@Transactional(readOnly = true)
	@Override
	public TableVo<BookSetVo> getTableVoByParamsAndPage(BookSet bookSet, PageConfig pageConfig) {
		List<BookSet> bookSets = bookSetDao.queryListByParamsAndPage(bookSet, pageConfig);
		
		TableVo<BookSetVo> tableVo = null;
		tableVo = new TableVo<>(pageConfig.getRowCount(), getVoList(bookSets));
		
		return tableVo;
	}
	
	@Override
	public PageVo<BookSetVo> getPageVoByParamsAndPage(BookSetVo bookSetVo, PageConfig pageConfig) {
		/**
		 * 根据首页设计，组合查询条件
		 * 【多表、单条件、模糊查询】bookName、author、isbn、press、sortName
		 */
		StringBuffer hql = new StringBuffer("from BookSet");
		List<Object> params = new ArrayList<>();
		if (StringUtils.isNoneBlank(bookSetVo.getSortName())) {
			hql = new StringBuffer("select bs from BookSet bs, Sort s where bs.sortId = s.id AND s.sortName like ? AND bs.status = ?");
			params.add("%" + bookSetVo.getSortName() + "%");
			params.add(ConstantsUtils.JUDGE_YES);
		} else if (StringUtils.isNoneBlank(bookSetVo.getBookName())) {
			hql.append(" where bookName like ? AND status = ?");
			params.add("%" + bookSetVo.getBookName() + "%");
			params.add(ConstantsUtils.JUDGE_YES);
		} else if (StringUtils.isNoneBlank(bookSetVo.getAuthor())) {
			hql.append(" where author like ? AND status = ?");
			params.add("%" + bookSetVo.getAuthor() + "%");
			params.add(ConstantsUtils.JUDGE_YES);
		} else if (StringUtils.isNoneBlank(bookSetVo.getIsbn())) {
			hql.append(" where isbn = ? AND status = ?");
			params.add(bookSetVo.getIsbn());
			params.add(ConstantsUtils.JUDGE_YES);
		} else if (StringUtils.isNoneBlank(bookSetVo.getPress())) {
			hql.append(" where press like ? AND status = ?");
			params.add("%" + bookSetVo.getPress() + "%");
			params.add(ConstantsUtils.JUDGE_YES);
		} else {
			hql.append(" where status = ?");
			params.add(ConstantsUtils.JUDGE_YES);
		}
		
		List<BookSet> bookSets = bookSetDao.queryListByHqlAndPage(hql.toString(), params.toArray(), pageConfig);
		
		return PageUtil.getPageList(pageConfig, getVoList(bookSets));
	}


	@Transactional
	@Override
	public void changeBookSet(BookSet bookSet) {
		
		BookSet origalBookSet = bookSetDao.get(bookSet.getId()); //原来的图书信息
		int addSum = bookSet.getSum() - origalBookSet.getSum();
		//TODO 对应单本图书，不能减少库存，只能增加库存，前端验证
		if (addSum > 0) {
			/**
			 * 对应增加单本图书
			 */
			for (int i = 0; i < addSum; i++) {
				SingleBook singleBook = new SingleBook();
				
				singleBook.setBarcode(genericBarcode(bookSet.getIsbn(), (i + origalBookSet.getSum()) + "")); 
				singleBook.setIsBorrowed(ConstantsUtils.NOT_BORROWED);
				if (0 == i + origalBookSet.getSum()) {
					//第一本图书要馆藏
					singleBook.setIsCollected(ConstantsUtils.IS_COLLECTED);
				} else {
					singleBook.setIsCollected(ConstantsUtils.NOT_COLLECTED);
				}
				singleBook.setBookSetId(bookSet.getId());
				
				singleBookService.save(singleBook);
			}
			
			//新的剩余量 = 新总数 - 原来已借的数量
			Integer leftCount = bookSet.getSum() - origalBookSet.getBorrowedCount();
			origalBookSet.setLeftCount(leftCount);
		}
		
		//将bookSet的属性全都copy到origalBookSet中保存，以免发生hibernate对象主键冲突
		origalBookSet.setBookName(bookSet.getBookName());
		origalBookSet.setIsbn(bookSet.getIsbn());
		origalBookSet.setSortId(bookSet.getSortId());
		origalBookSet.setAuthor(bookSet.getAuthor());
		origalBookSet.setTranslator(bookSet.getTranslator());
		origalBookSet.setPress(bookSet.getPress());
		origalBookSet.setPrice(bookSet.getPrice());
		origalBookSet.setSum(bookSet.getSum());
		origalBookSet.setSummary(bookSet.getSummary());
		
		bookSetDao.update(origalBookSet);
	}
	
	/**
	 * 根据图书列表，获取图书vo列表
	 * @createUser ying luo
	 * @createDate 2018年4月9日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param bookSets
	 * @return
	 */
	private List<BookSetVo> getVoList(List<BookSet> bookSets) {
		if (null == bookSets || bookSets.isEmpty()) {
			return null;
		}
		
		List<BookSetVo> bookSetVos = new ArrayList<>();
		for (BookSet bookSet: bookSets) {
			BookSetVo bookSetVo = getVoByBookSet(bookSet);
			if (bookSetVo == null) {
				continue;
			}
			bookSetVos.add(bookSetVo);
		}
		return bookSetVos;
	}
	
	/**
	 * 根据图书,获取图书Vo
	 * @createUser ying luo
	 * @createDate 2018年4月9日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param bookSet
	 * @return
	 */
	@Transactional(readOnly = true)
	private BookSetVo getVoByBookSet(BookSet bookSet) {
		if (bookSet == null) {
			return null;
		}
		
		Sort sort = null;
		String sortId = bookSet.getSortId();
		if (StringUtils.isNoneBlank(sortId)) {
			sort = sortService.get(sortId);
		}
		return new BookSetVo(bookSet, sort);
	}

	@Override
	public BookSetVo getVoById(String id) {
		return getVoByBookSet(bookSetDao.get(id));
	}

	@Override
	public TableVo<BookSetVo> getTableVoByParamsAndPage(BookSetVo bookSetVo, PageConfig pageConfig) {
		/**
		 * 根据【图书管理页面】设计，组合查询条件
		 * 【多表、单条件、模糊查询】bookName、author、isbn、press、sortName
		 */
		StringBuffer hql = new StringBuffer("from BookSet");
		List<Object> params = new ArrayList<>();
		if (StringUtils.isNoneBlank(bookSetVo.getSortName())) {
			hql = new StringBuffer("select bs from BookSet bs, Sort s where bs.sortId = s.id AND s.sortName like ? AND bs.status = ?");
			params.add("%" + bookSetVo.getSortName() + "%");
			params.add(ConstantsUtils.JUDGE_YES);
		} else if (StringUtils.isNoneBlank(bookSetVo.getBookName())) {
			hql.append(" where bookName like ? AND status = ?");
			params.add("%" + bookSetVo.getBookName() + "%");
			params.add(ConstantsUtils.JUDGE_YES);
		} else if (StringUtils.isNoneBlank(bookSetVo.getAuthor())) {
			hql.append(" where author like ? AND status = ?");
			params.add("%" + bookSetVo.getAuthor() + "%");
			params.add(ConstantsUtils.JUDGE_YES);
		} else if (StringUtils.isNoneBlank(bookSetVo.getIsbn())) {
			hql.append(" where isbn = ? AND status = ?");
			params.add(bookSetVo.getIsbn());
			params.add(ConstantsUtils.JUDGE_YES);
		} else if (StringUtils.isNoneBlank(bookSetVo.getPress())) {
			hql.append(" where press like ? AND status = ?");
			params.add("%" + bookSetVo.getPress() + "%");
			params.add(ConstantsUtils.JUDGE_YES);
		} else {
			hql.append(" where status = ?");
			params.add(ConstantsUtils.JUDGE_YES);
		}
		
 		List<BookSet> bookSets = bookSetDao.queryListByHqlAndPage(hql.toString(), params.toArray(), pageConfig);
		
		List<BookSetVo> bookSetVos = getVoList(bookSets);
		TableVo<BookSetVo> table = new TableVo<>(pageConfig.getRowCount(), bookSetVos);
		return table;
	}

	@Override
	public void changeBookSort(String oldSortId, String newSortId) {
		bookSetDao.changeBookSort(oldSortId, newSortId);
	}

	@Override
	public PageVo<BookSetVo> queryListBySort(String sortId, PageConfig pageConfig) {
		//sortId为空时，查询根目录的图书列表
		sortId = sortId == null ? ConstantsUtils.ROOT_SORT_ID : sortId;
		
		/**
		 * 1、根据sortId，查询其所有子分类id
		 */
		List<String> sortCids = sortService.getAllCidByPid(sortId);
		
		/**
		 * 2、查询包括自己以及所有子分类下的图书列表
		 */
		List<String> sortIdList = new ArrayList<>();
		if (StringUtils.isNoneBlank(sortId)) {
			sortIdList.add(sortId);
		}
		if (sortCids != null && !sortCids.isEmpty()) {
			sortIdList.addAll(sortCids);
		}
		List<BookSet> bookSets = bookSetDao.queryListBySortList(sortIdList, pageConfig);
		
		List<BookSetVo> bookSetVos = getVoList(bookSets);
		return PageUtil.getPageList(pageConfig, bookSetVos);
	}

	@Override
	public boolean valiISBN(String isbn, String editFlag) {
		if (StringUtils.isBlank(isbn) || StringUtils.isBlank(editFlag)) {
			return false;
		}
		
		boolean flag = false;
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("isbn", isbn);
		paramMap.put("status", ConstantsUtils.JUDGE_YES);
		List<BookSet> bookSets = bookSetDao.queryListByParamsAndPage(paramMap, null);
		if (null == bookSets || bookSets.isEmpty()) {
			flag = true;
		} else {
			 if (editFlag.equals(ConstantsUtils.EDIT)) {
				if (bookSets.size() <= 1) {
					flag = true;
				}
			}
		}
		
		return flag;
	}

	@Override
	public BookSet getByIsbn(String isbn) {
		if (StringUtils.isBlank(isbn)) {
			return null;
		}
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("isbn", isbn);
		List<BookSet> bookSets = bookSetDao.queryListByParamsAndPage(paramMap, null);
		if (null == bookSets || bookSets.isEmpty()) {
			return null;
		}
		
		return bookSets.get(0);
	}

	@Override
	public String genericBarcode(String isbn, String no) {
		if (StringUtils.isBlank(isbn) || StringUtils.isBlank(no)) {
			return "";
		}
		
		String barcode = isbn + no;
		//长度不够12前补0；或者长度超出截取后12位
		while (barcode.length() < 12) {
			barcode = "0" + barcode;
		}
		barcode = barcode.substring(barcode.length() - 12);
		
		return barcode;
	}

}
