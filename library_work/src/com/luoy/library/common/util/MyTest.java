package com.luoy.library.common.util;

public class MyTest {
	public int val = 0;
	public static void main(String[] args) {
		/**
		 * String是不可变的：final声明、内部数据封装
		 */
		String s = "orignal"; 
		/**
		 * 包装内特点：
		 * 1. 所有实例都是只读的
		 * 2. 用基本类型进行赋值时，会自动新建实例。
		 * ps:【直接赋值】-128~127类似于String的变量池、其他值自动装箱时会重新new一个对象
		 */
		Integer i = 0; 
		/**
		 * final声明引用对象时，变量指向的引用不能改变，但是引用内的值可变
		 */
		final MyTest t = new MyTest(); 
		
		StringBuffer sb = new StringBuffer();
		
		System.out.println("before s:" + s);
		System.out.println("before i:" + i);
		System.out.println("before val:" + t.val);
		System.out.println("before sb:" + sb);
		changeStr(s, i, t, sb);
		System.out.println("********************after change**********************");
		System.out.println("after s:" + s);
		System.out.println("after i:" + i);
		System.out.println("after val:" + t.val);
		System.out.println("before sb:" + sb);
	}
	
	public static void changeStr(String s, Integer i, MyTest t, StringBuffer sb) {
		/**
		 * s传进来的是引用，但是重新赋值后指向的是另一个引用，原引用还在字符串池中【String不可变】
		 */
		s = "change"; 
		/**
		 * i原本指向的0不会被修改，而是新建了一个值为1的Integer并将i指向它，或者是重新指向变量池中其他值。
		 */
		i = 1; 
		t.val = 3;
		sb.append("_after");
	}

}
