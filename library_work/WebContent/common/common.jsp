<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="basePath" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<meta name="description" content="">
<meta name="keywords" content="">
<title>图书管理系统</title>

<!--start平台样式css-->
	<link rel="shortcut icon" href="${basePath }/resources/images/favicon_logo.png">
	<link href="${basePath }/resources/css/style.min.css" rel="stylesheet">
	<link href="${basePath }/resources/css/style.css" rel="stylesheet">
	<link href="${basePath }/resources/layui/css/layui.css" rel="stylesheet">
	
<!--end平台样式css-->

<!--start平台样式js-->
	<script src="${basePath }/resources/js/libs/jquery-2.1.4.min.js"></script>
	<script src="${basePath }/resources/js/libs/bootstrap.min.js"></script>
	<script src="${basePath }/resources/layui/layui.js"></script>

<!--end平台样式js-->

<script type="text/javascript">
var basePath='${basePath}';
var baseurl='${basePath}${path }';
</script>