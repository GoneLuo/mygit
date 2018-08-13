<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/common/common.jsp" %>
</head>

<body class="layui-layout-body">
	<div class="layui-layout layui-layout-admin">
		<%@ include file="/common/head.jsp" %>
		<%@ include file="/common/admin-side.jsp" %>
		
		<!-- 在头部和顶部定位当前页面 -->
		<script type="text/javascript">
			$('#adminHead').addClass('layui-this');
			$('#borrowSide').addClass('layui-this');
		</script>

		<div class="layui-body">
			<!-- 内容主体区域 start-->
			<div style="padding: 15px;">
				<div class="card">
					<div class="card-header">
                        <span><i class="fa fa-code"></i> 用户借阅记录</span>
                        <span class="float-right">
                        	<button type="button" class="btn btn-primary" id="adminBorrowBtn">用户借书
                        	</button>
                        </span>
                    </div>
					<div class="card-content"><table class="table table-hover" id="borrowLogSet" lay-filter="borrowLogSetFilter"></table></div>
				</div>
			</div>
			<!-- 内容主体区域 end-->

			<div class="layui-footer">
				<!-- 底部固定区域 -->
				© layui.com - 底部固定区域
			</div>
		</div>
	
	</div>
	
	<!-- 管理员提用户借书模态框 -->
	<div id="adminBorrowDiv" style="display: none;">
		<form class="layui-form" id="" style="padding: 0px 0px 0px 10px;">
			<table style="border: 0; width: 400px; height: 180px;">
				<tbody>
					<tr>
						<td width="30%"><label>用户名：</label></td>
						<td colspan="1"><input type="text" name="userName"
							class="layui-input" lay-verify="required|userIsExist"></td>
					</tr>
					<tr>
						<td><label> 图书ISBN：</label></td>
						<td colspan="1"><input type="text" name="isbn"
							class="layui-input" lay-verify="required|bookIsExist"></td>
					</tr>
				</tbody>
			</table>
			<div class="layui-form-item" style="margin-left: 14%;">
			    <div class="layui-input-block">
			      <button class="layui-btn" lay-submit="" lay-filter="borrowBtn">提交</button>
			      <button id="cancelBorrowBtn" class="layui-btn layui-btn-primary">取消</button>
			    </div>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		$('#adminBorrowDiv').hide();
	</script>
	
</body>

<script type="text/html" id="indexTpl">
	{{d.LAY_INDEX}}
</script>
<script type="text/html" id="opreBar">
<button class="btn btn-primary returnBook" lay-event="returnBook">还书</button>
</script>

