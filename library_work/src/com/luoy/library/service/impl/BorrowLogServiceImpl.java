package com.luoy.library.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.luoy.library.common.vo.BorrowLogVo;
import com.luoy.library.common.vo.TableVo;
import com.luoy.library.dao.IBorrowLogDao;
import com.luoy.library.dao.util.IBaseDao;
import com.luoy.library.pojo.BookSet;
import com.luoy.library.pojo.BorrowLog;
import com.luoy.library.pojo.SingleBook;
import com.luoy.library.pojo.User;
import com.luoy.library.service.IBookSetService;
import com.luoy.library.service.IBorrowLogService;
import com.luoy.library.service.ISingleBookService;
import com.luoy.library.service.IUserService;
import com.luoy.library.service.util.BaseServiceImpl;

@Service
@Transactional
public class BorrowLogServiceImpl extends BaseServiceImpl<BorrowLog> implements IBorrowLogService {

	@Autowired
	private IBorrowLogDao borrowLogDao;
	
	@Autowired
	private IBookSetService bookSetService;
	
	@Autowired
	private ISingleBookService singleBookService;
	
	@Autowired
	private IUserService userService;
	
	@Override
	public IBaseDao<BorrowLog> getDao() {
		return (IBaseDao<BorrowLog>)borrowLogDao;
	}
	
	@Transactional
	@Override
	public void addWhenBorrow(String userId, String singleBookId) {
		BorrowLog borrowLog = new BorrowLog();
		
		borrowLog.setUserId(userId);
		borrowLog.setSingleBookId(singleBookId);
		borrowLog.setBorrowTime(new Date());
		borrowLog.setIsReturn(ConstantsUtils.NOT_RETURN);
		borrowLog.setExceptReturnTime(getExpectReturnTime());
		
		borrowLogDao.save(borrowLog);
	}

	@Transactional
	@Override
	public void changeWhenReturn(String userId, String singleBookId) {
		/**
		 * 1、根据用户id和单本图书id，找到借书记录，找到"未还"的借书记录
		 */
		BorrowLog borrowLog = queryByUserAndSingleBook(userId, singleBookId);
		if (null == borrowLog) {
			return ;
		}
		
		/**
		 * 2、设置借书记录的归还时间，还书状态
		 */
		borrowLog.setActualReturnTime(new Date());
		borrowLog.setIsReturn(ConstantsUtils.IS_RETURN);
		borrowLogDao.update(borrowLog);
	}

