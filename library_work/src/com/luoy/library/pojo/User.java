package com.luoy.library.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 用户表
 * @author ying luo
 *
 */
@Entity
@Table(name = "user")
public class User {
	
	@Id
	@Column(name = "id")
	@GenericGenerator(name = "uuidGen", strategy = "uuid")
	@GeneratedValue(generator = "uuidGen")
	private String id;

	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "password")
	private String password;

	@Column(name = "useful")
	private String useful;
	
	@Column(name = "left_borrow_count")
	private Integer leftBorrowCount;
	
	@Column(name = "left_order_count")
	private Integer leftOrderCount;
	
	@Column(name = "role")
	private String role;
	
	@Column(name = "user_info_id")
	private String userInfoId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUseful() {
		return useful;
	}

	public void setUseful(String useful) {
		this.useful = useful;
	}

	public Integer getLeftBorrowCount() {
		return leftBorrowCount;
	}

	public void setLeftBorrowCount(Integer leftBorrowCount) {
		this.leftBorrowCount = leftBorrowCount;
	}

	public Integer getLeftOrderCount() {
		return leftOrderCount;
	}

	public void setLeftOrderCount(Integer leftOrderCount) {
		this.leftOrderCount = leftOrderCount;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserInfoId() {
		return userInfoId;
	}

	public void setUserInfoId(String userInfoId) {
		this.userInfoId = userInfoId;
	}
}
