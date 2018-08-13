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
			<div style="padding: 15px;" class="layui-row">
				<div class="layui-col-md3">
					<ul class="ztree" id="ztreeDemo"></ul>
				</div>
				
				<div class="card-content layui-col-md8" style="margin-left: 20px; margin-top: 10px;">
					<div class="card-header">
                         <span><i class="fa fa-code"></i> 图书管理</span>
                         <span class="float-right">
                         	<button type="button" class="btn btn-primary" id="addBookSet">添加图书
                         	</button>
                         </span>
                     </div>
					<div class="card-content-main">
						<table class="table table-hover" id="bookTable" lay-filter="bookTableFilter">
						</table>
					</div>
                </div>
			</div>
			<!-- 内容主体区域 end-->


			<div class="layui-footer">
				<!-- 底部固定区域 -->
				© layui.com - 底部固定区域
			</div>
		</div>
	
	</div>
	
<script type="text/html" id="indexTpl">
	{{d.LAY_INDEX}}
</script>
<script type="text/html" id="opreBar">
<button class="btn btn-primary" lay-event="editBook">编辑</button>&nbsp;&nbsp;
<button class="btn btn-danger" lay-event="delBook">删除</button>
</script>

<script src="${basePath }/resources/js/libs/jquery.ztree.all.js"></script>
<script src="${basePath }/resources/js/modules/admin/sortManager.js?ver=1"></script>
<script src="${basePath }/resources/js/modules/admin/bookManager.js?ver=1"></script>
</body>

</html>