	@Transactional(readOnly = true)
	@Override
	public TableVo<BorrowLogVo> getAllListByUserId(String userId, PageConfig pageConfig) {
		List<BorrowLog> borrowLogs = null;
		
		if (StringUtils.isNoneBlank(userId)) {
			Map<String, Object> params = new HashMap();
			params.put("userId", userId);
			borrowLogs = borrowLogDao.queryListByParams(params, pageConfig);
		}
		
		List<BorrowLogVo> borrowLogVos = getVoList(borrowLogs);
		
		TableVo<BorrowLogVo> tableVo = new TableVo<>(pageConfig.getRowCount(), borrowLogVos);
		
		return tableVo;
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<BorrowLog> getNoReturnList(String userId, PageConfig pageConfig) {
		if (StringUtils.isBlank(userId)) {
			return null;
		}
		
		Map<String, Object> params = new HashMap();
		params.put("userId", userId);
		params.put("isReturn", ConstantsUtils.NOT_RETURN);
		List<BorrowLog> borrowLogs = borrowLogDao.queryListByParams(params, pageConfig);
		
		return borrowLogs;
	}
	
	@Transactional(readOnly = true)
	@Override
	public TableVo<BorrowLogVo> getCurListByUserId(String userId, PageConfig pageConfig) {
		List<BorrowLog> borrowLogs = getNoReturnList(userId, pageConfig);
 		
		List<BorrowLogVo> borrowLogVos = getVoList(borrowLogs);
		TableVo<BorrowLogVo> tableVo = new TableVo<>(pageConfig.getRowCount(), borrowLogVos);
		
		return tableVo;
	}

	@Transactional
	@Override
	public boolean borrowBook(String userId, String singleBookId) {
		try {
			SingleBook singleBook = singleBookService.get(singleBookId);
			BookSet bookSet = bookSetService.get(singleBook.getBookSetId());
			User user = userService.get(userId);
			

			/**
			 * 0、限制条件：TODO
			 * flag1:不是馆藏本【前端处理】
			 * flag1:用户当前可借阅次数是否>0，该单本书未被借走
			 */
			boolean flag1 = singleBook.getIsCollected().equals(ConstantsUtils.NOT_COLLECTED);
			boolean flag2 = user.getLeftBorrowCount() > 0 && singleBook.getIsBorrowed().equals(ConstantsUtils.NOT_BORROWED);
			if (!flag1 || !flag2) {
				return false;
			}
			
			/**
			 * 1、当前SingleBook：是否借出状态改为已借出
			 */
			singleBook.setIsBorrowed(ConstantsUtils.BE_BORROWED);
			singleBookService.update(singleBook);
			
			/**
			 * 2、所属BookSet：剩余数量-1、图书被借阅的总次数+1、当前借出去的图书数量+1
			 */
			Integer leftCount = ConstantsUtils.changeInteger(bookSet.getLeftCount(), -1);
			Integer borrowedCount = ConstantsUtils.changeInteger(bookSet.getBorrowedCount(), 1);
			Integer curBorrowCount = ConstantsUtils.changeInteger(bookSet.getCurBorrowCount(), 1);
			bookSet.setLeftCount(leftCount);
			bookSet.setBorrowedCount(borrowedCount);
			bookSet.setCurBorrowCount(curBorrowCount);
			bookSetService.changeBookSet(bookSet);
			
			/**
			 * 3、增加一条借书记录
			 */
			addWhenBorrow(userId, singleBookId);
			
			/**
			 * 4、用户的剩余可借次数-1
			 */
			Integer leftBorrowCount = ConstantsUtils.changeInteger(user.getLeftBorrowCount(), -1);
			user.setLeftBorrowCount(leftBorrowCount);
			userService.update(user);
			
			return true;
		} catch (Exception e) {
			//空指针异常是，返回失败
			return false;
		}
		
	}

	@Transactional
	@Override
	public boolean returnBook(String userId, String singleBookId) {
		try {
			SingleBook singleBook = singleBookService.get(singleBookId);
			BookSet bookSet = bookSetService.get(singleBook.getBookSetId());
			User user = userService.get(userId);
			
			/**
			 * 0、限制条件：TODO：有bug
			 * flag:该借阅记录未还
			 */
			BorrowLog borrowLog = queryByUserAndSingleBook(userId, singleBookId); //未还的借阅记录
			if (null == borrowLog) {
				return false;
			}
			
			/**
			 * 1、当前SingleBook：是否借出状态改为未借出
			 */
			singleBook.setIsBorrowed(ConstantsUtils.NOT_BORROWED);
			singleBookService.update(singleBook);
			
			/**
			 * 2、所属BookSet：剩余数量+1、当前借出去的图书数量-1
			 */
			Integer leftCount = ConstantsUtils.changeInteger(bookSet.getLeftCount(), 1);
			Integer curBorrowCount = ConstantsUtils.changeInteger(bookSet.getCurBorrowCount(), -1);
			bookSet.setLeftCount(leftCount);
			bookSet.setCurBorrowCount(curBorrowCount);
			bookSetService.changeBookSet(bookSet);
			
			/**
			 * 3、修改指定借书记录
			 */
			changeWhenReturn(userId, singleBookId);
			
			/**
			 * 4、用户的剩余可借次数+1
			 */
			Integer leftBorrowCount = ConstantsUtils.changeInteger(user.getLeftBorrowCount(), 1);
			user.setLeftBorrowCount(leftBorrowCount);
			userService.update(user);
			
			return true;
		} catch (NullPointerException e) { 
			//空指针异常是，返回失败
			return false;
		}
	}

	/**
	 * 根据借阅记录BorrowLog，获取BorrowLogVo
	 * @createUser ying luo
	 * @createDate 2018年4月6日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param borrowLog
	 * @return
	 */
	@Transactional(readOnly = true)
	private BorrowLogVo getVoByBorrowLog(BorrowLog borrowLog) {
		if (null == borrowLog) {
			return null;
		}
		
		String singleBookId = borrowLog.getSingleBookId();
		SingleBook singleBook = null;
		if (StringUtils.isNoneBlank(singleBookId)) {
			singleBook = singleBookService.get(singleBookId);
		}
		String bookSetId = singleBook.getBookSetId();
		BookSet bookSet = null;
		if (StringUtils.isNoneBlank(bookSetId)) {
			bookSet = bookSetService.get(bookSetId);
		}
		String userId = borrowLog.getUserId();
		User user = null;
		if (StringUtils.isNoneBlank(userId)) {
			user = userService.get(userId);
		}
		
		return new BorrowLogVo(borrowLog, singleBook, bookSet, user);
	}
	
	/**
	 * 根据借阅记录列表，获取BorrowLogVo列表
	 * @createUser ying luo
	 * @createDate 2018年4月6日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param borrowLogs
	 * @return
	 */
	@Transactional(readOnly = true)
	private List<BorrowLogVo> getVoList(List<BorrowLog> borrowLogs) {
		if (null == borrowLogs || borrowLogs.isEmpty()) {
			return null;
		}
		
		List<BorrowLogVo> borrowLogVos = new ArrayList<>();
		for (BorrowLog borrowLog: borrowLogs) {
			BorrowLogVo borrowLogVo = getVoByBorrowLog(borrowLog);
			if (borrowLogVo == null) {
				continue;
			}
			borrowLogVos.add(borrowLogVo);
		}
		
		return borrowLogVos;
	}
	
	@Override
	public Date getExpectReturnTime() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.MONTH, c.get(Calendar.MONTH) + ConstantsUtils.BORROW_MONTH);
		Date d = c.getTime();
		
		return d;
	}

	@Transactional(readOnly = true)
	@Override
	public TableVo<BorrowLogVo> findListByParams(PageConfig pageConfig, BorrowLogVo borrowLogVo) {
		List<BorrowLog> borrowLogs = borrowLogDao.queryListByParams(borrowLogVo, pageConfig);
		List<BorrowLogVo> borrowLogVos = getVoList(borrowLogs);
		
		return new TableVo<>(pageConfig.getRowCount(), borrowLogVos);
	}

	@Override
	public BorrowLog queryByUserAndSingleBook(String userId, String singleBookId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("singleBookId", singleBookId);
		params.put("isReturn", ConstantsUtils.NOT_BORROWED);
		List<BorrowLog> borrowLogs = borrowLogDao.queryListByParams(params, null);
		if (borrowLogs == null && borrowLogs.isEmpty()) {
			return null;
		} 
		BorrowLog borrowLog = borrowLogs.get(0);
		
		return borrowLog;
	}

	@Override
	public List<BorrowLog> getAllNoReturnList() {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("isReturn", ConstantsUtils.JUDGE_NOT);
		List<BorrowLog> borrowLogs = borrowLogDao.queryListByParams(paramMap, null);
		return borrowLogs;
	}


}
