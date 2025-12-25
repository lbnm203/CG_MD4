<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <!DOCTYPE html>
  <html lang="vi">

  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chuyển đổi USD sang VNĐ</title>
  </head>

  <body>
      <h1>Chuyển đổi tiền tệ</h1>
      <p>Chuyển đổi từ USD sang VNĐ</p>
    <form action="${pageContext.request.contextPath}/result" method="post">
      <fieldset>
        <legend>Form chuyển đổi tiền tệ</legend>
        <label for="rate">Tỉ giá (VND/USD)</label>
        <input type="number" id="rate" name="rate" class="rate" step="0.01" min="0" placeholder="Nhập tỉ giá"
          required><br><br>
        <label for="usd">Số USD</label>
        <input type="number" id="usd" name="usd" class="usd" step="0.01" min="0" placeholder="Nhập số USD"
          required><br><br>
        <button type="submit" class="btn btn-convert">Chuyển đổi ngay</button><br><br>
      </fieldset>
    </form>
    </div>
  </body>

  </html>