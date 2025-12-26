<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>

    <head>
      <title>Title</title>
    </head>

    <body>
      <fieldset>
        <legend>Kết quả chuyển đổi </legend>
        <label>Tỉ giá (VND/USD): </label>
        <%= String.format("%,.0f", (Double) request.getAttribute("rate")) %> VNĐ/USD <br><br>
          <label>Số tiền USD</label>
          $ <%= String.format("%,.2f", (Double) request.getAttribute("usd")) %> <br><br>
            <label>Số tiền VNĐ nhận được</label>
            <%= String.format("%,.0f", (Double) request.getAttribute("vnd")) %> VNĐ <br><br>
              <a href="/"><button type="button">Trở về</button></a>
      </fieldset>
    </body>

    </html>