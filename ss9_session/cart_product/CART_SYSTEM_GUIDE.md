# SHOPPING CART SYSTEM - USER GUIDE

## 📋 Tổng quan hệ thống

Hệ thống giỏ hàng hoàn chỉnh với 3 trang chính:
1. **List Page** - Danh sách sản phẩm
2. **Detail Page** - Chi tiết sản phẩm
3. **Cart Page** - Giỏ hàng

---

## 🎯 Tính năng Cart Page

### 1. Hiển thị giỏ hàng
- ✅ Bảng danh sách sản phẩm trong giỏ
- ✅ Hình ảnh sản phẩm
- ✅ Tên sản phẩm và mã sản phẩm
- ✅ Đơn giá
- ✅ Số lượng (có thể tăng/giảm)
- ✅ Thành tiền (đơn giá × số lượng)
- ✅ Tổng giá trị giỏ hàng

### 2. Thao tác với giỏ hàng
- ✅ Tăng/giảm số lượng sản phẩm
- ✅ Xóa sản phẩm khỏi giỏ
- ✅ Xóa toàn bộ giỏ hàng
- ✅ Tiếp tục mua hàng
- ✅ Thanh toán (placeholder)

### 3. Trạng thái giỏ hàng
- ✅ Hiển thị thông báo khi giỏ trống
- ✅ Nút "Mua sắm ngay" khi giỏ trống

---

## 🔗 Navigation Flow

```
┌─────────────────┐
│   List Page     │ (/product)
│  - Xem chi tiết │
│  - Thêm vào giỏ │
└────────┬────────┘
         │
         ├──────────────┐
         │              │
         ▼              ▼
┌─────────────┐  ┌──────────────┐
│ Detail Page │  │  Cart Page   │
│ (/detail/id)│  │  (/cart)     │
│- Thêm vào giỏ│  │- Update qty  │
└──────┬──────┘  │- Remove item │
       │         │- Clear cart  │
       │         └──────┬───────┘
       │                │
       └────────┬───────┘
                ▼
         Quay lại List
```

---

## 📊 Cấu trúc Cart Page

```
┌──────────────────────────────────────────────────────┐
│  🛒 Giỏ hàng của bạn (Header - Primary)             │
├──────────────────────────────────────────────────────┤
│  ⬅️ Tiếp tục mua hàng                                │
│  ──────────────────────────────────────────────      │
│                                                      │
│  ┌────────────────────────────────────────────────┐ │
│  │ # │ Hình │ Tên SP │ Giá │ SL │ Thành tiền │ ⚙️ │ │
│  ├───┼──────┼────────┼─────┼────┼────────────┼───┤ │
│  │ 1 │ 🖼️   │ SP 1   │ 500k│ 2  │ 1.000k     │🗑️ │ │
│  │ 2 │ 🖼️   │ SP 2   │ 300k│ 1  │ 300k       │🗑️ │ │
│  ├───┴──────┴────────┴─────┴────┼────────────┴───┤ │
│  │              Tổng cộng:      │   1.300.000 ₫  │ │
│  └──────────────────────────────┴────────────────┘ │
│                                                      │
│  ┌──────────────────┐  ┌───────────────────────┐   │
│  │ 🗑️ Xóa toàn bộ   │  │ 💳 Thanh toán         │   │
│  └──────────────────┘  └───────────────────────┘   │
└──────────────────────────────────────────────────────┘
```

---

## 🛠️ API Endpoints

### Product List
```
GET /product
- Hiển thị danh sách sản phẩm
```

### Product Detail
```
GET /product/detail/{id}
- Hiển thị chi tiết sản phẩm
```

### Cart Operations
```
GET /product/cart
- Hiển thị giỏ hàng
- Tự động tạo giỏ mới nếu chưa có

GET /product/cart/{productId}?quantity=1
- Thêm sản phẩm vào giỏ
- Mặc định quantity = 1

GET /product/cart/{cartId}/update/{productId}?quantity={newQty}
- Cập nhật số lượng sản phẩm
- Nếu quantity <= 0: xóa sản phẩm

GET /product/cart/{cartId}/remove/{productId}
- Xóa sản phẩm khỏi giỏ

GET /product/cart/{cartId}/clear
- Xóa toàn bộ giỏ hàng
```

