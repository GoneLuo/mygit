package com.luoy.library.service;

import com.luoy.library.common.util.PageConfig;
import com.luoy.library.common.vo.TableVo;
import com.luoy.library.common.vo.UserVo;
import com.luoy.library.pojo.User;
import com.luoy.library.pojo.UserInfo;
import com.luoy.library.service.util.IBaseService;

public interface IUserService extends IBaseService<User> {
	
	/**
	 * 更新用户账号状态；注销或恢复
	 * @createUser ying luo
	 * @createDate 2018年3月29日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param id User.id
	 * @param useful 0：注销、1：恢复
	 */
	void setUseful(String id, String useful);
	
	/**
	 * 用户登陆验证
	 * @createUser ying luo
	 * @createDate 2018年3月29日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param userName
	 * @param password
	 * @return 验证成功时，返回该用户vo；否则，返回null
	 */
	UserVo login(String userName, String password);
	
	/**
	 * 分页条件查询所有用户，封装到适应layui.table的TableVo并返回
	 * @createUser ying luo
	 * @createDate 2018年3月29日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param pageConfig 分页
	 * @param userVo UserVo，保存查询条件
	 * @return 适应layui.table的TableVo
	 */
	TableVo<UserVo> findUserByParams(PageConfig pageConfig, UserVo userVo);
	
	/**
	 * 通过用户id，获取UserVo
	 * @createUser ying luo
	 * @createDate 2018年4月9日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param userId
	 * @return
	 */
	UserVo getUserVoById(String userId);

	/**
	 * 验证用户名是否可用，即用户名未被占用
	 * @createUser ying luo
	 * @createDate 2018年4月15日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param userName 用户名
	 * @return true：可用；false：不可用，已被占用
	 */
	boolean valiUserName(String userName);
	
	/**
	 * 注册新用户
	 * @createUser ying luo
	 * @createDate 2018年4月15日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param user
	 * @param userInfo
	 */
	void registerUser(User user, UserInfo userInfo);
	
	/**
	 * 根据userVo，更新指定用户的User和UserInfo信息
	 * @createUser ying luo
	 * @createDate 2018年4月15日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param userVo
	 */
	void changeUserVo(UserVo userVo);
	
	/**
	 * 根据userId，获取UserVo
	 * @createUser ying luo
	 * @createDate 2018年4月15日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param userId
	 * @return
	 */
	UserVo getUserVoByUserId(String userId);
	
	/**
	 * 通过用户名查询用户
	 * @createUser ying luo
	 * @createDate 2018年4月21日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param userName
	 * @return
	 */
	User getByUserName(String userName);
}
