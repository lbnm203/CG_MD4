# VẤN ĐỀ: TẠI SAO HẾT HÀNG VẪN THÊM ĐƯỢC VÀO GIỎ?

## 🐛 **NGUYÊN NHÂN**

### Code SAI (Trước khi sửa)

```java
@Override
public void addProductToCart(Long cartId, Long productId, Integer quantity) {
    Cart cart = cartRepository.findById(cartId).orElseThrow();
    Product product = productRepository.findById(productId).orElseThrow();
    
    // ❌ SAI: Chỉ kiểm tra quantity mới, KHÔNG kiểm tra tổng
    if (product.getQuantity() < quantity) {
        throw new RuntimeException("Insufficient stock");
    }
    
    // Thêm vào giỏ
    Map<Long, Integer> items = cart.getItems();
    items.put(productId, items.getOrDefault(productId, 0) + quantity);
    
    cartRepository.save(cart);
}
```

### Vấn đề

**Ví dụ cụ thể:**

```
Product: SP101
Tồn kho: 5 cái

┌─────────────────────────────────────────────┐
│ Lần 1: User thêm 3 cái                      │
├─────────────────────────────────────────────┤
│ Kiểm tra: 5 < 3? NO → OK ✅                 │
│ Giỏ hàng: {101 = 3}                         │
└─────────────────────────────────────────────┘

┌─────────────────────────────────────────────┐
│ Lần 2: User thêm 3 cái nữa                  │
├─────────────────────────────────────────────┤
│ Kiểm tra: 5 < 3? NO → OK ✅                 │
│                                             │
│ ❌ SAI! Phải kiểm tra:                      │
│    Tồn kho (5) < Trong giỏ (3) + Mới (3)?  │
│    5 < 6? YES → REJECT                      │
│                                             │
│ Giỏ hàng: {101 = 6}  ← VƯỢT TỒN KHO!       │
└─────────────────────────────────────────────┘
```

---

## ✅ **GIẢI PHÁP**

### Code ĐÚNG (Sau khi sửa)

```java
@Override
public void addProductToCart(Long cartId, Long productId, Integer quantity) {
    Cart cart = cartRepository.findById(cartId).orElseThrow();
    Product product = productRepository.findById(productId).orElseThrow();
    
    // ✅ ĐÚNG: Lấy số lượng hiện tại trong giỏ
    Map<Long, Integer> items = cart.getItems();
    Integer currentQuantityInCart = items.getOrDefault(productId, 0);
    
    // ✅ ĐÚNG: Tính tổng số lượng
    Integer totalQuantity = currentQuantityInCart + quantity;
    
    // ✅ ĐÚNG: Kiểm tra tổng với tồn kho
    if (product.getQuantity() < totalQuantity) {
        throw new RuntimeException(
            String.format("Không đủ hàng! Tồn kho: %d, Trong giỏ: %d, Yêu cầu thêm: %d", 
                product.getQuantity(), currentQuantityInCart, quantity)
        );
    }
    
    // Kiểm tra số lượng > 0
    if (quantity <= 0) {
        throw new RuntimeException("Số lượng phải lớn hơn 0");
    }
    
    // Thêm vào giỏ
    items.put(productId, totalQuantity);
    cartRepository.save(cart);
}
```

---

## 📊 **SO SÁNH**

### Trước khi sửa

```
Kiểm tra: product.quantity < quantity
          ↓
     Chỉ kiểm tra số lượng MỚI
          ↓
     KHÔNG kiểm tra số lượng ĐÃ CÓ trong giỏ
          ↓
     User có thể thêm nhiều lần → Vượt tồn kho
```

### Sau khi sửa

```
1. Lấy số lượng hiện tại: currentQuantityInCart
2. Tính tổng: totalQuantity = current + new
3. Kiểm tra: product.quantity < totalQuantity
          ↓
     Kiểm tra TỔNG số lượng
          ↓
     Đảm bảo KHÔNG vượt tồn kho
```

---

## 🎯 **TEST CASES**

### Test Case 1: Thêm lần đầu

```
Tồn kho: 10
Trong giỏ: 0
Thêm: 5

Kiểm tra: 10 < (0 + 5) = 10 < 5? NO → OK ✅
Kết quả: Giỏ = 5
```

### Test Case 2: Thêm lần 2 (OK)

```
Tồn kho: 10
Trong giỏ: 5
Thêm: 3

Kiểm tra: 10 < (5 + 3) = 10 < 8? NO → OK ✅
Kết quả: Giỏ = 8
```

### Test Case 3: Thêm lần 3 (VƯỢT)

```
Tồn kho: 10
Trong giỏ: 8
Thêm: 5

Kiểm tra: 10 < (8 + 5) = 10 < 13? YES → REJECT ❌
Error: "Không đủ hàng! Tồn kho: 10, Trong giỏ: 8, Yêu cầu thêm: 5"
```

### Test Case 4: Hết hàng

```
Tồn kho: 0
Trong giỏ: 0
Thêm: 1

Kiểm tra: 0 < (0 + 1) = 0 < 1? YES → REJECT ❌
Error: "Không đủ hàng! Tồn kho: 0, Trong giỏ: 0, Yêu cầu thêm: 1"
```

---

## 🔍 **CÁC TRƯỜNG HỢP ĐẶC BIỆT**

### 1. Thêm số lượng âm

```java
if (quantity <= 0) {
    throw new RuntimeException("Số lượng phải lớn hơn 0");
}
```

### 2. Sản phẩm không tồn tại

```java
Product product = productRepository.findById(productId)
    .orElseThrow(() -> new NoResultException("Product not found"));
```

### 3. Giỏ hàng không tồn tại

```java
Cart cart = cartRepository.findById(cartId)
    .orElseThrow(() -> new NoResultException("Cart not found"));
```

---

## 💡 **BÀI HỌC**

### Khi validate tồn kho, phải kiểm tra:

1. ✅ Số lượng hiện tại trong giỏ
2. ✅ Số lượng muốn thêm
3. ✅ **TỔNG** số lượng
4. ✅ So sánh với tồn kho

### Công thức

```
if (stock < currentInCart + newQuantity) {
    REJECT
}
```

### KHÔNG được

```
if (stock < newQuantity) {  // ❌ SAI
    REJECT
}
```

---

## 🚀 **KẾT QUẢ SAU KHI SỬA**

### addProductToCart()
- ✅ Kiểm tra tổng số lượng
- ✅ Thông báo lỗi rõ ràng
- ✅ Validate số lượng > 0

### updateProductQuantity()
- ✅ Kiểm tra tồn kho
- ✅ Thông báo lỗi rõ ràng
- ✅ Xóa nếu quantity <= 0

---

## 📝 **TÓM TẮT**

| Vấn đề | Nguyên nhân | Giải pháp |
|--------|-------------|-----------|
| Hết hàng vẫn thêm được | Chỉ kiểm tra quantity mới | Kiểm tra TỔNG (current + new) |
| Vượt tồn kho | Không tính số lượng đã có trong giỏ | Lấy currentQuantityInCart trước |
| Thông báo lỗi không rõ | Message chung chung | Format message chi tiết |

**Công thức vàng:**
```java
totalQuantity = currentInCart + newQuantity
if (stock < totalQuantity) REJECT
```
