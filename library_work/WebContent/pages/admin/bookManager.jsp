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
			$('#bookSide').addClass('layui-this');
		</script>

		<div class="layui-body">
			<!-- 内容主体区域 start-->
			<div style="padding: 15px;">
				<div class="col-md-12">
				 	<div class="card">
		                <div id="forSearch" style="margin: 20px 10px;">
			                <form>
			                	<ul class="form-inline">
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
		                          <li class="form-group-btn">
		                              <input type="reset" class="btn btn-warning" id="resetCondition" value="重置">
		                              <input type="button" class="btn btn-primary" id="searchByCondition" value="搜索">
		                          </li>
		                      </ul>
			                </form>
						</div>
		            </div>
				</div>
				
				<script type="text/javascript">
					$(function(){
						var select = $('#conditionType');
						select.change(function(){
							var condiName = select.val();
							$('#conditionContent').attr('name', condiName);
						});
					});
				</script>
		
		        <div class="container-fluid">
		            <div class="animated fadeIn">
		                <div class="row">
		                    <div class="col-md-12">
		                        <div class="card-content">
									<div class="card-header">
		                                <span><i class="fa fa-code"></i> 图书管理</span>
		                                <span class="float-right">
		                                	<button type="button" class="btn btn-primary" id="addBookSet">添加图书
		                                	</button>
		                                </span>
		                            </div>
									<div class="card-content-main">
										<table class="table table-hover" id="bookTable" lay-filter="bookTableFilter">
										</table>
									</div>
		                        </div>
		                    </div>
		                </div>
		            </div>

        		</div>
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
<button class="btn btn-primary" lay-event="editBook">编辑</button>&nbsp;&nbsp;
<button class="btn btn-danger" lay-event="delBook">删除</button>
</script>
<script src="${basePath }/resources/js/modules/admin/bookManager.js?ver=1"></script>
</html>