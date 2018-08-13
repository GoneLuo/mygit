<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/common/common.jsp" %>
</head>
<body>
<div style="margin-top: 30px;">
   <form class="layui-form" action="${basePath }/user/editInfo">
   	  <input type="hidden" name="userInfoId" value="${user.userInfoId }">
   	  
   	  <input type="hidden" name="userId" value="${user.userId }">
   	  <input type="hidden" name="useful" value="${user.useful }">
   	  <input type="hidden" name="leftBorrowCount" value="${user.leftBorrowCount }">
   	  <input type="hidden" name="leftOrderCount" value="${user.leftOrderCount }">
   	  <input type="hidden" name="role" value="${user.role }">
   
   	  <div class="layui-form-item">
	    <label class="layui-form-label">用户名</label>
	      <div class="layui-input-inline">
	        <input type="tel" name="userName" readonly="readonly" value="${user.userName }" lay-verify="" autocomplete="off" class="layui-input">
	      </div>
	      <div class="layui-form-mid layui-word-aux">用户名由1-10位的数字字母组成</div>
	  </div>
   		
   	  <div class="layui-form-item">
	    <div class="layui-inline">
	        <label class="layui-form-label">原密码</label>
		    <div class="layui-input-inline">
		      <input type="password" name="orginalPassword" value="${user.password }" readonly="readonly" placeholder="请输入密码" autocomplete="off" class="layui-input">
		    </div>
		    <div class="layui-form-mid layui-word-aux">密码由6-20位的数字字母组成</div>
	    </div>
	  </div>
   
   	  <div class="layui-form-item">
	    <div class="layui-inline">
	        <label class="layui-form-label">新密码</label>
		    <div class="layui-input-inline">
		      <input type="password" name="password" value="${user.password }" lay-verify="pass" placeholder="请输入密码" autocomplete="off" class="layui-input">
		    </div>
		    <div class="layui-form-mid layui-word-aux">密码由6-20位的数字字母组成</div>
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <div class="layui-inline">
	      	<label class="layui-form-label">再次输入密码</label>
		    <div class="layui-input-inline">
		      <input type="password" name="rePassword" lay-verify="rePass" placeholder="请输入密码" autocomplete="off" class="layui-input">
		    </div>
		    <div class="layui-form-mid layui-word-aux">密码由6-20位的数字字母组成</div>
	    </div>
	  </div>
   
   	  <div class="layui-form-item">
	    <label class="layui-form-label">真实姓名</label>
	      <div class="layui-input-inline">
	        <input type="tel" name="name" value="${user.name }" lay-verify="name" autocomplete="off" class="layui-input">
	      </div>
	      <div class="layui-form-mid layui-word-aux">姓名由1-5个中文组成</div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">性别</label>
	    <div class="layui-input-block">
		    <c:if test="${user.gender == '1' }">
		    	<input type="radio" name="gender" value="1" title="男" checked="">
	      		<input type="radio" name="gender" value="0" title="女">
		    </c:if>
		    <c:if test="${user.gender == '0' }">
		    	<input type="radio" name="gender" value="1" title="男">
	      		<input type="radio" name="gender" value="0" title="女" checked="">
		    </c:if>
	    </div>
	  </div>
	  <div class="layui-form-item">
	  	<div class="layui-inline">
	       <label class="layui-form-label">身份证</label>
		    <div class="layui-input-inline">
		      <input type="text" name="cid" value="${user.cid }" lay-verify="identity" placeholder="" autocomplete="off" class="layui-input">
		    </div>
	    </div>
	  </div>
   
	  <div class="layui-form-item">
	    <div class="layui-inline">
	      <label class="layui-form-label">手机</label>
	      <div class="layui-input-inline">
	        <input type="tel" name="tel" value="${user.tel }" lay-verify="required|phone" autocomplete="off" class="layui-input">
	      </div>
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <div class="layui-inline">
	      <label class="layui-form-label">邮箱</label>
	      <div class="layui-input-inline">
	        <input type="text" name="email" value="${user.email }" lay-verify="email" autocomplete="off" class="layui-input">
	      </div>
	    </div>
	  </div>
	  
	  <div class="layui-form-item" style="margin-left: 20%;">
	    <div class="layui-input-block">
	      <button class="layui-btn" lay-submit="" lay-filter="register">提交</button>
	      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
	    </div>
	  </div>
	</form>
</div>

</body>

<script>
	layui.use(['form', 'layedit', 'laydate'], function(){
	  var form = layui.form
	  ,layer = layui.layer;
	  
		//注意：parent 是 JS 自带的全局对象，可用于操作父页面
		var index = parent.layer.getFrameIndex(window.name);//获取子窗口索引
		form.on('submit', function(){
			parent.layer.close(index);
			parent.location.reload();
		});
	  
	  var userNameFlag = false; /* 用户名是否可用的标志*/
	  $(function(){
		  $('input[name="userName"]').on('blur', function(){
			  if($(this).val() !== ''){
				  valiFunc($(this).val()); 
			  }
		  }); 
	  });
	  
	  /* 验证用户名是否可用，即用户名未被占用 */
	  function valiFunc(userName){
		  $.ajax({
    		url: basePath + '/register/valiUserName',
    		type: 'post',
    		data: {
    			'userName': userName
    		},
    		dataType: 'json',
    		success: function(res){
    			userNameFlag = res.status;
    		},
    		error: function(){
    			layer.msg('error');
    		}
    	});/* ajax end */
	  }
	  
	  //自定义验证规则
	  form.verify({
	    title: function(value){
	      if(value.length < 5){
	        return '标题至少得5个字符啊';
	      }
	    }
	    ,pass: [/^[a-zA-Z0-9_]{6,20}$/, '密码由6-20位的数字字母组成']
	    ,rePass: function(value){
	    	var passInput = $('input[name="password"]');
	    	if(value !== passInput.val()){
	    		return '两次密码输入不一致';
	    	}
	    }
	    ,userName: [/^[a-zA-Z0-9_]{1,10}$/, '用户名由1-10位的数字字母组成']
	    ,valiUserName: function(value){
	    	if(!userNameFlag){
    	  		return '该用户名已被占用';
        	} 
		 }
	    ,name: [/^[\u4e00-\u9fa5]{1,5}$/, '姓名由1-5个中文组成']
	  });
	  
	});
</script>
</html>