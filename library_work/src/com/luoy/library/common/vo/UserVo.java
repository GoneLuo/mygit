package com.luoy.library.common.vo;

import javax.persistence.Column;

import com.luoy.library.pojo.User;
import com.luoy.library.pojo.UserInfo;

public class UserVo {
	/**
	 * User的属性
	 */
	private String userId;
	private String userName;
	private String password;
	private String useful;
	private Integer leftBorrowCount;
	private Integer leftOrderCount;
	private String role;
	
	/**
	 * UserInfo的属性
	 */
	private String userInfoId;
	private String name;
	private String cid;
	private String tel;
	private String email;
	private String gender;
	private String headPic;
	
	public UserVo(){}
	
	public UserVo(User user, UserInfo userInfo) {
		if (user != null) {
			this.userId = user.getId();
			this.userName = user.getUserName();
			this.password = user.getPassword();
			this.useful = user.getUseful();
			this.leftBorrowCount = user.getLeftBorrowCount();
			this.leftOrderCount = user.getLeftOrderCount();
			this.role = user.getRole();
		}
		
		if (userInfo != null) {
			this.userInfoId = user.getUserInfoId();
			this.name = userInfo.getName();
			this.cid = userInfo.getCid();
			this.tel = userInfo.getTel();
			this.email = userInfo.getEmail();
			this.gender = userInfo.getGender();
			this.headPic = userInfo.getHeadPic();
		}
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}


}
