package com.luoy.library.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 借阅记录表
 * @author ying luo
 *
 */
@Entity
@Table(name = "borrow_log")
public class BorrowLog {

	@Id
	@Column(name = "id")
	@GenericGenerator(name = "uuidGen", strategy = "uuid")
	@GeneratedValue(generator = "uuidGen")
	private String id;

	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "single_book_id")
	private String singleBookId;

	@Column(name = "borrow_time")
	private Date borrowTime;
	
	@Column(name = "except_return_time")
	private Date exceptReturnTime;
	
	@Column(name = "actual_return_time")
	private Date actualReturnTime;
	
	@Column(name = "is_return")
	private String isReturn;

	public String getIsReturn() {
		return isReturn;
	}

	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
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
}
