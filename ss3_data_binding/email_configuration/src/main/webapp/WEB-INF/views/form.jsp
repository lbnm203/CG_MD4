<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Setting</title>
</head>
<body>
<h1>Settings</h1>
<form:form action="/update" method="post" modelAttribute="email">
    <table>
        <tr>
            <td><form:label path="language">Languages </form:label></td>
            <td>
                <form:select path="language">
                    <form:option value="English">English</form:option>
                    <form:option value="Vietnamese">Vietnamese</form:option>
                    <form:option value="Japanese">Japanese</form:option>
                    <form:option value="Chinese">Chinese</form:option>
                </form:select>
            </td>
        </tr>
        <tr>
            <td><form:label path="pageSize">Page Size </form:label></td>
            <td>Show
                <form:select path="pageSize">
                    <form:option value="5">5</form:option>
                    <form:option value="10">10</form:option>
                    <form:option value="15">15</form:option>
                    <form:option value="25">25</form:option>
                    <form:option value="50">50</form:option>
                    <form:option value="100">100</form:option>
                </form:select>

                emails per page
            </td>
        </tr>
        <tr>
            <td><form:label path="spamFilter">Spams Filter</form:label></td>
            <td><form:checkbox path="spamFilter"/> enable spam filter</td>
        </tr>
        <tr>
            <td><form:label path="signature">Signature </form:label></td>
            <td><form:textarea path="signature" rows="4" cols="30" /></td>
        </tr>
        <tr>
            <td><button type="submit">Update</button></td>
            <td><button type="button" onclick="window.location.href='/'">Cancel</button></td>
        </tr>
    </table>
</form:form>
<p style="color: green">${message}</p>
</body>
</html>
