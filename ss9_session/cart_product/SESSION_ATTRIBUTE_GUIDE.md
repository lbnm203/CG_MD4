# @SessionAttribute - GIẢI THÍCH CHI TIẾT

## 🎯 TẠI SAO CẦN @SessionAttribute?

### Vấn đề
Khi user mua hàng trên website:
- User CHƯA đăng nhập (anonymous)
- User thêm sản phẩm vào giỏ
- User chuyển trang, reload browser
- **Làm sao nhớ giỏ hàng của user?**

### Giải pháp: HTTP Session

```
┌──────────────────────────────────────┐
│     USER (Browser)                   │
│  Cookie: JSESSIONID=ABC123           │
└──────────────┬───────────────────────┘
               │
               ↓
┌──────────────────────────────────────┐
│     SERVER (Spring Boot)             │
│  HttpSession: ABC123                 │
│    └─ cartId = 1                     │
└──────────────┬───────────────────────┘
               │
               ↓
┌──────────────────────────────────────┐
│     DATABASE                         │
│  Cart (id=1)                         │
│    └─ items: {101=2, 102=1}          │
└──────────────────────────────────────┘
```

---

## 📚 SESSION LÀ GÌ?

### HTTP Session
- Lưu trữ dữ liệu **TẠM THỜI** trên server
- Mỗi user có 1 session riêng
- Session được identify bằng **JSESSIONID** (cookie)
- Tồn tại trong khoảng thời gian (default: 30 phút)

### So sánh với các cách lưu trữ khác

| Cách lưu | Vị trí | Thời gian | Use case |
|----------|--------|-----------|----------|
| **Session** | Server | Tạm thời (30 phút) | Giỏ hàng, user chưa login |
| **Cookie** | Browser | Lâu dài | Remember me, preferences |
| **Database** | Server | Vĩnh viễn | User data, orders |
| **LocalStorage** | Browser | Vĩnh viễn | Client-side data |

---

## 🔍 @SessionAttribute HOẠT ĐỘNG NHƯ THẾ NÀO?

### Code

```java
@GetMapping
public String showCart(
    @SessionAttribute(value = "cartId", required = false) Long cartId,
    Model model
) {
    // Spring tự động lấy cartId từ HttpSession
    // Tương đương: Long cartId = session.getAttribute("cartId");
}
```

### Giải thích

```java
@SessionAttribute(
    value = "cartId",      // Tên attribute trong session
    required = false       // Có thể null (lần đầu chưa có)
)
Long cartId
```

### Tương đương với

```java
@GetMapping
public String showCart(HttpSession session, Model model) {
    Long cartId = (Long) session.getAttribute("cartId");
    if (cartId == null) {
        // Lần đầu tiên, chưa có giỏ hàng
    }
}
```

---

## 🔄 FLOW HOÀN CHỈNH

### Lần 1: User thêm sản phẩm đầu tiên

```
1. User click "Thêm vào giỏ" (Product 101)
   ↓
2. Browser gửi request: GET /cart/add/101
   Cookie: (chưa có JSESSIONID)
   ↓
3. Server:
   - Tạo HttpSession mới
   - Tạo JSESSIONID = ABC123
   - @SessionAttribute → cartId = null (lần đầu)
   ↓
4. Controller:
   if (cartId == null) {
       Cart cart = cartService.createCart();  // Tạo cart mới, id=1
       cartId = cart.getId();                 // cartId = 1
       session.setAttribute("cartId", 1);     // ✅ LƯU VÀO SESSION
   }
   cartService.addProductToCart(1, 101, 1);
   ↓
5. Server response:
   Set-Cookie: JSESSIONID=ABC123
   Redirect: /cart
   ↓
6. Browser lưu cookie: JSESSIONID=ABC123
```

### Lần 2: User thêm sản phẩm thứ 2

```
1. User click "Thêm vào giỏ" (Product 102)
   ↓
2. Browser gửi request: GET /cart/add/102
   Cookie: JSESSIONID=ABC123  ← GỬI COOKIE
   ↓
3. Server:
   - Tìm HttpSession với ID = ABC123
   - @SessionAttribute → cartId = 1  ← LẤY TỪ SESSION
   ↓
4. Controller:
   if (cartId == null) {
       // SKIP (cartId đã có = 1)
   }
   cartService.addProductToCart(1, 102, 1);  ← Dùng cart cũ
   ↓
5. Redirect: /cart
```

### Lần 3: User xem giỏ hàng

