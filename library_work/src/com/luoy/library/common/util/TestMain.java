package com.luoy.library.common.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestMain {

	public static void main(String[] args) {
		subStr();
		
	}
	
	//格式化double数据，小数点位数
	public static double parseDouble(double d) {
		d = Double.parseDouble(new DecimalFormat("#.0").format(d));
		System.out.println(d);
		
		return d;
	}
	
	/**
	 * 计算date1-date2的天数
	 * @createUser ying luo
	 * @createDate 2018年4月16日
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public Long diffDays(Date date1, Date date2) {
		long nd = 1000 * 24 * 60 * 60;
	    long diff = date2.getTime() - date1.getTime();
	    // 计算差多少天
	    long day = diff / nd;
	    return day;
	}
	
	public static void subStr() {
		String fileName = "123";
		String[] strs = fileName.split("\\.");
		for (String str: strs) {
			System.out.println(str);
		}
		
		StringBuffer s = new StringBuffer("1");
		String ss = s.substring(0, s.length() - 1);
		System.out.println(ss);
	}
	
	/**
	 * 去掉from前的字符
	 */
	public static String hqlSubStr(String hql) {
		int fisrt = hql.indexOf("from");
		String res = hql.substring(fisrt);
		
		return res;
	}

	public static void CalendarTest(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
		
		Date d = c.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		System.out.println(format.format(d));
	}
}
