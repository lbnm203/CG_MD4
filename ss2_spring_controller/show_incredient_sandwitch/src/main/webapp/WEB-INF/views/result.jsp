<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <html>

        <head>
            <title>Title</title>
        </head>

        <body>
            <h1>Ingredient</h1>
            <c:if test="${empty condiments}">
                <p>Không có nguyên liệu thêm vào</p>
            </c:if>
            <ul>
                <c:forEach items="${condiments}" var="condiment">
                    <li>${condiment}</li>
                </c:forEach>
            </ul>

            <form action="/" method="get">
                <button type="submit">Trở về</button>
            </form>
        </body>

        </html>