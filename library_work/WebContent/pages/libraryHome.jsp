<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/common/common.jsp" %>
	<link href="${basePath }/resources/css/font-awesome.min.css" rel="stylesheet">
	<link href="${basePath }/resources/css/simple-line-icons.css" rel="stylesheet">
	<link href="${basePath }/resources/css/forum.css" rel="stylesheet">
	
	<!-- 分页css -->
	<link href="${basePath }/resources/css/uplan.min.css" rel="stylesheet">
	<!-- z-tree的css，可以中和分页css冲突 -->
	<link href="${basePath }/resources/css/treeDemo.css" rel="stylesheet">
</head>

<script type="text/javascript">
	$(function(){
		
		var select = $('#conditionType');
		select.change(function(){
			var condiName = select.val();
			$('#conditionContent').attr('name', condiName);
		});
		
		/* 显示下拉框查询条件，根据查询条件初始化输入框的name */
		var conditionType = $('#hideCType').val();
		select.val(conditionType);
		$('#conditionContent').attr('name', conditionType);
		
	});
</script>

<body class="">
	<div class="layui-layout layui-layout-admin">
		<%@ include file="/common/head.jsp" %>
		
		<!-- 在头部和顶部定位当前页面 -->
		<script type="text/javascript">
			$('#homeHead').addClass('layui-this');
		</script>

		<div class="layui-row" style="margin: 0 10%;">
			<!-- 内容主体区域 start-->
			<div style="padding: 15px;">
				<div class="card">
                     <div class="card-header">
                         	图书搜索
                     </div>
                     <div id="forSearch" style="margin: 20px 10px;">
			  			<form action="${basePath}/index" method="post" id="searchForm" >
		                      <ul class="form-inline">
		                      	<input type="hidden" id="pageNum" name="pageNum" value="1">
		                          <li class="form-group">
		                              <label for="goodsId">搜索类型</label> &nbsp;&nbsp;
		                              <input type="hidden" value="${conditionType }" id="hideCType">
		                              <select type="text" id="conditionType" class="form-control" name="goodsId" >
		                                  <option value="">请选择</option>
		                                  <option value="bookName">书名</option>
		                                  <option value="author">作者</option>
		                                  <option value="isbn">ISBN</option>
		                                  <option value="press">出版社</option>
		                                  <option value="sortName">图书类型</option>
		                              </select>
		                          </li> &nbsp;&nbsp;
		                          <li class="form-group">
		                             	<input type="text" id="conditionContent" name="" value="${conditionContent }" class="form-control" />
		                          </li>
		                          <div class="form-group-btn"> 
		                          <input type="reset" class="btn btn-warning" id="resetCondition" value="重置">
		                          <input type="submit" class="btn btn-primary" id="searchByCondition" value="搜索">
		                          </div>
		                      </ul>
		                  </form>
					</div>
                 </div>
                 <div class="card card-accent-primary">
                     <div class="card-header">
                         <span class="float-left">热门图书</span>
                     </div>
                     <div class="card-block">
                         <div class="theme-con" id="bookList">
                             <ul>
								<c:forEach var="bookSet" items="${page.dataList }" varStatus="status">
									<li class="theme-list clearfix">
	                                     <div class="theme-list-img">
	                                         <a href="f" class="headportrait" target="_blank" rel="noopener noreferrer">
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
                 </div>
                 
                 <!-- 分页 start -->
                 <div class="up-clearfix">
                     <div class="up-pull-right" style="line-height: 63px;">
                         <%@include file="/common/page.jsp" %>
                     </div>
                 </div>
                 <!-- 分页 start -->
    
			</div>
			<!-- 内容主体区域 end-->

			<!-- foot -->
		    <div><br><hr><br></div>
		</div>
	
	</div>
</body>
<script type="text/javascript">
	/* 加载layui下拉框 */
	layui.use(['element'], function(){
	});
</script>
</html>
