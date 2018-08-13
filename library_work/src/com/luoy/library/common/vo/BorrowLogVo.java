package com.luoy.library.common.vo;

import java.util.Date;

import javax.persistence.Column;

import com.luoy.library.pojo.BookSet;
import com.luoy.library.pojo.BorrowLog;
import com.luoy.library.pojo.SingleBook;
import com.luoy.library.pojo.User;

/**
 * 借阅记录vo
 * @author ying luo
 * @createDate 2018年4月6日
 */
public class BorrowLogVo {
	
	/**
	 * BorrowLog
	 */
	private String id; //借阅记录id
	private String userId;
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
	private String isbn;
	private String author;
	private String press;
	
	/**
	 * User
	 */
	private String userName;
	
	public BorrowLogVo(){}
	
	public BorrowLogVo(BorrowLog borrowLog, SingleBook singleBook, BookSet bookSet, User user) {
		if (borrowLog != null) {
			this.id = borrowLog.getId();
			this.userId = borrowLog.getUserId();
			this.singleBookId = borrowLog.getSingleBookId();
			this.borrowTime = borrowLog.getBorrowTime();
			this.exceptReturnTime = borrowLog.getExceptReturnTime();
			this.actualReturnTime = borrowLog.getActualReturnTime();
			this.isReturn = borrowLog.getIsReturn();
		}
		
		if (singleBook != null) {
			this.barcode = singleBook.getBarcode();
			bookSetId = singleBook.getBookSetId();
		}
		
		if (bookSet != null) {
			this.bookName = bookSet.getBookName();
			this.isbn = bookSet.getIsbn();
			this.author = bookSet.getAuthor();
			this.press = bookSet.getPress();
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPress() {
		return press;
	}

	public void setPress(String press) {
		this.press = press;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
