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
		<%@ include file="/common/personal-side.jsp" %>
		
		<!-- 在头部和顶部定位当前页面 -->
		<script type="text/javascript">
			$('#myHead').addClass('layui-this');
			$('#infoSide').addClass('layui-this');
		</script>

		<div class="layui-body">
			<!-- 内容主体区域 start-->
			<div style="padding: 15px;">
				<div class="row">
					<div class="col-md-7">
                        <div class="card-header">
                            <strong>个人信息</strong>
                        </div>
                        <div class="card-content">
                            <div class="card-block">
                                <div class="form-horizontal container-fluid ">
                                    <div class="row">
                                        <div class="form-group col-md-4">
                                            <img width="150px" height="190px" border="0" src="${basePath }/resources/images/header_user.jpg">
                                        </div>
                                        <div class="col-md-8">
                                            <div>
                                            	<div class="form-group row">
                                                    <label  class="col-md-3 form-control-label text-right">用户名：</label>
                                                    <div class="col-md-8">
                                                        <div class="input-group file_box">
                                                            <span>${userVo.userName }</span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label  class="col-md-3 form-control-label text-right">真实姓名：</label>
                                                    <div class="col-md-8">
                                                        <div class="input-group file_box">
                                                            <span>${userVo.name }</span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label  class="col-md-3 form-control-label text-right">身份证号：</label>
                                                    <div class="col-md-8">
                                                        <div class="input-group file_box">
                                                           <span>${userVo.cid }</span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label  class="col-md-3 form-control-label text-right">email：</label>
                                                    <div class="col-md-8">
                                                        <div class="input-group file_box">
                                                           <span>${userVo.email }</span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group row">
                                                    <label  class="col-md-3 form-control-label text-right">tel：</label>
                                                    <div class="col-md-8">
                                                        <div class="input-group file_box">
                                                            <span>${userVo.tel }</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="text-center btn-my-info">
                                        <!-- <button class="btn btn-primary">修改密码</button> -->
                                        <button class="btn btn-primary" id="editInfoBtn" data-toggle="modal" data-target="#editUserInfoModel">修改信息</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-3">
                        <div class="card-header">
                            <strong>借阅信息</strong>
                        </div>
                        <div class="card-content">
                            <div class="card-block">
                                <div class="form-horizontal container-fluid ">
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div>
                                                <div class="form-group row">
                                                	<span class="btn btn-app btn-danger btn-sm">
                                                		<span class="bigger-170">${userVo.leftBorrowCount }</span>
                                                		<br />
                                                		<span>最多可借</span>
                                                	</span>
                                                </div>
                                                <div class="form-group row">
                                                	<span class="btn btn-app btn-success btn-sm">
                                                		<span class="bigger-170">${userVo.leftOrderCount }</span>
                                                		<br />
                                                		<span>最多可预约</span>
                                                	</span>
                                                </div>
                                                <div class="form-group row">
                                                	<span class="btn btn-app btn-primary btn-sm">
                                                		<span class="bigger-170">${punishCount }</span>
                                                		<br />
                                                		<span>超期图书</span>
                                                	</span>
                                                </div>
                                                <div class="form-group row">
                                                	<span class="btn btn-app btn-warning btn-sm">
                                                		<span class="bigger-170">0</span>
                                                		<br />
                                                		<span>预约到书</span>
                                                	</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
				</div>
			</div>
			<!-- 内容主体区域 end-->
			
			<div class="form-group col-md-4">
                 <div id="preview" class="" style="border: none">
                     <img id="imghead" width="100%" height="100%" style="border-radius: 100%;border:1px solid #EEE;" border="0" src="${basePath}/file/image/${user.headPic}">
                 </div>
                 <button class="btn-upload-img" id="editHeadPic">更改头像</button>

                 <script type="text/javascript">
                     $("#editHeadPic").on('click' , function(){
                        $("#headPicModal").modal("show");
                     });
                 </script>
             </div>

			<div class="layui-footer">
				<!-- 底部固定区域 -->
				© layui.com - 底部固定区域
			</div>
		</div>
	
	</div>
	
	<!-- start 详情model -->
<div class="up-modal up-fade" id="headPicModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2"
     aria-hidden="true" style="display:none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h6 class="modal-title" id="myModalLabel">更换头像</h6>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body">
					<div class="card-block">
						<div class="avator-mode">
							<img id="imgHead" width="100%" height="100%" style="border-radius: 100%; border: 1px solid #EEE;" border="0" src="${basePath}/file/image/${user.headPic}">
						</div>
						<div class="user-header-img">
							  <form id="fileUploadForm" method="post" enctype="multipart/form-data">
                                   <input type="file" name="upload_file" id="up"  />
                              </form>
						</div>
						<div class="w140">
							<button class="btn-upload-img" onclick="document.getElementById('up').click();">更改头像</button>
						</div>
						<script type="text/javascript">
							
							$("#up").change(function (){
								 $("#imgHead").attr("src", URL.createObjectURL($(this)[0].files[0]));
                               	
                                 $("#img-upload").click(function () {
                                     $.ajax({
                                         url:"${basePath}/file/upload",
                                         data:new FormData($('#fileUploadForm')[0]),
                                         contentType: false,
                                         processData: false,
                                         type:"POST",
                                         dataType:"JSON",
                                         success:function(result){
                                             $("#imgHead").attr("src","${basePath}/file/image/"+result);
                                             
                                             /* 将新头像保存到数据库 */
                                             $.ajax({
                                            	url: '${basePath}/userInfo/changeHeadPic',
                                            	type: 'post',
                                            	dataType: 'json',
                                            	data: {
                                            		'objectId': result
                                            	},
                                            	success: function(data){
                                            		if(data.status){
                                            			window.location.reload();
                                            		}
                                            	},
                                            	error: function(){
                                            		alert('error');
                                            	}
                                             });
                                         },
                                         error:function(){
                                             alert("错误");
                                         }
                                     });
                                 });
							});
						
						</script>
					</div>
				</div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-default" id="img-upload">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<!-- end 详情model-->
</body>
<script type="text/javascript">
	/* 加载layui下拉框 */
	layui.use(['element', 'form', 'layer'], function(){
		var element = layui.element
		,form = layui.form
		,layer = layui.layer;
		
		$(function(){
			$('#editInfoBtn').on('click', function(){
				editUserInfo();
			});
		});
		
		function editUserInfo(){
			layer.open({
				type: 2,
				content: basePath + '/user/toEditMyInfo',
				title: '修改用户信息',
				area: ['520px', '680px'],
				success: function(index, layero){
				}
			})
		}
	});
</script>
</html>