layui.use(['layer', 'table', 'form', 'element'], function(){
		var layer = layui.layer
		,table = layui.table
		,form = layui.form
		,element = layui.element;
		
		 /* 渲染表格 */
		  table.render({
		    elem: '#bookTable'
		    ,url: basePath + '/book/bookSetList'
		    ,method : 'POST'
		    ,page: true
		    ,id : 'bookTableReload'
		//    ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
		    ,cols: [[
		      {field:'no', title: '序号', templet:"#indexTpl"}
		      ,{field:'bookName', title: '书名'}
		      ,{field:'isbn', title: 'ISBN'}
		      ,{field:'sortName', title: '分类'}
		      ,{field:'leftCount', title: '馆藏'}
		   //   ,{field:'leftCount', title: '馆藏', sort: true}
		      ,{field: 'opreation', title: '操作', toolbar: '#opreBar'} 
		    ]]
			 , request: {
		          pageName: 'pageNum' //页码的参数名称，默认：page
		         ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
		     } 
		  	, done: function(res, page, count){
		  		 
		  		if(res.data == null){
		  			return;
		  		}
		  		/**
		  		 * 给书名加上超链接
		  		 */
		          var tds = $("[data-field='bookName']").children();
		          for(var i = 0; i < res.data.length; i++){
		        	  var bookName = res.data[i]['bookName'];
		        	  var id = res.data[i]['id'];
		        	  var href = basePath + "/book/bookDetail?bookSetId=" + id;
		        	  var $a = $('<a href=""></a>');
		        	  $a.attr('href', href);
		        	  $a.html(bookName);
		        	  $a.css('color', 'blue');
		        	  
		        	  var $td = $(tds[parseInt(i) + 1]);
		        	  $td.html($a)
		          }
		          
		  	}/* done end */
			 
		  });/*table.render  end */ 
		  
		  $('#searchByCondition').on('click', function(){
			  bookSetReload();
		  });
		
		$('#addBookSet').on('click', function(){
			layer.open({
				type: 2,
				content: basePath + '/book/admin/toEditInfo',
				title: '新增图书',
				area: ['800px', '550px']
			});
		});
		
		table.on('tool(bookTableFilter)', function(obj){
			var data = obj.data;
			var event = obj.event;
			if(event === 'editBook'){
				editBookInfo(data);
			}else if(event === 'delBook'){
				delBook(data.id);
			}
		});
		
		function editBookInfo(data){
			layer.open({
				type: 2,
				content: basePath + '/book/admin/toEditInfo?bookSetId=' + data.id,
				title: '编辑图书',
				area: ['800px', '550px'],
				success: function(index, layero){
				}
			})
		}
		
		//删除图书，逻辑删除
		function delBook(bookSetId){
			$.ajax({
				url: basePath + "/book/admin/delBook",
				type: 'post',
				dataType: 'json',
				data: {
					'bookSetId': bookSetId
				},
				success: function(res){
					if(res.status){
						bookSetReload();
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
		  function bookSetReload(){
		      var cType = $('#conditionType').val();
		      
			  /*table.reload('bookTableReload', {
	      		method : 'POST',
	      		where : {
	      			cType: $('#conditionContent').val(),
	            }
	      	  });*/
		      
		      if (cType === 'bookName') {
		    	  console.log('bookName');
		    	  
		    	  table.reload('bookTableReload', {
		      		method : 'POST',
		      		where : {
		      			bookName: $('#conditionContent').val(),
		      			sortName: '',
		      			author: '',
		      			isbn: '',
		      			press: '',
		            }
		      	  });
		      } else if (cType === 'sortName') {
		    	  console.log('sortName');
		    	  
		    	  table.reload('bookTableReload', {
		      		method : 'POST',
		      		where : {
		      			bookName: '',
		      			sortName: $('#conditionContent').val(),
		      			author: '',
		      			isbn: '',
		      			press: '',
		            }
		      	  });
		      }else if (cType === 'author') {
		    	  console.log('author');
		    	  
		    	  table.reload('bookTableReload', {
		      		method : 'POST',
		      		where : {
		      			bookName: '',
		      			sortName: '',
		      			author: $('#conditionContent').val(),
		      			isbn: '',
		      			press: '',
		            }
		      	  });
		      }else if (cType === 'isbn') {
		    	  console.log('isbn');
		    	  
		    	  table.reload('bookTableReload', {
		      		method : 'POST',
		      		where : {
		      			bookName: '',
		      			sortName: '',
		      			author: '',
		      			isbn: $('#conditionContent').val(),
		      			press: '',
		            }
		      	  });
		      }else if (cType === 'press') {
		    	  console.log('press');
		    	  
		    	  table.reload('bookTableReload', {
		      		method : 'POST',
		      		where : {
		      			bookName: '',
		      			sortName: '',
		      			author: '',
		      			isbn: '',
		      			press: $('#conditionContent').val(),
		            }
		      	  });
		      }else{
		    	  table.reload('bookTableReload', {
		      		method : 'POST',
		      		where : {
		      			bookName: '',
		      			sortName: '',
		      			author: '',
		      			isbn: '',
		      			press: '',
		            }
		      	  });
		      }
		  }
	});