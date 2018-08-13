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
	<form class="layui-form" action="" method="post" style="margin-top: 10px;margin-right: 20px">
	  <!-- 新增图书或者编辑图书的判断标志，页面加载时设置 -->
	  <input type="hidden" name="editFlag" id="editFlag" value="${editFlag }">
	  
	  <!-- 修改图书时，需要保存图书id -->
	  <input type="hidden" name="id" value="${bookSet.id }">
	  
	  <!-- 修改图书时，需要保存图书status -->
	  <input type="hidden" name="status" value="${bookSet.status }">
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label">书名</label>
	    <div class="layui-input-block">
	      <input type="text" name="bookName" value="${bookSet.bookName }" required lay-verify="required" placeholder="请输入书名" autocomplete="off" class="layui-input">
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">ISBN</label>
	    <div class="layui-input-block">
	      <input type="text" name="isbn" id="isbn" value="${bookSet.isbn }" required  lay-verify="required|valiIsbn" placeholder="请输入ISBN" autocomplete="off" class="layui-input">
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">分类</label>
	    <div class="layui-input-block">
	    	<!-- 隐藏保存当前所属分类id -->
	    	<input type="hidden" id="sortId" name="sortId" value="${bookSet.sortId }">
	        <input type="text" id="sortName" readonly="readonly" name="sortName" value="${bookSet.sortName }" required  lay-verify="required" placeholder="请输入分类" autocomplete="off" class="layui-input">
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <div style="float: left;">
	    	<label class="layui-form-label">作者</label>
		    <div class="layui-input-block">
		      <input type="text" name="author" value="${bookSet.author }" required  lay-verify="required" placeholder="请输入作者" autocomplete="off" class="layui-input">
		    </div>
	    </div>
	    
	    <div style="float: left;">
	    	<label class="layui-form-label">译者</label>
		    <div class="layui-input-block">
		      <input type="text" name="translator" value="${bookSet.translator }" placeholder="请输入译者" autocomplete="off" class="layui-input">
		    </div>
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">出版社</label>
	    <div class="layui-input-block">
	      <input type="text" name="press" value="${bookSet.press }" required  lay-verify="required" placeholder="请输入出版社" autocomplete="off" class="layui-input">
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <div style="float: left;">
	    	<label class="layui-form-label">定价</label>
		    <div class="layui-input-block">
		      <input type="text" name="price" value="${bookSet.price }"placeholder="请输入定价" lay-verify="price1|price2" autocomplete="off" class="layui-input">
		    </div>
	    </div>
	    
	    <div style="float: left;">
	    	<label class="layui-form-label">总数量</label>
		    <div class="layui-input-block">
		      <!-- 编辑图书时，保存原来的总数量，保证只增不减 -->
		      <input type="hidden" value="${bookSet.sum }" id="origalSum">
		      <input type="text" name="sum" value="${bookSet.sum }" required  lay-verify="num|addCount" placeholder="请输入数量" autocomplete="off" class="layui-input">
		    </div>
	    </div>
	  </div>
	  <div class="layui-form-item layui-form-text">
	    <label class="layui-form-label">简介</label>
	    <div class="layui-input-block">
	      <textarea name="summary"  placeholder="请输入简介" class="layui-textarea">${bookSet.summary }</textarea>
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <div class="layui-input-block" style="margin-left: 40%">
	      <button class="layui-btn" lay-submit lay-filter="bookSubmit">立即提交</button>
	      <button type="reset" id="editCancel" class="layui-btn layui-btn-primary">重置</button>
	    </div>
	  </div>
	</form>
	
	<div id="treeDiv" style="width: 200px;height: 250px;"><ul class="ztree" id="ztreeDemo"></ul></div>
</body>

