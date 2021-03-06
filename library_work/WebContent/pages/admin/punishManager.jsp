<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/common/common.jsp" %>
</head>

<script type="text/html" id="indexTpl">
{{d.LAY_INDEX}}
</script>

<body class="layui-layout-body">
	<div class="layui-layout layui-layout-admin">
		<%@ include file="/common/head.jsp" %>
		<%@ include file="/common/admin-side.jsp" %>
		
		<!-- 在头部和顶部定位当前页面 -->
		<script type="text/javascript">
			$('#adminHead').addClass('layui-this');
			$('#punishSide').addClass('layui-this');
		</script>

		<div class="layui-body">
			<!-- 内容主体区域 start-->
			<div style="padding: 15px;">
				<table class="table table-hover" id="punishLogSet" lay-filter="punishLogSetFilter">
				</table>
			</div>
			<!-- 内容主体区域 end-->


			<div class="layui-footer">
				<!-- 底部固定区域 -->
				© layui.com - 底部固定区域
			</div>
		</div>
	
	</div>
</body>

<script type="text/html" id="indexTpl">
	{{d.LAY_INDEX}}
</script>
<script type="text/html" id="opreBar">
<button class="btn btn-primary returnBtn" lay-event="returnDebtAndBook">管理员帮还</button>
</script>
<script type="text/javascript">
layui.use(['table', 'layer', 'element'], function(){ 
	var layer = layui.layer
	,table = layui.table
	,element = layui.element; /* 加载layui下拉框 */
	
	/* 渲染表格 */
	  table.render({
	    elem: '#punishLogSet'
	    ,url: basePath + '/punishLog/admin/allList'
	    ,method : 'POST'
	    ,page: true
	    ,id : 'punishLogSetReload'
	    ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
	    ,cols: [[
	      {field:'no', title: '序号', templet:"#indexTpl", width:60}
	      ,{field:'barcode', title: '条形码', width:120}
	      ,{field:'bookName', title: '书名', width:100}
	      ,{field:'userName', title: '用户名', width:100}
	      ,{field:'borrowTime', title: '借书日期', width:160}
	      ,{field:'exceptReturnTime', title: '限定还书日期', width:160}
	      ,{field:'actualReturnTime', title: '还书日期', width:160}
	      ,{field:'debt', title: '当前欠款', width:90}
	      ,{field:'sumPunishMoney', title: '历史欠款', width:90}
	      ,{field:'opreation', title: '操作', toolbar:'#opreBar', width:150}
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
	          var opreationTds = $("[data-field='opreation']").children();
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
	        	  
	        	  var $bookNameTd = $(bookNameTds[i + 1]);
	        	  $bookNameTd.html($a);
	        	  
	        	  /* 日期格式 */
	        	  var $borrowTimeTd = $(borrowTimeTds[i + 1]);
	        	  $borrowTimeTd.text(jqueryDataFormat($borrowTimeTd.text()));
	        	  
	        	  var $exceptReturnTimeTd = $(exceptReturnTimeTds[i + 1]);
	        	  if($exceptReturnTimeTd.text() != ''){
	        		  $exceptReturnTimeTd.text(jqueryDataFormat($exceptReturnTimeTd.text()));
	        	  }
	        	  
	        	 var $actualReturnTimeTd = $(actualReturnTimeTds[i + 1]);
	        	  if(res.data[i]['isReturn'] === '1'){
	        		  $actualReturnTimeTd.text(jqueryDataFormat($actualReturnTimeTd.text()));
	        	  } else {
	        		  $actualReturnTimeTd.text('');
	        	  }
	        	  
	        	  var $opreationTd = $(opreationTds[i + 1]);
	        	  if(res.data[i]['hasReturnDebt'] === '1'){
	        		  var returnBtn = $opreationTd.find('button.returnBtn');
	        		  returnBtn.attr('disabled', true);
	        	  }
	        	  
	        	  
	          }
	          
	  	}/* done end */
	  
	  });/*table.render  end */ 
	  
	  /* table监听 */
	  table.on('tool(punishLogSetFilter)', function(obj){
		 var data = obj.data;
		 if(obj.event === 'returnDebtAndBook'){
			 returnDebtAndBook(data.id);
		 }
	  });
	  
	  /* 还款并还书 */
	  function returnDebtAndBook(punishLogId){
		  $.ajax({
				url: basePath + "/punishLog/returnDebtAndBook",
				type: 'post',
				dataType: 'json',
				data: {
					'punishLogId': punishLogId
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
	  
	  /* table重载 */
	  function punishLogSetReload(){
	      table.reload('punishLogSetReload', {
      		method : 'POST',
      		where : {
      	//		isReturn: $('#isReturn').val(),
            }
      	  });
	  }
		
});

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