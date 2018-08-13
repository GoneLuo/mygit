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
<script type="text/html" id="opreBar">
<button class="btn btn-danger" lay-event="destroy">注销</button>&nbsp;&nbsp;
<button class="btn btn-primary" lay-event="recovery">恢复</button>
</script>

<body class="layui-layout-body">
	<div class="layui-layout layui-layout-admin">
		<%@ include file="/common/head.jsp" %>
		<%@ include file="/common/admin-side.jsp" %>
		
		<!-- 在头部和顶部定位当前页面 -->
		<script type="text/javascript">
			$('#adminHead').addClass('layui-this');
			$('#userSide').addClass('layui-this');
		</script>

		<div class="layui-body">
			<!-- 内容主体区域 -->
			<div style="padding: 15px;">
				<div class="col-md-12">
				 	<div class="card">
		                <div id="forSearch" style="margin: 20px 10px;">
		                    <ul class="form-inline">
		                        <li class="form-group">
		                        	<label for="goodsId">用户名</label> &nbsp;&nbsp;
		                           	<input type="text" id="userName" class="check-input form-control" placeholder="输入用户名" />
		                        </li>
		                        <li class="form-group">
		                        	<label for="goodsId">真实姓名</label> &nbsp;&nbsp;
		                           	<input type="text" id="realName" class="check-input form-control" placeholder="输入真实姓名" />
		                        </li>
		                        <li class="form-group">
		                            <label for="goodsId">用户身份</label> &nbsp;&nbsp;
		                            <select type="text" id="roleType" class="form-control" name="roleType" >
		                                <option value="">请选择</option>
		                                <option value="0">管理员</option>
		                                <option value="1">普通用户</option>
		                            </select>
		                        </li> &nbsp;&nbsp;
		                        <li class="form-group">
		                            <label for="goodsId">账号状态</label> &nbsp;&nbsp;
		                            <select type="text" id="statusType" class="form-control" name="statusType" >
		                                <option value="">请选择</option>
		                                <option value="1">账号有效</option>
		                                <option value="0">账号无效</option>
		                            </select>
		                        </li> &nbsp;&nbsp;
		                        <li class="form-group-btn">
		                            <button class="btn btn-warning" id="resetCondition">重置</button>
		                            <button class="btn btn-primary" id="searchByCondition">搜索</button>
		                        </li>
		                    </ul>
						</div>
		            </div>
				</div>
		
		        <div class="container-fluid">
		            <div class="animated fadeIn">
		                <div class="row">
		                    <div class="col-md-12">
		                        <div class="card-content">
										<div class="card-header">
											<button class="btn btn-danger" id="destorySelectUser">注销选中用户</button> 
											<button class="btn btn-primary" id="coverySelectUser">恢复选中用户</button>
			                            </div>
										<div class="card-content-main">
											<table class="layui-hide" id="userSet" lay-filter="userSetFilter"></table>
										</div>
		                        </div>
		                    </div>
		                </div>
		            </div>
		
		        </div>
				
			</div>


			<div class="layui-footer">
				<!-- 底部固定区域 -->
				© layui.com - 底部固定区域
			</div>
		</div>
	
	</div>
</body>

<script src="${basePath}/resources/js/modules/admin/userManager.js?ver=1"></script>
</html>