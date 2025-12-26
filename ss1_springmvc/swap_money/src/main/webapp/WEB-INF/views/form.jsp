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
      <c:if test="${not empty errorMessage}">
        <div style="color: red; font-weight: bold; margin-bottom: 15px;">
          ${errorMessage}
        </div>
      </c:if>

      <form action="/result" method="post">
        <fieldset>
          <legend>Form chuyển đổi tiền tệ</legend>
          <label for="rate">Tỉ giá (VND/USD)</label>
          <input type="text" id="rate" name="rate" class="rate" value="26500" placeholder="Nhập tỉ giá"
            readonly><br><br>
          <label for="usd">Số USD</label>
          <input type="text" id="usd" name="usd" class="usd" placeholder="Nhập số USD" required><br><br>
          <button type="submit" class="btn btn-convert">Chuyển đổi ngay</button><br><br>
        </fieldset>
      </form>
    </body>

    </html>