```
1. User truy cập: GET /cart
   Cookie: JSESSIONID=ABC123
   ↓
2. Server:
   - Tìm HttpSession với ID = ABC123
   - @SessionAttribute → cartId = 1
   ↓
3. Controller:
   Cart cart = cartService.getCartById(1);
   // Hiển thị giỏ hàng với SP 101 và 102
```

---

## ⚠️ VẤN ĐỀ TRONG CODE CŨ

### Code SAI

```java
@GetMapping("/cart/{productId}")
public String addToCart(..., Model model) {
    if (cartId == null) {
        Cart cart = cartService.createCart();
        cartId = cart.getId();
        model.addAttribute("cartId", cartId);  // ❌ SAI!
    }
}
```

### Tại sao SAI?

```
model.addAttribute("cartId", cartId)
    ↓
Lưu vào Model (chỉ tồn tại trong 1 request)
    ↓
Sau khi redirect → Model bị XÓA
    ↓
Request tiếp theo → cartId = null lại
    ↓
Tạo Cart mới → Mất giỏ hàng cũ!
```

### Code ĐÚNG

```java
@GetMapping("/cart/add/{productId}")
public String addToCart(..., HttpSession session) {
    if (cartId == null) {
        Cart cart = cartService.createCart();
        cartId = cart.getId();
        session.setAttribute("cartId", cartId);  // ✅ ĐÚNG!
    }
}
```

### Tại sao ĐÚNG?

```
session.setAttribute("cartId", cartId)
    ↓
Lưu vào HttpSession (tồn tại qua nhiều request)
    ↓
Sau khi redirect → Session vẫn còn
    ↓
Request tiếp theo → cartId = 1 (lấy từ session)
    ↓
Dùng lại Cart cũ → Giữ được giỏ hàng!
```

---

## 📊 SO SÁNH Model vs Session

| Aspect | Model | Session |
|--------|-------|---------|
| **Scope** | 1 request | Nhiều request |
| **Lifetime** | Đến khi response | 30 phút (default) |
| **Storage** | Server memory | Server memory |
| **Use case** | Truyền data đến view | Lưu trữ tạm thời |
| **Ví dụ** | `model.addAttribute("user", user)` | `session.setAttribute("cartId", 1)` |

---

## 💡 KHI NÀO DÙNG @SessionAttribute?

### ✅ NÊN DÙNG

1. **Giỏ hàng** (user chưa login)
2. **Multi-step form** (wizard)
3. **Temporary user data** (chưa lưu DB)
4. **Shopping flow** (product selection)

### ❌ KHÔNG NÊN DÙNG

1. **User đã login** → Dùng Database
2. **Permanent data** → Dùng Database
3. **Large data** → Session tốn memory
4. **Sensitive data** → Không an toàn

---

## 🔐 SESSION TIMEOUT

### Default: 30 phút

```properties
# application.properties
server.servlet.session.timeout=30m
```

### Khi timeout:
- Session bị xóa
- cartId mất
- User phải tạo giỏ mới

### Giải pháp:
1. Tăng timeout
2. Lưu cart vào Database khi user login
3. Dùng Cookie để remember cart

---

## 📝 TÓM TẮT

### @SessionAttribute

```java
@SessionAttribute(value = "cartId", required = false) Long cartId
```

**Làm gì?**
- Lấy giá trị từ HttpSession
- Tự động inject vào parameter

**Khi nào dùng?**
- Cần lưu data qua nhiều request
- User chưa login
- Temporary data

**Lưu ý:**
- Phải LƯU vào session: `session.setAttribute("cartId", value)`
- KHÔNG dùng `model.addAttribute()` cho session data
- Session có timeout (default 30 phút)

---

## 🚀 BEST PRACTICES

### 1. Luôn check null

```java
if (cartId == null) {
    // Tạo mới
}
```

### 2. Lưu vào session đúng cách

```java
session.setAttribute("cartId", cartId);  // ✅
model.addAttribute("cartId", cartId);    // ❌
```

### 3. Clear session khi không cần

```java
session.removeAttribute("cartId");
```

### 4. Set timeout hợp lý

```properties
server.servlet.session.timeout=60m
```

---

## 🎓 KẾT LUẬN

**@SessionAttribute** là cách Spring Boot giúp bạn:
- Lưu trữ dữ liệu tạm thời
- Qua nhiều request
- Cho từng user riêng biệt
- Mà không cần database

**Perfect cho giỏ hàng!** 🛒
