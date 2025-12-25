<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html lang="vi">

    <head>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <title>Từ Điển Anh - Việt</title>
      <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    </head>

    <body>
      <form action="" method="post">
        <fieldset>
          <legend>
            <h1>Từ Điển Anh - Việt</h1>
          </legend>
          <label for="word">Từ Tiếng Anh cần tra</label>
          <input type="text" id="word" name="word" placeholder="Ví dụ: hello, world, computer..." required
            value="${searchWord}" autocomplete="off">
          <button type="submit">Tra cứu</button>

          <c:if test="${not empty meaning}">
            <div>
              <hr>
              <div>Tìm thấy kết quả: </div>
              <div>${searchWord}</div>
              <div>→ ${meaning}</div>
            </div>
          </c:if>

          <c:if test="${notFound}">
            <div>
              <hr>
              <div>Không tìm thấy!</div>
              <div>
                Từ "<strong>${searchWord}</strong>" không có trong từ điển.
                Vui lòng thử lại với từ khác.
              </div>
            </div>
          </c:if>
        </fieldset>
      </form>
    </body>

    </html>