<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/common/common.jsp" %>
</head>
<body class="login">
<div class="login-body">
    <div class="login-logo">
        <span>图书管理系统<span class="login-logo-text">LIBRARY</span></span>
    </div>
    <div class="login-from">
    <form action="${basePath }/login" method="post">
        <div class="login-padding">
            <div>
                <h2>用户名:</h2>
                <label>
                    <input type="text" id="userName" name="userName" class="txt-input" autocomplete="off">
                </label>
                <h2>密码:</h2>
                <label>
                    <input type="password" id="password" name="password" class="txt-input" autocomplete="off">
                </label>
            </div>
            <div class="operate-box">
                <!--<label class="remember-me">
                    <span class="remember-icon"><span class="remember-box"></span></span>
                    <input type="checkbox" name="remember-me" id="remember-me" checked>
                    <span>记住密码</span>
                </label>-->
                <span class="forgot-pwd" style="float: left;"><a href="javascript:void(0);">忘记密码?</a></span>
                <span class="forgot-pwd"><a href="${basePath }/register/toRegister">用户注册</a></span>
            </div>
            <div class="login-button"><a href="manageUser.html">
                <input type="submit" class="sub-button" name="button" id="button" value="登 录">
            </div>
        </div>
    </form>
    </div><!--login_boder end-->
</div><!--login_m end-->

<script>
    //login_bg
    var winHeight = $(window).height();
    $(".login").css("height", winHeight);
    $(window).resize(function () {
        winHeight = $(window).height();
        $(".login").css("height", winHeight);
    });
</script>
</body>
</html>