<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/common/common.jsp" %>
	<link href="${basePath }/resources/css/font-awesome.min.css" rel="stylesheet">
	<link href="${basePath }/resources/css/simple-line-icons.css" rel="stylesheet">
	<link href="${basePath }/resources/css/forum.css" rel="stylesheet">
</head>

<body class="">
	<div class="layui-layout-admin">
		<%@ include file="/common/head.jsp" %>
		
		<!-- 在头部和顶部定位当前页面 -->
		<script type="text/javascript">
			$('#homeHead').addClass('layui-this');
		</script>

		<div class="" style="margin: 0 10%;">
			<!-- 内容主体区域 start-->
			<!--图书详情div start-->
			<div class="card">
				<div class="card-header">
					<span class="float-left">图书详情</span>
				</div>
				<div class="card-block">

					<div class="book-cover" style="float: right;">
						<img src="${basePath }/resources/images/forum_img3.jpg" width="110" height="150" />
					</div>

					<!--book-itms start-->
					<div class="book-itms" style="float: left; width: 80%;">
						<div class="form-group row">
							<label class="col-md-3 book-info-label"><b>书名</b> </label>
							<div class="col-md-8">${book.bookName }</div>
						</div>
						<div class="form-group row">
							<label class="col-md-3 book-info-label"><b>ISBN</b> </label>
							<div class="col-md-8">${book.isbn }</div>
						</div>
						<div class="form-group row">
							<label class="col-md-3 book-info-label"><b>分类</b> </label>
							<div class="col-md-8">${book.sortName }</div>
						</div>
						<div class="form-group row">
							<label class="col-md-3 book-info-label"><b>作者</b> </label>
							<div class="col-md-8">${book.author }</div>
						</div>
						<div class="form-group row">
							<label class="col-md-3 book-info-label"><b>译者</b> </label>
							<div class="col-md-8">${book.translator }</div>
						</div>
						<div class="form-group row">
							<label class="col-md-3 book-info-label"><b>出版社</b> </label>
							<div class="col-md-8">${book.press }</div>
						</div>
						<div class="form-group row">
							<label class="col-md-3 book-info-label"><b>定价</b> </label>
							<div class="col-md-8">${book.price }RMB</div>
						</div>
						<div class="form-group row">
							<label class="col-md-3 book-info-label"><b>简介</b> </label>
							<div class="col-md-8">
								${book.summary }
							</div>
						</div>
					</div>
					<!--book-itms end-->

				</div>
			</div>
			<!--图书详情div end-->

			<hr />

			<!--馆藏信息div start-->
			<div class="card">
				<div class="card-header">
					<span> 馆藏信息</span>
				</div>
				<div class="card-content-main">
					<c:if test="${book.status == '0' }">
						<div class="layui-fluid" style="font-size: 25px; color: #435984;">
						  当前图书已下架
						</div>   
					</c:if>
					
					<c:if test="${book.status == '1' }">
						<table class="table table-hover" id="levelSet">
							<thead>
								<tr>
									<th>序号</th>
									<th>图书条码号</th>
									<th>是否借出</th>
									<th>是否馆藏</th>
									<th>操作</th>
								</tr>
							<tbody>
								<c:forEach var="singleBook" items="${singleBooks }" varStatus="status">
									<tr id="${singleBook.id }">
										<td>${status.index }</td>
										<td>${singleBook.barcode }</td>
										<td>
											<c:if test="${singleBook.isBorrowed=='0' }">未借出</c:if>
											<c:if test="${singleBook.isBorrowed=='1' }">借出</c:if>
										</td>
										<td>
											<c:if test="${singleBook.isCollected=='0' }">非馆藏</c:if>
											<c:if test="${singleBook.isCollected=='1' }">馆藏</c:if>
										</td>
										<td>
											<!-- 馆藏本不可预约，不可借 -->
											<!-- 非馆藏并且未借出时，图书才可借 -->
											<!-- 不可借时，才能预约 -->
											<c:choose>
												<c:when test="${singleBook.isCollected=='1'}">
													<button type="button" class="btn btn-primary borrowBook" disabled="true">借书</button>
													<button type="button" class="btn btn-primary orderBook" disabled="true">预约</button>
												</c:when>
												<c:when test="${singleBook.isBorrowed=='0' && singleBook.isCollected=='0'}">
													<button type="button" class="btn btn-primary borrowBook">借书</button>
													<button type="button" class="btn btn-primary orderBook" disabled="true">预约</button>
												</c:when>
												<c:otherwise>
													<button type="button" class="btn btn-primary borrowBook" disabled="true">借书</button>
													<button type="button" class="btn btn-primary orderBook">预约</button>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
				
				</div>
			</div>
			<!--馆藏信息div end-->
			<!-- 内容主体区域 end-->
			
			<!-- foot -->
		    <div><br><hr><br></div>
		</div>
	
	</div>
</body>
<script type="text/javascript">
	/* 加载layui下拉框 */
	layui.use(['layer', 'element'], function(){
		var layer = layui.layer
		, element = layui.element;
		
		/* 借书 */
		$('.borrowBook').on('click', function(){
			var tr = $(this).parent().parent();
			var singleBookId = tr.attr('id');
			$.ajax({
				url: basePath + "/borrowLog/userBorrow",
				type: 'post',
				dataType: 'json',
				data: {
					'singleBookId': singleBookId
				},
				success: function(res){
					layer.msg(res.msg);
					if(res.status){
					//	window.location.reload();
						changeBtnStatus(tr);
					}
				},
				error: function(){
					layer.msg('error');
				}
			}); /* ajax end */
			
		});
	});
	
	/* 改变馆藏信息表中指定行的按钮状态；将“借书”按钮设为不可点击 ，将“预约”按钮设为可点击 */
	function changeBtnStatus(tr){
		var borrowBtn = tr.find('button.borrowBook');
		borrowBtn.attr('disabled', true);
		
		var orderBtn = tr.find('button.orderBook');
		orderBtn.attr('disabled', false);
	}
</script>
</html>
