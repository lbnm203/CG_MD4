<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 12/26/2025
  Time: 1:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<h1>Email Validate</h1>
<h3>${message}</h3>
<form action="/validata" method="post">
    <legend>Kiểm tra email hợp lệ</legend>
    <input type="text" name="email" placeholder="Nhập email của bạn"><br><br>
    <button type="submit">Kiểm tra</button>
</form>
</body>
</html>
