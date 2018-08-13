<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/2/6
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/common/common.jsp" %>
</head>
<body>

<form name="form" id="form" action="${basePath }/file/upload" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file" id="upload_file">
    <input type="text" name="imageName" id="imageName">
   <!--  <input type="submit" value="提交"> -->
</form>

<input type="button" value="提交" id="upload">

<img id="img" src="${basePath }/file/image/5ae128653fa9d42ca8999ff5" style="width: 100px;height: 100px;">
<script>
    $("#upload").click(function () {
        $.ajax({
            url: basePath + "/file/upload",
            data:new FormData($('#form')[0]),
            contentType: false,
            processData: false,
            type:"POST",
            dataType:"JSON",
            success:function(result){
            	console.log(result);
                $("#img").attr("src", basePath + "/file/image/"+result);
            },
            error:function(){
                alert("错误");
            }
        });
    });

    $("#upload_file").change(function () {
        $("#img").attr("src", URL.createObjectURL($(this)[0].files[0]));
    });
</script>


</body>
</html>
