package com.codegym.cart_product.service;

import com.codegym.cart_product.entity.Cart;
import com.codegym.cart_product.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ICartService {
    /**
     * Tạo giỏ hàng mới
     */
    Cart createCart();

    /**
     * Lấy giỏ hàng theo ID
     */
    Optional<Cart> getCartById(Long id);

    /**
     * Lưu giỏ hàng
     */
    Cart saveCart(Cart cart);

    /**
     * Thêm sản phẩm vào giỏ hàng
     */
    void addProductToCart(Long cartId, Long productId, Integer quantity);

    /**
     * Xóa sản phẩm khỏi giỏ hàng
     */
    void removeProductFromCart(Long cartId, Long productId);

    /**
     * Cập nhật số lượng sản phẩm trong giỏ
     */
    void updateProductQuantity(Long cartId, Long productId, Integer quantity);

    /**
     * Lấy tổng số lượng sản phẩm trong giỏ
     */
    int getTotalItems(Long cartId);

    /**
     * Lấy danh sách sản phẩm trong giỏ với thông tin chi tiết
     */
    List<Product> getCartProducts(Long cartId);

    /**
     * Tính tổng giá trị giỏ hàng
     */
    Double calculateTotalPrice(Long cartId);

    /**
     * Xóa tất cả sản phẩm trong giỏ
     */
    void clearCart(Long cartId);
}
