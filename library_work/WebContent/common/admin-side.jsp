<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<meta charset="utf-8">
</head>
<body>
	<div class="layui-side layui-bg-black">
		<div class="layui-side-scroll">
			<!-- 左侧导航区域（可配合layui已有的垂直导航） -->
			<ul class="layui-nav layui-nav-tree" lay-filter="test">
				<li class="layui-nav-item layui-nav-itemed" id="bookSide">
					<a href="${basePath }/admin/book">图书管理</a>
				</li>
				<li class="layui-nav-item" id="sortSide">
					<a href="${basePath }/admin/sort">图书分类管理</a>
				</li>
				<li class="layui-nav-item" id="userSide">
					<a href="${basePath }/admin/user">用户管理</a>
				</li>
				<li class="layui-nav-item" id="borrowSide">
					<a href="${basePath }/admin/borrow">用户借阅管理</a>
				</li>
				<li class="layui-nav-item" id="punishSide">
					<a href="${basePath }/admin/punish">用户违章管理</a>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>