<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<meta charset="utf-8">
</head>
<body>
<div class="layui-header">
	<div class="layui-logo">图书管理系统</div>
	<!-- 头部区域（可配合layui已有的水平导航） -->
	<ul class="layui-nav layui-layout-left">
		<li class="layui-nav-item" id="homeHead">
			<a href="${basePath }/index">首页</a>
		</li>
		<li class="layui-nav-item" id="sortHead">
			<a href="${basePath }/book/toLibarySort">分类浏览</a>
		</li>
		<li class="layui-nav-item" id="myHead">
			<a href="${basePath }/user/toPersonalLibrary">我的图书馆</a>
		</li>
		
		<!-- 当前用户是管理员时显示 -->
		<c:if test="${CURRENT_USER.role == '0' }">
			<li class="layui-nav-item" id="adminHead">
				<a href="${basePath }/admin/book">后台管理</a>
			</li>
		</c:if>
	</ul>
	<ul class="layui-nav layui-layout-right">
		<li class="layui-nav-item">
			<a href="javascript:;">
				<img src="http://t.cn/RCzsdCq" class="layui-nav-img"> ${CURRENT_USER.name }
			</a>
			<dl class="layui-nav-child">
				<dd>
					<a href="">基本资料</a>
				</dd>
				<dd>
					<a href="">修改密码</a>
				</dd>
			</dl>
		</li>
		<li class="layui-nav-item">
			<a href="${basePath }/logout">退出登录</a>
		</li>
	</ul>
</div>
</body>
</html>