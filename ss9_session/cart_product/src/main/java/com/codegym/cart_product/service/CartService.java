package com.codegym.cart_product.service;

import com.codegym.cart_product.entity.Cart;
import com.codegym.cart_product.entity.Product;
import com.codegym.cart_product.repository.ICartRepository;
import com.codegym.cart_product.repository.IProductRepository;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CartService implements ICartService {
    
    private final ICartRepository cartRepository;
    private final IProductRepository productRepository;

    public CartService(ICartRepository cartRepository, IProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Cart createCart() {
        Cart cart = new Cart();
        cart.setItems(new HashMap<>());
        return cartRepository.save(cart);
    }

    @Override
    public Optional<Cart> getCartById(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void addProductToCart(Long cartId, Long productId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NoResultException("Cart not found with id: " + cartId));
        
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoResultException("Product not found with id: " + productId));
        
        // Lấy số lượng hiện tại trong giỏ
        Map<Long, Integer> items = cart.getItems();
        Integer currentQuantityInCart = items.getOrDefault(productId, 0);
        
        // Tính tổng số lượng sau khi thêm
        Integer totalQuantity = currentQuantityInCart + quantity;
        
        // Kiểm tra tồn kho
        if (product.getQuantity() < totalQuantity) {
            throw new RuntimeException(
                String.format("Không đủ hàng! Tồn kho: %d, Trong giỏ: %d, Yêu cầu thêm: %d", 
                    product.getQuantity(), currentQuantityInCart, quantity)
            );
        }
        
        // Kiểm tra số lượng phải > 0
        if (quantity <= 0) {
            throw new RuntimeException("Số lượng phải lớn hơn 0");
        }
        
        // Thêm hoặc cập nhật số lượng sản phẩm trong giỏ
        items.put(productId, totalQuantity);
        
        cartRepository.save(cart);
    }

    @Override
    public void removeProductFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));
        
        cart.getItems().remove(productId);
        cartRepository.save(cart);
    }

    @Override
    public void updateProductQuantity(Long cartId, Long productId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));
        
        if (quantity <= 0) {
            // Nếu số lượng <= 0, xóa sản phẩm khỏi giỏ
            cart.getItems().remove(productId);
        } else {
            // Kiểm tra tồn kho
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
            
            if (product.getQuantity() < quantity) {
                throw new RuntimeException(
                    String.format("Không đủ hàng! Tồn kho chỉ còn: %d, Bạn muốn: %d", 
                        product.getQuantity(), quantity)
                );
            }
            
            cart.getItems().put(productId, quantity);
        }
        
        cartRepository.save(cart);
    }

    @Override
    public int getTotalItems(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));
        
        return cart.getItems().values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    @Override
    public List<Product> getCartProducts(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));
        
        Set<Long> productIds = cart.getItems().keySet();
        return productRepository.findAllById(productIds);
    }

    @Override
    public Double calculateTotalPrice(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));
        
        double total = 0.0;
        
        for (Map.Entry<Long, Integer> entry : cart.getItems().entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();
            
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                total += product.getPrice() * quantity;
            }
        }
        
        return total;
    }

    @Override
    public void clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));
        
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
