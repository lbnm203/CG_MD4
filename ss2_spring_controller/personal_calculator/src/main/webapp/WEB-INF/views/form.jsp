<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <html>

        <head>
            <title>Tính toán</title>
        </head>

        <body>
            <h1>Máy tính</h1>
            <form action="/result" method="post">
                <fielset>
                    <legend>Máy tính cá nhân</legend>
                    <input type="text" name="aValue" id="aValue">
                    <input type="text" name="bValue" id="bValue">
                    <br><br>
                    <button type="submit" name="operation" value="add" id="add">Addition (+)</button>
                    <button type="submit" name="operation" value="sub" id="sub">Subtraction (-)</button>
                    <button type="submit" name="operation" value="mul" id="mul">Multiplication (*)</button>
                    <button type="submit" name="operation" value="div" id="div">Division (/)</button>
                    <br><br>
                    <c:if test="${not empty errorMessage}">
                        <h3 style="color: red">${errorMessage}</h3>
                    </c:if>
                    <c:if test="${not empty result}">
                        <h3 style="color: green">Result: ${result}</h3>
                    </c:if>
                </fielset>
            </form>
        </body>

        </html>