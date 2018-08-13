<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/3/6
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <title>jqPaginator</title>
    <link type="text/css" rel="stylesheet" href="http://cdn.staticfile.org/twitter-bootstrap/3.1.1/css/bootstrap.min.css"/>
</head>
<body style="padding:10px;">
<p id="p1">1</p>
<ul class="pagination" id="pagination1"></ul>
<button id="getData">获取数据</button>
<%--<p id="p2"></p>--%>
<%--<ul class="pagination" id="pagination2"></ul>--%>

<script type="text/javascript" src="resources/js/jquery.min.js"></script>
<script type="text/javascript" src="resources/js/jqPaginator.js"></script>
<script type="text/javascript">
    $.jqPaginator('#pagination1', {
        totalPages: 0,
        visiblePages: 0,
        currentPage: 1,
        onPageChange: function (num, type) {
            $.ajax({
                url: "${basePath}/category/1/2323",
                data: {
                    'pageNum':num
                },
                type: "POST",
                dataType: 'json',
                success: function (result) {
                    console.info(result);
                },
                error: function () {
                    alert('分类下帖子查询失败');
                }
            });
        }
    });
    $.jqPaginator('#pagination2', {
        totalPages: 100,
        visiblePages: 10,
        currentPage: 3,
        prev: '<li class="prev"><a href="javascript:;">Previous</a></li>',
        next: '<li class="next"><a href="javascript:;">Next</a></li>',
        page: '<li class="page"><a href="javascript:;">{{page}}</a></li>',
        onPageChange: function (num, type) {
            $('#p2').text(type + '：' + num);
        }
    });
</script>

</body>
</html>
