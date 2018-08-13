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
				</div>
				<div class="card layui-col-md9">
					<!-- <div class="card-header">
                         <span class="float-left">热门图书</span>
                     </div> -->
                     <div class="card-block">
                         <div class="theme-con">
                             <ul id="bookList"></ul>
                             <span id="msgSpan"></span>
                         </div>
                     </div>
                 
                   <!-- 分页 -->
				   <div class="up-clearfix">
				       <div class="up-pull-right" style="line-height: 3px;">
				     		<ul class="pagination" id="pagination1"></ul>
				       </div>
			       </div>			
		       </div>
			<!-- 内容主体区域 end-->
		</div>

			<!-- foot -->
		    <div><br><hr><br></div>
		</div>
	
	</div>
</body>

<script src="${basePath }/resources/js/libs/jquery.ztree.all.js"></script>
<script type="text/javascript" src="${basePath}/resources/js/libs/jqPaginator.js"></script>
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
				
				//默认选中根节点
				var root = zTreeObj.getNodesByParam('id', '0', null)[0];
				zTreeObj.selectNode(root);
			//	root.onClick;
				var sortId = '0';
				$.ajax({
		            url: basePath + "/book/getListBySort",
		            data: {
		                'sortId': sortId
		            },
		            type: "POST",
		            dataType: 'json',
		            success: function(result){
		            	console.log(result);
		                showJqPaginator(sortId, result);
		            },
		            error: function(){
		                alert('error');
		            }
		        });
			},
			error: function(){
				layer.msg('error');
			}
		});/* ajax end */
	}
	
	//节点点击事件
	function clickSort(event, treeId, treeNode){
		zTreeObj.expandNode(treeNode, true, false, true);
		console.log(treeNode);
		
		var sortId = treeNode.id;
		$.ajax({
            url: basePath + "/book/getListBySort",
            data: {
                'sortId': sortId
            },
            type: "POST",
            dataType: 'json',
            success: function(result){
            	console.log(result);
                showJqPaginator(sortId, result);
            },
            error: function(){
                alert('error');
            }
        });
	}
	
	//选中分类时，替换原先的分页和数据
    function showJqPaginator (sortId, result){
    	/*首先去除原先分页*/
    	 var $div = $('.up-clearfix');
         $div.children('.up-pull-right').remove();
         $div.children('#pagination1').remove();
         /*添加分页组件*/
         var $pagination = $("<ul class='pagination' id='pagination1'></ul>");
         var $div_2 = $("<div class='up-pull-right' style='line-height: 3px;'> </div>");
         $div_2.append($pagination);
         $div.append($div_2);
         
         if(result.data.pageCount == 0){
        	 $('#bookList').html('');
             $('#msgSpan').html("当前分类暂无图书");
             return ;
         }
         
    	 $.jqPaginator('#pagination1', {
             totalPages: result.data.pageCount,
             visiblePages: result.data.pageSize,
             currentPage: result.data.pageNum,
             onPageChange: function (num, type) {
                 $.ajax({
                     url: basePath + "/book/getListBySort?sortId=" + sortId,
                     data: {
                         'pageNum': num
                     },
                     type: "POST",
                     dataType: 'json',
                     success: function(res){
                         showBooks(res.data.dataList);
                     },
                     error: function(){
                         alert('error');
                     }
                 });
             }
         });
    };
    
    /* 手动填充图书列表 */
    function showBooks(dataList){
    	console.log(dataList);
    	
    	$('#bookList').html('');
    	var length = dataList.length;
    	for(var i = 0; i < length; i++){
    		var bookSetVo = dataList[i];
    		
    		var $bookLi = $($("#bookTpl").html());
        	var bookhref = basePath + '/book/bookDetail?bookSetId=' + bookSetVo['id']
        //	, bookCover = basePath + '/file/image/' + bookSetVo['coverUrl']
        	, bookCover = basePath + '/file/image/5ae128653fa9d42ca8999ff5'
        	, bookName = bookSetVo['bookName']
        	, author = bookSetVo['author']
        	, sortName = bookSetVo['sortName']
        	, press = bookSetVo['press']
        	, borrowedCount = bookSetVo['borrowedCount'];
        	
        	var $a1 = $bookLi.find('a.cover');
        	$a1.attr('href', bookhref);
        	$a1.find('img').attr('src', bookCover);
        	
        	var $a2 = $bookLi.find('a.title-name');
        	$a2.attr('href', bookhref);
        	$a2.html(bookName);
        	
        	var $msgDiv = $bookLi.find('div.auth-msg');
        	$msgDiv.find('span.author').html(author);
        	$msgDiv.find('span.sortName').html(sortName);
        	$msgDiv.find('span.press').html(press);
        	$msgDiv.find('span.borrowedCount').html(borrowedCount);
        	
        	$('#bookList').append($bookLi);
    	}
    }
	
	$(document).ready(function(sortId){
		initZtree();
	});
});
</script>

<script type="text/html" id="bookTpl">
<li class="theme-list clearfix">
	<div class="theme-list-img">
		<a href="#" class="headportrait cover" target="_blank" rel="noopener noreferrer">
			<img src="${basePath }/resources/images/forum_img4.jpg" style="display: block;">
		</a>
	</div>
	<div class="theme-list-con">
		<div class="title">
			<a href="${basePath }/book/bookDetail?bookSetId=${bookSet.id }" class="title-name" target="_blank">${bookSet.bookName }</a>
		</div>
		<div class="auth-msg clearfix">
			<span class="author">${bookSet.author }</span>
			<span class="time txt sortName"> <i>·</i> ${bookSet.sortName }</span>
			<span class="time txt press"> <i>·</i> ${bookSet.press }</span>
			<p class="forum-see float-right">
				<i class="layui-icon">&#xe756;</i><span class="borrowedCount">${bookSet.borrowedCount }</span>
			</p>
		</div>
	</div>
</li>
</script>
</html>