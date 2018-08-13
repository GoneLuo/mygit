package com.luoy.library.common.vo;

import java.util.Date;

import javax.persistence.Column;

import com.luoy.library.pojo.BookSet;
import com.luoy.library.pojo.BorrowLog;
import com.luoy.library.pojo.PunishLog;
import com.luoy.library.pojo.SingleBook;
import com.luoy.library.pojo.User;

/**
 * 罚款记录vo
 * @author ying luo
 * @createDate 2018年4月16日
 */
public class PunishLogVo {
	/**
	 * PunishLog
	 */
	private String id;
	private String userId;
	private Integer overDay;
	private double sumPunishMoney;
	private double debt;
	private String punishType;
	private String borrowLogLd;
	private String hasReturnDebt;
	
	/**
	 * BorrowLog
	 */
	private String singleBookId;
	private Date borrowTime;
	private Date exceptReturnTime;
	private Date actualReturnTime;
	private String isReturn;
	
	/**
	 * SingleBook
	 */
	private String barcode;
	private String bookSetId;
	
	/**
	 * BookSet
	 */
	private String bookName;
	private double price;
	
	/**
	 * User
	 */
	private String userName;
	
	public PunishLogVo(){}
	
	public PunishLogVo(PunishLog punishLog, BorrowLog borrowLog, SingleBook singleBook, BookSet bookSet, User user) {
		if (punishLog != null) {
			this.id = punishLog.getId();
			this.userId = punishLog.getUserId();
			this.overDay = punishLog.getOverDay();
			this.sumPunishMoney = punishLog.getSumPunishMoney();
			this.debt = punishLog.getDebt();
			this.punishType = punishLog.getPunishType();
			this.borrowLogLd = punishLog.getBorrowLogLd();
			this.hasReturnDebt = punishLog.getHasReturnDebt();
		}
		
		if (borrowLog != null) {
			this.singleBookId = borrowLog.getSingleBookId();
			this.borrowTime = borrowLog.getBorrowTime();
			this.exceptReturnTime = borrowLog.getExceptReturnTime();
			this.actualReturnTime = borrowLog.getActualReturnTime();
			this.isReturn = borrowLog.getIsReturn();
		}
		
		if (singleBook != null) {
			this.barcode = singleBook.getBarcode();
			this.bookSetId = singleBook.getBookSetId();
		}
		
		if (bookSet != null) {
			this.bookName = bookSet.getBookName();
			this.price = bookSet.getPrice();
		}
		
		if (user != null) {
			this.userName = user.getUserName();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHasReturnDebt() {
		return hasReturnDebt;
	}

	public void setHasReturnDebt(String hasReturnDebt) {
		this.hasReturnDebt = hasReturnDebt;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getOverDay() {
		return overDay;
	}

	public void setOverDay(Integer overDay) {
		this.overDay = overDay;
	}

	public double getSumPunishMoney() {
		return sumPunishMoney;
	}

	public void setSumPunishMoney(double sumPunishMoney) {
		this.sumPunishMoney = sumPunishMoney;
	}

	public double getDebt() {
		return debt;
	}

	public void setDebt(double debt) {
		this.debt = debt;
	}

	public String getPunishType() {
		return punishType;
	}

	public void setPunishType(String punishType) {
		this.punishType = punishType;
	}

	public String getBorrowLogLd() {
		return borrowLogLd;
	}

	public void setBorrowLogLd(String borrowLogLd) {
		this.borrowLogLd = borrowLogLd;
	}

	public String getSingleBookId() {
		return singleBookId;
	}

	public void setSingleBookId(String singleBookId) {
		this.singleBookId = singleBookId;
	}

	public Date getBorrowTime() {
		return borrowTime;
	}

	public void setBorrowTime(Date borrowTime) {
		this.borrowTime = borrowTime;
	}

	public Date getExceptReturnTime() {
		return exceptReturnTime;
	}

	public void setExceptReturnTime(Date exceptReturnTime) {
		this.exceptReturnTime = exceptReturnTime;
	}

	public Date getActualReturnTime() {
		return actualReturnTime;
	}

	public void setActualReturnTime(Date actualReturnTime) {
		this.actualReturnTime = actualReturnTime;
	}

	public String getIsReturn() {
		return isReturn;
	}

	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getBookSetId() {
		return bookSetId;
	}

	public void setBookSetId(String bookSetId) {
		this.bookSetId = bookSetId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
