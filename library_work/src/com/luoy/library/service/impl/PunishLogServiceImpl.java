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
import com.luoy.library.common.util.PageConfig;
import com.luoy.library.common.vo.PunishLogVo;
import com.luoy.library.common.vo.TableVo;
import com.luoy.library.dao.IPunishLogDao;
import com.luoy.library.dao.util.BaseDaoImpl;
import com.luoy.library.pojo.BookSet;
import com.luoy.library.pojo.BorrowLog;
import com.luoy.library.pojo.PunishLog;
import com.luoy.library.pojo.SingleBook;
import com.luoy.library.pojo.User;
import com.luoy.library.service.IBookSetService;
import com.luoy.library.service.IBorrowLogService;
import com.luoy.library.service.IPunishLogService;
import com.luoy.library.service.ISingleBookService;
import com.luoy.library.service.IUserService;

/**
 * 罚款记录service
 * @author ying luo
 * @createDate 2018年4月16日
 */
@Service
@Transactional
public class PunishLogServiceImpl extends BaseDaoImpl<PunishLog> implements IPunishLogService {
	@Autowired
	private IPunishLogDao punishLogDao;
	
	@Autowired
	private IBorrowLogService borrowLogService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ISingleBookService singleBookService;
	
	@Autowired
	private IBookSetService bookSetService;
	
	@Transactional(readOnly = true)
	@Override
	public TableVo<PunishLogVo> getListByParamsAndPage(PunishLogVo punishLogVo, PageConfig pageConfig) {
		/**
		 * 【根据违章记录页面组合查询条件】
		 */
		Map<String, Object> paramMap = new HashMap<>();
		if (punishLogVo != null && StringUtils.isNoneBlank(punishLogVo.getUserId())) {
			paramMap.put("userId", punishLogVo.getUserId());
		}
		
		List<PunishLog> punishLogs = punishLogDao.queryListByParamsAndPage(paramMap, pageConfig);
		
		List<PunishLogVo> punishLogVos = getVoList(punishLogs);
		TableVo<PunishLogVo> table = new TableVo<>(pageConfig.getRowCount(), punishLogVos);
		
		return table;
	}
	
	@Transactional
	@Override
	public void updateByUserId(String userId) {
		if (StringUtils.isBlank(userId)) {
			return;
		}
		
		List<BorrowLog> borrowLogs = borrowLogService.getNoReturnList(userId, null);
		if (null == borrowLogs || borrowLogs.isEmpty()) {
			return;
		}
		
		for (BorrowLog borrowLog: borrowLogs) {
			createPunishLogByBorrowLog(borrowLog);
		}
		
	}
	
	@Transactional
	@Override
	public void createPunishLogByBorrowLog(BorrowLog borrowLog) {
		SingleBook singleBook = null;
		BookSet bookSet = null;
		User user = null;
		try {
			singleBook = singleBookService.get(borrowLog.getSingleBookId());
			bookSet = bookSetService.get(singleBook.getBookSetId());
			user = userService.get(borrowLog.getUserId());
		} catch (Exception e) {
			return ;
		}
		
		/**
		 * 1、根据借阅记录，判断是否逾期未还
		 */
		boolean flag1 = borrowLog.getIsReturn().equals(ConstantsUtils.IS_RETURN); //已还
		boolean flag2 = borrowLog.getExceptReturnTime().after(new Date()); //未逾期
		if (flag1 || flag2) {
			return ;
		}
		
		/**
		 * 2、根据借阅记录id，判断该罚款信息是否已存在
		 * a：不存在，增加一条罚款记录
		 * b：存在，更新该罚款记录的overDay、sumPunishMoney
		 * ps：罚款增长规则：每天罚款图书定价的1%（该比例保存在ConstantUtils中），直到等于图书定价
		 */
		Map<String, Object> params = new HashMap<>();
		params.put("borrowLogLd", borrowLog.getId());
		List<PunishLog> punishLogs = punishLogDao.queryListByParams(params);
		if (null == punishLogs || punishLogs.isEmpty()) { //a
			PunishLog punishLog = new PunishLog();
			punishLog.setBorrowLogLd(borrowLog.getId());
			punishLog.setPunishType(ConstantsUtils.PUNISH_TYPE_OVERDAY);
			punishLog.setUserId(borrowLog.getUserId());
			punishLog.setHasReturnDebt(ConstantsUtils.JUDGE_NOT);
			
			int overDay = ConstantsUtils.diffDays(new Date(), borrowLog.getExceptReturnTime()) + 1; //不满一天算作一天
			double sumPunishMoney = bookSet.getPrice() * ConstantsUtils.PUNISH_PERCENT_DAY * overDay;
			sumPunishMoney = sumPunishMoney > bookSet.getPrice() ? bookSet.getPrice() : sumPunishMoney;
			punishLog.setDebt(sumPunishMoney);
			punishLog.setOverDay(overDay);
			punishLog.setSumPunishMoney(sumPunishMoney);
			
			punishLogDao.save(punishLog);
		} else { //b
			PunishLog punishLog = punishLogs.get(0);
			//只更新未还款的罚款记录
			if (null == punishLog.getHasReturnDebt() || punishLog.getHasReturnDebt().equals(ConstantsUtils.JUDGE_YES)) {
				return;
			}
			
			//更新罚金和逾期天数
			int overDay = ConstantsUtils.diffDays(new Date(), borrowLog.getExceptReturnTime()) + 1; //不满一天算作一天
			double sumPunishMoney = bookSet.getPrice() * ConstantsUtils.PUNISH_PERCENT_DAY * overDay;
			sumPunishMoney = sumPunishMoney > bookSet.getPrice() ? bookSet.getPrice() : sumPunishMoney;
			
			punishLog.setOverDay(overDay);
			punishLog.setSumPunishMoney(sumPunishMoney);
			punishLog.setDebt(sumPunishMoney);
			
			punishLogDao.update(punishLog);
		}
	}
	
