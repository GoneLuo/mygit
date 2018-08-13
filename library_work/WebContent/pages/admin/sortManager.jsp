<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/common/common.jsp" %>
	<link href="${basePath }/resources/css/zTreeStyle.css" rel="stylesheet">
	<link href="${basePath }/resources/css/treeDemo.css" rel="stylesheet">
	
	<style type="text/css">
		.ztree li span.button.add {
			margin-left:2px; margin-right: -1px; 
			background-position:-144px 0; vertical-align:top; *vertical-align:middle
		}
	</style>
</head>

<body class="layui-layout-body">
	<div class="layui-layout layui-layout-admin">
		<%@ include file="/common/head.jsp" %>
		<%@ include file="/common/admin-side.jsp" %>
		
		<!-- 在头部和顶部定位当前页面 -->
		<script type="text/javascript">
			$('#adminHead').addClass('layui-this');
			$('#sortSide').addClass('layui-this');
		</script>

		<div class="layui-body">
			<!-- 内容主体区域 start-->
			<div style="padding: 15px; width: 50%">
				<ul class="ztree" id="ztreeDemo"></ul>
			</div>
			<!-- 内容主体区域 end-->


			<div class="layui-footer">
				<!-- 底部固定区域 -->
				© layui.com - 底部固定区域
			</div>
		</div>
	
	</div>
	
<script src="${basePath }/resources/js/libs/jquery.ztree.all.js"></script>
<script src="${basePath }/resources/js/modules/admin/sortManager.js?ver=1"></script>
</body>

</html>