<script type="text/javascript">
	$(function(){
		$('#adminBorrowDiv').hide();
	});

	layui.use(['table', 'layer', 'element', 'form'], function(){ 
		var layer = layui.layer
		,table = layui.table
		,element = layui.element
		,form = layui.form; /* 加载layui下拉框 */
		
		$('#adminBorrowBtn').on('click', function(){
			 var div = $('#adminBorrowDiv');
			 div.show();
			 layer.open({
				 type: 1,
				 title: '用户借书',
				 content: div,
				 resize: false,
				 area: ['470px', '280px'],
			 });
		  });
		
		var userExist, bookExist;
		$('input[name = "isbn"]').on('blur', function(){
			$.ajax({
				url: basePath + "/borrowLog/bookIsExist",
				type: 'post',
				dataType: 'json',
				data: {
					'isbn': $(this).val()
				},
				success: function(res){
					bookExist = res.status;
				},
				error: function(){
					layer.msg('error');
				}
		   }); /* ajax end */
		});
		$('input[name = "userName"]').on('blur', function(){
			$.ajax({
				url: basePath + "/borrowLog/userIsExist",
				type: 'post',
				dataType: 'json',
				data: {
					'userName': $(this).val()
				},
				success: function(res){
					userExist = res.status;
				},
				error: function(){
					layer.msg('error');
				}
		   }); /* ajax end */
		});
		
		//自定义验证规则
		  form.verify({
			userIsExist: function(value){
		      	if(!userExist){
			       return '该用户名的用户不存在';
			    }
			}
		  	,bookIsExist: function(value){
		      	if(!bookExist){
			       return '该isbn的图书不存在';
			    }
			}
		  });
		
		 //监听提交
        form.on('submit(borrowBtn)', function(data){
	        var formData = data.field;
	        var url = basePath + '/borrowLog/adminBorrow';
	         // * 发起请求
	         $.ajax({
		         data: formData,
		         type: "POST",
		         dataType: "JSON",
		         url: url,
		         success: function(res) {
		        	 if(res.status){
		        		 bookSetReload(); //刷新表格记录
		        	 }
		        	 layer.closeAll();
		        	 layer.msg(res.msg);
		         },
		     	error: function(data) {
		     		layer.msg('error');
		     	}
	        }); // end ajax
            return false;
        });
		 
		$('#cancelBorrowBtn').on('click', function(){
			layer.closeAll();
		});
		
		/* 渲染表格 */
		  table.render({
		    elem: '#borrowLogSet'
		    ,url: basePath + '/borrowLog/list'
		    ,method : 'POST'
		    ,page: true
		    ,id : 'borrowLogSetReload'
		//    ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
		    ,cols: [[
		      {field:'no', title: '序号', templet:"#indexTpl", width:80}
		      ,{field:'userName', title: '借书用户', width:100}
		      ,{field:'barcode', title: '条形码', width:120}
		      ,{field:'bookName', title: '书名', width:120}
		      ,{field:'author', title: '作者', width:100}
		      ,{field:'borrowTime', title: '借书日期', width:160}
		      ,{field:'exceptReturnTime', title: '限定还书日期', width:160}
		      ,{field:'actualReturnTime', title: '还书日期', width:160}
		      ,{field: 'opreation', title: '操作', toolbar: '#opreBar', width:150} 
		    ]]
			 , request: {
		          pageName: 'pageNum' //页码的参数名称，默认：page
		         ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
		     } 
		  	, done: function(res, page, count){
		  		 
		  		if(res.data == null){
		  			return;
		  		}
		  		
		          var bookNameTds = $("[data-field='bookName']").children();
		          var borrowTimeTds = $("[data-field='borrowTime']").children();
		          var exceptReturnTimeTds = $("[data-field='exceptReturnTime']").children();
		          var actualReturnTimeTds = $("[data-field='actualReturnTime']").children();
		          for(var i = 0; i < res.data.length; i++){
		        	  /*
				  	   * 给书名加上超链接
				  	   */
		        	  var bookName = res.data[i]['bookName'];
		        	  var bookSetId = res.data[i]['bookSetId'];
		        	  var href = basePath + "/book/bookDetail?bookSetId=" + bookSetId;
		        	  var $a = $('<a href=""></a>');
		        	  $a.attr('href', href);
		        	  $a.html(bookName);
		        	  $a.css('color', 'blue');
		        	  
		        	  var $bookNameTd = $(bookNameTds[parseInt(i) + 1]);
		        	  $bookNameTd.html($a);
		        	  
		        	  /* 日期格式 */
		        	  var $borrowTimeTd = $(borrowTimeTds[parseInt(i) + 1]);
		        	  $borrowTimeTd.text(jqueryDataFormat($borrowTimeTd.text()));
		        	  
		        	  var $exceptReturnTimeTd = $(exceptReturnTimeTds[parseInt(i) + 1]);
		        	  if($exceptReturnTimeTd.text() != ''){
		        		  $exceptReturnTimeTd.text(jqueryDataFormat($exceptReturnTimeTd.text()));
		        	  }
		        	  
		        	  var $actualReturnTimeTd = $(actualReturnTimeTds[parseInt(i) + 1]);
		        	  if(res.data[i]['isReturn'] === '1'){
		        		  $actualReturnTimeTd.text(jqueryDataFormat($actualReturnTimeTd.text()));
		        	  } else {
		        		  $actualReturnTimeTd.text('');
		        	  }
		        	  
		        	  /* 借阅记录状态是“已还”时，不显示“还书”按钮 */
		        	  var isReturn = res.data[i]['isReturn'];
		        	  var opreationTds = $("[data-field='opreation']").children();
		        	  if (isReturn === '1'){
		        		  $(opreationTds[parseInt(i) + 1]).find('button.returnBook').hide();
		        	  }
		        	  
		        	  /* 逾期未还-限定还书日期标红；将按钮改为"还款并还书" */
		        	  if(res.data[i]['exceptReturnTime'] < new Date()){
		        		  $exceptReturnTimeTd.css('color', 'red');
		        		  var $opreationTd = $(opreationTds[i + 1]);
		        		  var returnBtn = $opreationTd.find('button.returnBook');
		        		  returnBtn.html('还款并还书');
		        		  returnBtn.attr('lay-event', 'returnDebtBook');
		        	  }
		          }
		          
		  	}/* done end */
			 
		  });/*table.render  end */ 
		  
		  /* table重载 */
		  function bookSetReload(){
		      table.reload('borrowLogSetReload', {
	      		method : 'POST',
	      		where : {
	      			isReturn: $('#isReturn').val(),
	            }
	      	  });
		  }
		  
		  /* table监听 */
		  table.on('tool(borrowLogSetFilter)', function(obj){
			 var data = obj.data;
			 if(obj.event === 'returnBook'){
				 returnBook(data.singleBookId, data.userId);
			 } else if(obj.event === 'returnDebtBook'){
				 returnDebtBook(data.id);
			 }
		  });
		  
		  /* 还书 */
		  function returnBook(singleBookId, userId){
			  $.ajax({
					url: basePath + "/borrowLog/adminReturn",
					type: 'post',
					dataType: 'json',
					data: {
						'singleBookId': singleBookId,
						"userId": userId
					},
					success: function(res){
						if(res.status){
							window.location.reload();
						} else{
							layer.msg(res.msg);
						}
					},
					error: function(){
						layer.msg('error');
					}
			   }); /* ajax end */
		  }
		  
		  /* 还款并还书 */
		  function returnDebtBook(borrowLogId){
			  $.ajax({
					url: basePath + "/punishLog/returnDebtBook",
					type: 'post',
					dataType: 'json',
					data: {
						'borrowLogId': borrowLogId
					},
					success: function(res){
						if(res.status){
							window.location.reload();
						} else{
							layer.msg(res.msg);
						}
					},
					error: function(){
						layer.msg('error');
					}
			   }); /* ajax end */
		  }
		  
				
	}); /* layui.use end */
	
	function jqueryDataFormat(time){
	    var datetime = new Date();
	    datetime.setTime(time);
	    var year = datetime.getFullYear();
	    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
	    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
	    var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
	    var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
	    var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
	    return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
	}
</script>
</html>