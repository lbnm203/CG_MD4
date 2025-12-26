
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ingredient</title>
</head>
<body>
<h1>Sandwich Condiments</h1>
<form action="/ingredient" method="post">
    <label><input type="checkbox" name="condiment" value="Lettuce"> Lettuce</label><br>
    <label><input type="checkbox" name="condiment" value="Tomato"> Tomato</label><br>
    <label><input type="checkbox" name="condiment" value="Mustard"> Mustard</label><br>
    <label><input type="checkbox" name="condiment" value="Sprouts"> Sprouts</label><br>
    <hr>
    <button type="submit">Save</button>
</form>
</body>
</html>
