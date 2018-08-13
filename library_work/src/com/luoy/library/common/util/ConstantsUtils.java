package com.luoy.library.common.util;

import java.util.Date;

/**
 * 系统常量及通用函数工具类
 * @author ying luo
 * @createDate 2018年3月29日
 */
public final class ConstantsUtils {
	
	/**
	 * 登录
	 */
	public static final String CURRENT_USER = "CURRENT_USER"; //session中保存当前用户的属性名
	
	/**
	 * 公用
	 */
	public static final String JUDGE_YES = "1"; //判断-是
	public static final String JUDGE_NOT = "0"; //判断-否
	
	public static final String EDIT_SUCCESS_MSG = "修改成功"; 
	public static final String EDIT_FAIL_MSG = "修改失败"; 
	public static final String DEL_SUCCESS_MSG = "删除成功"; 
	public static final String DEL_FAIL_MSG = "删除失败"; 
	public static final String SAVE_SUCCESS_MSG = "保存成功"; 
	public static final String SAVE_FAIL_MSG = "保存失败"; 
	public static final String SEARCH_SUCCESS_MSG = "查询成功"; 
	public static final String SEARCH_FAIL_MSG = "查询失败"; 
	
	public static final String PARAM_ERROR_MSG = "参数错误"; 
	public static final String SUCCESS_MSG = "成功";
	public static final String FAIL_MSG = "失败";
	
	public static final String EDIT = "1";//编辑
	public static final String ADD = "0";//新增
	
	/**
	 * 用户
	 */
	public static final String USEFUL = "1"; //账号有效
	public static final String USELESS = "0"; //账号无效
	
	public static final String ROLE_ADMIN = "0"; //管理员
	public static final String ROLE_USER = "1"; //普通用户
	
	public static final Integer MAX_BORROW_COUNT = 10; //最大可借阅数
	public static final Integer MAX_ORDER_COUNT = 2; //最大可预约数
	
	/**
	 * 单本图书
	 */
	public static final String BE_BORROWED = "1"; //被借出
	public static final String NOT_BORROWED = "0"; //未借出
	
	public static final String IS_COLLECTED = "1"; //馆藏
	public static final String NOT_COLLECTED = "0"; //非馆藏
	
	/**
	 * 借书记录
	 */
	public static final String IS_RETURN = "1";//已还
	public static final String NOT_RETURN = "0";//未还
	public static final int BORROW_MONTH = 1; //规定的借阅时限，单位‘月’
	
	/**
	 * 分类
	 */
	public static final String ROOT_SORT_ID = "0"; //总分类id
	
	/**
	 * 罚款
	 */
	public static final String PUNISH_TYPE_OVERDAY = "0"; //违章类型 - 超期未还书
	public static final String PUNISH_TYPE_LOSS = "1"; //违章类型 - 图书遗失
	public static final String PUNISH_TYPE_DAMAGE = "2"; //违章类型 - 损坏图书
	
	public static final double PUNISH_PERCENT_DAY = 0.01; //超期未还书-每天罚款定价的百分比
	
	/**
	 * 修改Integer类型的值；规定结果不能超过Integer上限，不能小于0
	 * @createUser ying luo
	 * @createDate 2018年4月5日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param count 初始值
	 * @param changeNum 改变的值；大于0表示增加、小于0表示减少
	 * @return 修改后的值
	 */
	public static final Integer changeInteger(Integer count, int changeNum) {
		count = count == null ? 0 : count;
		
		if (changeNum > 0 && count + changeNum < 0) {
			//正正相加小于0，表示超过了int上限，存int表示的最大值
			count = Integer.MAX_VALUE;
		} else if (changeNum < 0 && count + changeNum < 0) {
			//正负相加小于0，表示库存不足，存0，库存不能存负数
			count = 0;
		} else {
			count += changeNum;
		}
		
		return count;
	}
	
	/**
	 * 去掉hql语句from前的字符
	 * @createUser ying luo
	 * @createDate 2018年4月9日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param hql
	 * @return
	 */
	public final static String hqlSubStr(String hql) {
		int fisrt = hql.indexOf("from");
		String res = hql.substring(fisrt);
		
		return res;
	}
	
	/**
	 * 计算date1-date2的天数
	 * @createUser ying luo
	 * @createDate 2018年4月16日
	 * 
	 * @updateDate
	 * @updateUser
	 * @updateComment
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public final static  int diffDays(Date date1, Date date2) {
		long nd = 1000 * 24 * 60 * 60;
	    long diff = date1.getTime() - date2.getTime();
	    // 计算差多少天
	    int day = (int) (diff / nd);
	    return day;
	}
}