---

## 💾 Session Management

Cart ID được lưu trong Session:
```java
@SessionAttribute(value = "cartId", required = false) Long cartId
```

- Khi user truy cập lần đầu: tạo cart mới
- Các lần sau: sử dụng cart đã có trong session
- Cart được lưu trong database

---

## 📝 Data Model

### Cart Entity
```java
@Entity
public class Cart {
    private Long id;
    
    @ElementCollection
    private Map<Long, Integer> items; // productId -> quantity
}
```

### Cart Service Methods
```java
- createCart()                          // Tạo giỏ mới
- getCartById(Long id)                  // Lấy giỏ theo ID
- addProductToCart(cartId, productId, qty) // Thêm SP
- updateProductQuantity(...)            // Cập nhật SL
- removeProductFromCart(...)            // Xóa SP
- clearCart(cartId)                     // Xóa toàn bộ
- calculateTotalPrice(cartId)           // Tính tổng giá
- getCartProducts(cartId)               // Lấy danh sách SP
- getTotalItems(cartId)                 // Tổng số lượng
```

---

## 🎨 UI Features

### Bảng giỏ hàng
- ✅ Responsive table
- ✅ Striped rows (table-striped)
- ✅ Hover effect (table-hover)
- ✅ Bordered (table-bordered)
- ✅ Aligned middle (align-middle)

### Số lượng sản phẩm
- ✅ Input group với nút +/-
- ✅ Giá trị hiển thị ở giữa
- ✅ Readonly input
- ✅ Buttons với icons

### Giá cả
- ✅ Đơn giá: màu primary
- ✅ Thành tiền: màu danger (đỏ)
- ✅ Tổng cộng: màu danger, font lớn
- ✅ Format số: 1.000.000 ₫

### Thông báo
- ✅ Alert success (màu xanh)
- ✅ Alert danger (màu đỏ)
- ✅ Dismissible (có nút đóng)
- ✅ Icons Bootstrap

### Empty State
- ✅ Icon giỏ hàng trống lớn
- ✅ Thông báo rõ ràng
- ✅ Nút "Mua sắm ngay"

---

## 🔄 JavaScript Functions

```javascript
updateQuantity(productId, newQuantity)
- Cập nhật số lượng sản phẩm
- Confirm nếu quantity < 1

removeProduct(productId)
- Xóa sản phẩm với confirm dialog

clearCart()
- Xóa toàn bộ giỏ với confirm dialog
```

---

## ✨ Highlights

1. **Layout nhất quán**: Giống list.html
   - Card shadow-lg
   - Header bg-primary
   - Bootstrap 5 styling

2. **User Experience**:
   - Thông báo rõ ràng
   - Confirm dialogs
   - Responsive design
   - Icons trực quan

3. **Data Integrity**:
   - Validation số lượng
   - Kiểm tra tồn kho
   - Error handling

4. **Session Management**:
   - Cart ID trong session
   - Persistent cart
   - Auto-create cart

---

## 🚀 Cách sử dụng

1. **Xem danh sách**: Truy cập `/product`
2. **Thêm vào giỏ**: Click "Thêm vào giỏ" hoặc "Xem chi tiết" → "Thêm vào giỏ hàng"
3. **Xem giỏ hàng**: Click "Giỏ hàng" hoặc truy cập `/product/cart`
4. **Cập nhật số lượng**: Click nút +/- trong giỏ hàng
5. **Xóa sản phẩm**: Click icon 🗑️
6. **Xóa toàn bộ**: Click "Xóa toàn bộ giỏ hàng"
7. **Tiếp tục mua**: Click "Tiếp tục mua hàng"

---

## 📌 Notes

- Cart được lưu trong database
- Session timeout sẽ mất cartId (cần implement session persistence)
- Có thể mở rộng thêm tính năng checkout
- Có thể thêm voucher/discount
- Có thể thêm shipping calculator
