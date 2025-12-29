<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 12/29/2025
  Time: 10:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Login Details</title>
</head>
<body>
<h1>Welcome User ${user.name}</h1>
<h3>Account: ${user.account}</h3>
<h3>Name:${user.name}</h3>
<h3>Email: ${user.email}</h3>
<h3>Age: ${user.age}</h3>
</body>
</html>
