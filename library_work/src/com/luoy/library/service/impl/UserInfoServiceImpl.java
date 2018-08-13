package com.luoy.library.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luoy.library.dao.IUserInfoDao;
import com.luoy.library.dao.util.IBaseDao;
import com.luoy.library.pojo.UserInfo;
import com.luoy.library.service.IUserInfoService;
import com.luoy.library.service.util.BaseServiceImpl;

@Service
@Transactional
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements IUserInfoService {
	
	@Autowired
	private IUserInfoDao userInfoDao;
	
	@Override
	public IBaseDao<UserInfo> getDao() {
		return (IBaseDao<UserInfo>)userInfoDao;
	}

}