<script src="${basePath }/resources/js/libs/jquery.ztree.all.js"></script>
<script src="${basePath }/resources/js/libs/jquery.ztree.exhide.js"></script>
<script type="text/javascript">
	layui.use(['layer', 'form'], function(){
		var layer = layui.layer
		,form = layui.form;
		
		//注意：parent 是 JS 自带的全局对象，可用于操作父页面
		var index = parent.layer.getFrameIndex(window.name);//获取子窗口索引
		 //监听提交
        form.on('submit(bookSubmit)', function(data){
	        var formData = data.field;
	        var url = basePath + '/book/admin/edit';
	         // * 发起请求
	         $.ajax({
		         data: formData,
		         type: "POST",
		         dataType: "JSON",
		         url: url,
		         success: function(res) {
		        	 if(res.status){
		        		 parent.layer.close(index);
		     			 parent.location.reload();
		        	 }else{
		        		 layer.msg(res.msg);
		        	 }
		         },
		     	error: function(data) {
		     		layer.msg('error');
		     	}
	        }); // end ajax
            return false;
        });
		
		var isbnFlag; //验证ISBN是否合法的标志；false表示不合法，默认合法
		//验证ISBN是否合法（即ISBN不能重复）
		function valiISBN(isbn) {
			var editFlag = $('#editFlag').val();
			var url;
			if(editFlag === '0') { //增加
				url = basePath + '/book/admin/valiISBNWhenAdd';
			} else if(editFlag === '1'){ //编辑
				url = basePath + '/book/admin/valiISBNWhenEdit';
			}
			$.ajax({
				url: url,
				type: 'post',
				dataType: 'json',
				data: {
					'isbn': isbn
				},
				success: function(res){
					isbnFlag = res.status;
				},
				error: function(){
					layer.msg('验证ISBN出错');
				}
		   }); /* ajax end */
		}
		
		$('#isbn').on('blur', function(){
			valiISBN($(this).val());
		});
		//为了防止编辑图书时，‘onblur’方法里的valiISBN()方法没有被及时调用，页面加载时就要处理一次
		$(function(){ 
			valiISBN($('#isbn').val());
		});
		
		//自定义验证规则
		  form.verify({
		    title: function(value){
		      if(value.length < 5){
		        return '标题至少得5个字符啊';
		      }
		    }
		  	,valiIsbn: function(value){
		      	if(!isbnFlag){
			       return '该ISBN重复，请重新输入';
			    }
			}
		    ,num: [/^[0-9]{1,4}$/, '数量由1-4位的数字组成']
		    ,addCount: function(value){
		      	var editFlag = $('#editFlag').val();
		    	if(editFlag === '1' && value < $('#origalSum').val()){
			       return '图书数量只能增加，不能减少';
			    }
			} 
		    ,price1: [/^[0-9]+.?[0-9]*$/, '定价格式不正确']
		    ,price2: function(value){
		    	/* 判断定价的整数小数的是否超过上限 */
		    	var flag = true;
		    	var nums = value.split('.');
		    	if(nums.length >= 1){
		    		var num1 = nums[0];
		    		if(num1.length > 7){
			    		flag = false;
			    	}
		    	}
		    	if(nums.length >= 2){
		    		var num2 = nums[1];
			    	if(num2.length > 2){
			    		flag = false;
			    	}
		    	}
		    	if(!flag){
		    		return '定价格式不正确';
		    	}
		    }
		  });
		
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
			var curNode = null; //当前选中节点
			
			$.ajax({
				type: 'post',
				url: basePath + "/sort/getSortTree",
				data: {},
				dataType:'JSON',
				success: function(data){
					zTreeObj = $.fn.zTree.init($("#ztreeDemo"), treeSetting, data);
				},
				error: function(){
					layer.msg('error');
				}
			});/* ajax end */
		}
		//节点点击事件
		function clickSort(event, treeId, treeNode){
			zTreeObj.expandNode(treeNode, true, true, true);
			curNode = treeNode;
		}
		$(function(){
			$('#treeDiv').hide();
			initZtree();
		});
		$('#sortName').on('click', function(){
			initZtree();
			$('#treeDiv').show();
			layer.open({
				type: 1
				,content: $('#ztreeDemo')
				,btn: ['确定', '取消']
				,area: ['200px', '250px']
				,yes: function(index, layero){
					if(curNode != null){
						$('#sortName').val(curNode.name);
						$('#sortId').val(curNode.id);
					}
					$('#treeDiv').hide();
				    layer.close(index); //如果设定了yes回调，需进行手工关闭
				  }
				,btn2: function(index, layero){
					$('#treeDiv').hide();
				    layer.close(index);
				 }
				,cancel: function(index, layero){
					$('#treeDiv').hide();
				    layer.close(index); 
				 }
			}); 
		}); 
		
	});
</script>

</html>