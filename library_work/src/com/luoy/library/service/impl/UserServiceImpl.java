package com.luoy.library.service.impl;

import java.util.ArrayList;
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
import com.luoy.library.common.vo.TableVo;
import com.luoy.library.common.vo.UserVo;
import com.luoy.library.dao.IUserDao;
import com.luoy.library.dao.util.IBaseDao;
import com.luoy.library.pojo.BookSet;
import com.luoy.library.pojo.User;
import com.luoy.library.pojo.UserInfo;
import com.luoy.library.service.IUserInfoService;
import com.luoy.library.service.IUserService;
import com.luoy.library.service.util.BaseServiceImpl;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IUserInfoService userInfoService;

	@Override
	public IBaseDao<User> getDao() {
		return (IBaseDao<User>)userDao;
	}
	
	@Transactional(readOnly = true)
	@Override
	public UserVo login(String userName, String password) {
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userName", userName);
		paramMap.put("password", password);
		List<User> userList = userDao.queryUserBypParamMap(paramMap);
		
		if (userList != null && !userList.isEmpty()) {
			User user = userList.get(0);
			if (user != null && user.getUseful().equals(ConstantsUtils.USEFUL)) {
				return getUserVo(user);
			}
		}
		
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public TableVo<UserVo> findUserByParams(PageConfig pageConfig, UserVo userVo) {
		/**
		 * 根据首页设计，组合查询条件
		 * 【多表、多条件、模糊查询】userName、name、role、useful
		 */
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<>();
		if (StringUtils.isNoneBlank(userVo.getName())) {
			hql = new StringBuffer("select u from User u, UserInfo ui where u.userInfoId = ui.id AND ui.name like ?");
			params.add("%" + userVo.getName() + "%");
		} else {
			hql.append("from User u where 1=1");
		}
		if (StringUtils.isNoneBlank(userVo.getUserName())) {
			hql.append(" and u.userName like ?");
			params.add("%" + userVo.getUserName() + "%");
		} 
		if (StringUtils.isNoneBlank(userVo.getRole())) {
			hql.append(" and u.role = ?");
			params.add(userVo.getRole());
		} 
		if (StringUtils.isNoneBlank(userVo.getUseful())) {
			hql.append(" and u.useful = ?");
			params.add(userVo.getUseful());
		}
		
		List<User> userList = userDao.queryListByHqlAndPage(hql.toString(), params.toArray(), pageConfig);
		
		List<UserVo> voList = getUserVoList(userList);
		TableVo<UserVo> tableVo = new TableVo<>(pageConfig.getRowCount(), voList);
		
		return tableVo;
	}
//	public TableVo<UserVo> findUserByParams(PageConfig pageConfig, UserVo userVo) {
//		
//		/**
//		 * 根据页面设计提取出查询条件
//		 */
//		Map<String, Object> confirmParams = new HashMap<>(); //准确查询条件
//		
//		Map<String, Object> likeParams = new HashMap<>(); //模糊查询条件
//		if (StringUtils.isNotBlank(userVo.getUserName())) {
//			likeParams.put("userName", "%" + userVo.getUserName() + "%");
//		}
//		if (StringUtils.isNotBlank(userVo.getRole())) {
//			likeParams.put("role", userVo.getRole());
//		}
//		if (StringUtils.isNotBlank(userVo.getUseful())) {
//			likeParams.put("useful", userVo.getUseful());
//		}
//		
//		List<User> userList = userDao.queryUserBypParamMapAndPage(confirmParams, likeParams, pageConfig);
//		
//		List<UserVo> voList = getUserVoList(userList);
//		TableVo<UserVo> tableVo = new TableVo<>(pageConfig.getRowCount(), voList);
//		
//		return tableVo;
//	}
	
	private UserVo getUserVo(User user){
		if (null == user) {
			return null;
		}
		String userInfoId = user.getUserInfoId();
		UserInfo userInfo = null;
		if (StringUtils.isNoneBlank(userInfoId)) {
			userInfo = userInfoService.get(userInfoId);
		}
		return new UserVo(user, userInfo);
	}
	
	private List<UserVo> getUserVoList(List<User> userList) {
		if (null == userList) {
			return null;
		}
		
		List<UserVo> voList = new ArrayList<>();
		for (User user: userList) {
			UserVo userVo = getUserVo(user);
			if (userVo == null) {
				continue;
			}
			voList.add(userVo);
		}
		
		return voList;
	}

	@Override
	@Transactional
	public void setUseful(String id, String useful) {
		User user = userDao.get(id);
		user.setUseful(useful);
		userDao.update(user);;
	}

	@Override
	public UserVo getUserVoById(String userId) {
		return getUserVo(userDao.get(userId));
	}

	@Transactional(readOnly = true)
	@Override
	public boolean valiUserName(String userName) {
		Map<String, Object> params = new HashMap<>();
		params.put("userName", userName);
		List<User> users = userDao.queryUserBypParamMap(params);
		
		if (null == users || users.isEmpty()) {
			return true;
		}
		
		return false;
	}

	@Override
	public void registerUser(User user, UserInfo userInfo) {
		userInfoService.save(userInfo);

		user.setUserInfoId(userInfo.getId());
		user.setRole(ConstantsUtils.ROLE_USER);
		user.setUseful(ConstantsUtils.USEFUL);
		user.setLeftBorrowCount(ConstantsUtils.MAX_BORROW_COUNT);
		user.setLeftOrderCount(ConstantsUtils.MAX_ORDER_COUNT);
		userDao.save(user);
	}

	@Override
	public void changeUserVo(UserVo userVo) {
		User user = new User();
		user.setId(userVo.getUserId());
		user.setLeftBorrowCount(userVo.getLeftBorrowCount());
		user.setLeftOrderCount(userVo.getLeftOrderCount());
		user.setPassword(userVo.getPassword());
		user.setRole(userVo.getRole());
		user.setUseful(userVo.getUseful());
		user.setUserInfoId(userVo.getUserInfoId());
		user.setUserName(userVo.getUserName());
		userDao.update(user);
		
		UserInfo userInfo = new UserInfo();
		userInfo.setId(userVo.getUserInfoId());
		userInfo.setCid(userVo.getCid());
		userInfo.setEmail(userVo.getEmail());
		userInfo.setGender(userVo.getGender());
		userInfo.setName(userVo.getName());
		userInfo.setTel(userVo.getTel());
		userInfoService.update(userInfo);
	}

	@Override
	public UserVo getUserVoByUserId(String userId) {
		User user = userDao.get(userId);
		if (null == user){
			return null;
		}
		return getUserVo(user);
	}

	@Override
	public User getByUserName(String userName) {
		if (StringUtils.isBlank(userName)) {
			return null;
		}
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userName", userName);
		List<User> users = userDao.queryUserBypParamMap(paramMap);
		if (null == users || users.isEmpty()) {
			return null;
		}
		
		return users.get(0);
	}


}
