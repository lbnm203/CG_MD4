<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Update page Success</title>
</head>
<body>
<h1>Update page Success</h1>
<table>
    <tr>
        <td><h3>Language: </h3></td>
        <td><h3> ${email.language}</h3></td>
    </tr>
    <tr>
        <td><h3>Page Size: </h3></td>
        <td><h3> ${email.pageSize}</h3></td>
    </tr>
    <tr>
        <td><h3>Spams Filter: </h3></td>
        <td>
            <c:if test="${email.spamFilter == true}">
                <h3> Enable</h3>
            </c:if>
            <c:if test="${email.spamFilter == false}">
                <h3> Disable</h3>
            </c:if>
        </td>
    </tr>
    <tr>
        <td><h3>Signature: </h3></td>
        <td><h3> ${email.signature}</h3></td>
    </tr>
    <tr>
        <td>
            <a href="${pageContext.request.contextPath}/">
                <button type="button">Back to Update</button>
            </a>
        </td>
    </tr>
</table>
</body>
</html>
