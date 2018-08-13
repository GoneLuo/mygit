
layui.use(['layer', 'table', 'form', 'element'], function(){
	  var layer = layui.layer
	  ,table = layui.table
	  ,form = layui.form
	  ,element = layui.element;
	  
	  /* 渲染表格 */
	  table.render({
	    elem: '#userSet'
	    ,url: basePath + '/user/admin/searchUser'
	    ,method : 'POST'
	    ,page: true
	    ,id : 'userSetReload'
	    ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
	    ,cols: [[
	       {type:'checkbox'}
	      ,{field:'no', title: '序号', templet:"#indexTpl"}
	      ,{field:'userName', title: '用户名', sort: true}
	      ,{field:'name', title: '真实姓名'}
	      ,{field:'gender',title: '性别', templet:"#genderTpl"}
	      /* ,{field:'cid', title: '身份证号', minWidth: 100} //minWidth：局部定义当前单元格的最小宽度，layui 2.2.1 新增
	      ,{field:'email', title: 'email'}
	      ,{field:'tel', title: '手机号码'} */
	      ,{field:'useful',title: '账号状态'}
	      ,{field: 'opreation', title: '操作', toolbar: '#opreBar'} 
	    ]]
		 , request: {
	          pageName: 'pageNum' //页码的参数名称，默认：page
	         ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
	     }
		 ,done: function(res, page, count){
			  //如果是异步请求数据方式，res即为你接口返回的信息。  
	          //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度  
			 
		  		if(res.data == null){
		  			return;
		  		}
			  //性别显示中文名称  
	          $("[data-field='gender']").children().each(function(){
	        	  if($(this).text()==0){  
                     $(this).text("女")  
                  }else if($(this).text()==1){  
                     $(this).text("男")  
                  } 
	        	  
	          });/* each end */
	          
	          //账号状态显示中文名称  
	          $("[data-field='useful']").children().each(function(){
	        	  if($(this).text()==0){  
                     $(this).text("无效")  
                  }else if($(this).text()==1){  
                     $(this).text("有效")  
                  } 
	        	  
	          });/* each end */
	     
		 }/*done  end */ 
		 
	  });/*table.render  end */ 
	  
	  /* table重载 */
	  function userSetReload(){
	      table.reload('userSetReload', {
      		method : 'POST',
      		where : {
      			userName: $('#userName').val(),
	        	role: $('#roleType').val(),
	            useful: $('#statusType').val(),
	            name: $('#realName').val()
            }
      	  });
	  }
	  
	  $('#searchByCondition').on('click', function(){
		  userSetReload();
	  })
		  
	/* 监听表格工具条*/
	table.on('tool(userSetFilter)', function(obj){
		var data = obj.data;
		if(obj.event === 'destroy'){ //注销
			var id = data.userId;
			$.ajax({
				url: basePath + "/user/admin/destoryUser",
				type: 'post',
				dataType: 'json',
				data: {
					'id': id
				},
				success: function(data){
					layer.msg(data.msg);
					userSetReload();
				},
				error: function(){
					layer.msg('error');
				}
			});/* ajax end */
		} else if(obj.event === 'recovery'){ //恢复
			var id = data.userId;
			$.ajax({
				url: basePath + "/user/admin/recoveryUser",
				type: 'post',
				dataType: 'json',
				data: {
					'id': id
				},
				success: function(data){
					layer.msg(data.msg);
					userSetReload();
				},
				error: function(){
					layer.msg('error');
				}
			});/* ajax end */
		}
		
	})/*table.on  end */ 
	
	/* 注销选中用户 */
	$('#destorySelectUser').on('click', function(){
		//获取table复选框选中数据 
		var checkStatus = table.checkStatus('userSet')
	      ,data = checkStatus.data;
		
		console.log(checkStatus);
		console.log(data);
	});
	  
	 /* 恢复选中用户 */
	$('#coverySelectUser').on('click', function(){
		//获取table复选框选中数据 
		var checkStatus = table.checkStatus('userSet')
	      ,data = checkStatus.data;
	});
	
}); /* layui end */
