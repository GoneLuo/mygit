<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/common/common.jsp" %>
	<!-- 分页css -->
	<link href="${basePath }/resources/css/uplan.min.css" rel="stylesheet">
	<link href="${basePath }/resources/css/font-awesome.min.css" rel="stylesheet">
	<link href="${basePath }/resources/css/simple-line-icons.css" rel="stylesheet">
	<link href="${basePath }/resources/css/forum.css" rel="stylesheet">
	
	<link href="${basePath }/resources/css/zTreeStyle.css" rel="stylesheet">
	<link href="${basePath }/resources/css/treeDemo.css" rel="stylesheet">
</head>

<body class="">
	<div class="layui-layout layui-layout-admin">
		<%@ include file="/common/head.jsp" %>
		
		<!-- 在头部和顶部定位当前页面 -->
		<script type="text/javascript">
			$('#sortHead').addClass('layui-this');
		</script>
		
		<div class="layui-row">
			<!-- 内容主体区域 start-->
			<div style="padding: 15px;">
				<div class="layui-col-md2">
					<!-- 左侧导航区域（可配合layui已有的垂直导航） -->
					<ul class="ztree" id="ztreeDemo" style="height: 650px;"></ul>
					<!-- 加载页面时保存当前分类id -->
					<input type="hidden" id="curSortId" value="${sortId }">
				</div>
				<div class="card layui-col-md9">
					<!-- <div class="card-header">
                         <span class="float-left">热门图书</span>
                     </div> -->
                     <div class="card-block">
                         <div class="theme-con" id="bookList">
                             <ul>
								<c:forEach var="bookSet" items="${page.dataList }" varStatus="status">
									<li class="theme-list clearfix">
	                                     <div class="theme-list-img">
	                                         <a href="#" class="headportrait" target="_blank" rel="noopener noreferrer">
	                                             <img src="${basePath }/resources/images/forum_img4.jpg" style="display: block;">
	                                         </a>
	                                     </div>
	                                     <div class="theme-list-con">
	                                         <div class="title">
	                                             <a href="${basePath }/book/bookDetail?bookSetId=${bookSet.id }"  class="title-name" target="_blank">${bookSet.bookName }</a>
	                                         </div>
	                                         <div class="auth-msg clearfix">
	                                             ${bookSet.author }
	                                             <span class="time txt"> <i>·</i> ${bookSet.sortName }</span>
	                                             <span class="time txt"> <i>·</i> ${bookSet.press }</span>
	                                             <p class="forum-see float-right">
	                                                 <span class=""><i class="layui-icon">&#xe756;</i>${bookSet.borrowedCount }</span>
	                                             </p>
	                                         </div>
	                                     </div>
	                                 </li>
								</c:forEach>
                             </ul>
                         </div>
                     </div>
                 
                 <!-- 分页 start -->
                 <form action="${basePath}/book/toLibarySort?sortId=${sortId}" method="post" id="searchForm" >
                     <input type="hidden" id="pageNum" name="pageNum" value="1">
                 </form>
                 <div class="up-clearfix">
                     <div class="up-pull-right" style="line-height: 63px;">
                         <%@include file="/common/page.jsp" %>
                     </div>
                 </div>
                 <!-- 分页 start -->
			</div>
			<!-- 内容主体区域 end-->
		</div>

			<!-- foot -->
		    <div><br><hr><br></div>
		</div>
	
	</div>
</body>
<script src="${basePath }/resources/js/libs/jquery.ztree.all.js"></script>
<script type="text/javascript">
layui.use(['layer', 'element'], function(){
	var layer = layui.layer
	,element = layui.element;
	
	var zTreeObj;
	/* 初始化分类树 */
	function initZtree(){
		var treeSetting = {
			treeId:"ztreeDemo",
			data:{
				simpleData: {
					enable: true,
					idKey:"id",
					pIdKey:"parentId",
					rootPId:null
				}
			},
			view: {
				showIcon: true,
				selectedMulti: false
			},
			callback: {
				onClick: clickSort
			}

		};
		var log, className = "dark";
		
		$.ajax({
			type: 'post',
			url: basePath + "/sort/getSortTree",
			data: {},
			dataType:'JSON',
			success: function(data){
				zTreeObj = $.fn.zTree.init($("#ztreeDemo"), treeSetting, data);
				
				/* 定位当前分类节点 */
				var curSortId = $('#curSortId').val();
				var curNode = zTreeObj.getNodeByParam('id', curSortId);
				zTreeObj.selectNode(curNode);
				zTreeObj.expandNode(curNode, true, false, true);
			},
			error: function(){
				layer.msg('error');
			}
		});/* ajax end */
	}
	
	//节点点击事件
	function clickSort(event, treeId, treeNode){
		var sortId = treeNode.id;
		window.location.href = basePath + '/book/toLibarySort?sortId=' + sortId;
	}
	
	$(document).ready(function(sortId){
		initZtree();
	});
});

</script>
</html>