	@Override
	public void returnDebtAndBook(String punishLogId) {
		PunishLog punishLog = punishLogDao.get(punishLogId);
		PunishLogVo punishLogVo = getVo(punishLog);
		if (null == punishLogVo) {
			return;
		}
		
		/**
		 * 1、还款
		 * 欠款debt设为0，将hasReturnDebt设为已还	
		 */
		punishLog.setDebt(0);
		punishLog.setHasReturnDebt(ConstantsUtils.JUDGE_YES);
		punishLogDao.update(punishLog);
		
		/**
		 * 2、还书
		 */
		String userId = punishLog.getUserId();
		String singleBookId = punishLogVo.getSingleBookId();
		borrowLogService.returnBook(userId, singleBookId);
	}

	@Override
	public void returnDebtBook(String borrowLogId) {
		if (StringUtils.isBlank(borrowLogId)) {
			return;
		}
		
		Map<String, Object> params = new HashMap<>();
		params.put("borrowLogLd", borrowLogId);
		List<PunishLog> punishLogs = punishLogDao.queryListByParams(params);
		if (null == punishLogs || punishLogs.isEmpty()) {
			return ;
		}
		
		returnDebtAndBook(punishLogs.get(0).getId());
	}

	@Override
	public Integer getCurCount(String userId) {
		if (StringUtils.isBlank(userId)) {
			return null;
		}
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userId", userId);
		paramMap.put("hasReturnDebt", ConstantsUtils.JUDGE_NOT);
		paramMap.put("punishType", ConstantsUtils.PUNISH_TYPE_OVERDAY);
		List<PunishLog> punishLogs = punishLogDao.queryListByParams(paramMap);
		if (null == punishLogs || punishLogs.isEmpty()) {
			return null;
		}
		
		return punishLogs.size();
	}
	
	/**
	 * 根据罚款记录，获取罚款记录vo
	 * @createUser ying luo
	 * @createDate 2018年4月16日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param punishLog 罚款记录
	 * @return 
	 */
	private PunishLogVo getVo(PunishLog punishLog) {
		if (null == punishLog) {
			return null;
		}
		
		BorrowLog borrowLog = null;
		SingleBook singleBook = null;
		BookSet bookSet = null;
		User user = null;
		
		String borrowLogLd = punishLog.getBorrowLogLd();
		borrowLog = borrowLogService.get(borrowLogLd);
		String userId = punishLog.getUserId();
		user = userService.get(userId);
		
		if (borrowLog != null) {
			String singleBookId = borrowLog.getSingleBookId();
			singleBook = singleBookService.get(singleBookId);
		}
		
		if (singleBook != null) {
			String bookSetId = singleBook.getBookSetId();
			bookSet = bookSetService.get(bookSetId);
		}
		
		return new PunishLogVo(punishLog, borrowLog, singleBook, bookSet, user);
	}
	
	/**
	 * 根据罚款记录列表，获取罚款记录vo列表
	 * @createUser ying luo
	 * @createDate 2018年4月16日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param punishLogs 罚款记录列表
	 * @return
	 */
	private List<PunishLogVo> getVoList(List<PunishLog> punishLogs) {
		if (null == punishLogs || punishLogs.isEmpty()) {
			return null;
		}
		
		List<PunishLogVo> punishLogVos = new ArrayList<>();
		for (PunishLog punishLog: punishLogs) {
			PunishLogVo punishLogVo = getVo(punishLog);
			if (punishLogVo != null) {
				punishLogVos.add(punishLogVo);
			}
		}
		
		return punishLogVos;
	}

	@Override
	public void updateAllPunish() {
		List<BorrowLog> borrowLogs = borrowLogService.getAllNoReturnList();
		if (null == borrowLogs || borrowLogs.isEmpty()) {
			return ;
		}
		
		for (BorrowLog borrowLog: borrowLogs) {
			createPunishLogByBorrowLog(borrowLog);
		}
	}

}
