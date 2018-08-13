<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/common/common.jsp" %>
</head>
<body>
<div style="margin-left: 38%;margin-top: 30px;">
	<h4>新用户注册</h4>
	<br>
   <form class="layui-form" action="${basePath }/register">
   	  <div class="layui-form-item">
	    <label class="layui-form-label">用户名</label>
	      <div class="layui-input-inline">
	        <input type="tel" name="userName" lay-verify="userName|valiUserName" autocomplete="off" class="layui-input">
	      </div>
	      <div class="layui-form-mid layui-word-aux">用户名由1-10位的数字字母组成</div>
	  </div>
   
   	  <div class="layui-form-item">
	    <div class="layui-inline">
	        <label class="layui-form-label">密码</label>
		    <div class="layui-input-inline">
		      <input type="password" name="password" lay-verify="pass" placeholder="请输入密码" autocomplete="off" class="layui-input">
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
	        <input type="tel" name="name" lay-verify="name" autocomplete="off" class="layui-input">
	      </div>
	      <div class="layui-form-mid layui-word-aux">姓名由1-5个中文组成</div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">性别</label>
	    <div class="layui-input-block">
	      <input type="radio" name="gender" value="1" title="男" checked="">
	      <input type="radio" name="gender" value="0" title="女">
	    </div>
	  </div>
	  <div class="layui-form-item">
	  	<div class="layui-inline">
	       <label class="layui-form-label">身份证</label>
		    <div class="layui-input-inline">
		      <input type="text" name="cid" lay-verify="identity" placeholder="" autocomplete="off" class="layui-input">
		    </div>
	    </div>
	  </div>
   
	  <div class="layui-form-item">
	    <div class="layui-inline">
	      <label class="layui-form-label">手机</label>
	      <div class="layui-input-inline">
	        <input type="tel" name="tel" lay-verify="required|phone" autocomplete="off" class="layui-input">
	      </div>
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <div class="layui-inline">
	      <label class="layui-form-label">邮箱</label>
	      <div class="layui-input-inline">
	        <input type="text" name="email" lay-verify="email" autocomplete="off" class="layui-input">
	      </div>
	    </div>
	  </div>
	  
	  <div class="layui-form-item" >
	    <div class="layui-input-block">
	      <button class="layui-btn" lay-submit="" lay-filter="register">提交</button>
<!-- 	      <button class="layui-btn" lay-submit="" lay-filter="register">立即提交</button> -->
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
	  
	  //监听提交
	 /* form.on('submit(register)', function(data){
		 var formData = data.field;
	    $.ajax({
    		url: basePath + '/register',
    		type: 'post',
    		data: formData,
    		dataType: 'json',
    		success: function(res){
    			if(res.status){
    				window.location.href = basePath;
    			}else{
    				layer.msg(res.msg)
    			}
    		},
    		error: function(){
    			layer.msg('error');
    		}
    	});
	    return false;
	  });  */
	  
	});
</script>
</html>