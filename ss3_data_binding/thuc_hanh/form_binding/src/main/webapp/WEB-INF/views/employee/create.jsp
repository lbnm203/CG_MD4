<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 12/27/2025
  Time: 4:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Trang tạo</title>
</head>
<body>
<h1>Nhập thông tin chi tiết Nhân viên</h1>
<form action="add-employee" method="post" modelAttribute="employee"></form>
</body>
</html>
