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
				<li class="layui-nav-item layui-nav-itemed" id="infoSide">
					<a href="${basePath }/user/toPersonalLibrary">我的信息</a>
				</li>
				<li class="layui-nav-item" id="currentSide">
					<a href="${basePath }/user/toCurrentBorrowList">当前借阅</a>
				</li>
				<li class="layui-nav-item" id="borrowSide">
					<a href="${basePath }/user/toBorrowHistory">借阅历史</a>
				</li>
				<li class="layui-nav-item" id="orderSide">
					<a href="${basePath }/user/toOrderList">预约信息</a>
				</li>
				<li class="layui-nav-item" id="punishSide">
					<a href="${basePath }/user/toPunishLog">违章缴款</a>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>