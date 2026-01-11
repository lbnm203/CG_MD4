package com.codegym.cart_product.controller;

import com.codegym.cart_product.entity.Cart;
import com.codegym.cart_product.entity.Product;
import com.codegym.cart_product.service.ICartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final ICartService cartService;
    
    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String showCart(@SessionAttribute(value = "cartId", required = false) Long cartId,
                          HttpSession session,
                          Model model) {
        Cart cart;

        if (cartId == null) {
            cart = cartService.createCart();
            session.setAttribute("cartId", cart.getId());
        } else {
            cart = cartService.getCartById(cartId)
                    .orElse(cartService.createCart());
        }

        // Lấy thông tin sản phẩm
        Map<Long, Product> productsMap = new HashMap<>();
        if (cart.getItems() != null && !cart.getItems().isEmpty()) {
            List<Product> products = cartService.getCartProducts(cart.getId());
            for (Product product : products) {
                productsMap.put(product.getId(), product);
            }
        }

        Double totalPrice = cartService.calculateTotalPrice(cart.getId());

        model.addAttribute("cart", cart);
        model.addAttribute("products", productsMap);
        model.addAttribute("totalPrice", totalPrice);

        return "product/cart";
    }

    @GetMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId,
                           @SessionAttribute(value = "cartId", required = false) Long cartId,
                           @RequestParam(defaultValue = "1") Integer quantity,
                           @RequestParam(required = false) String returnUrl,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        try {
            if (cartId == null) {
                Cart cart = cartService.createCart();
                cartId = cart.getId();
                session.setAttribute("cartId", cartId);
            }

            cartService.addProductToCart(cartId, productId, quantity);
            redirectAttributes.addFlashAttribute("message", "Đã thêm sản phẩm vào giỏ hàng!");
            
            if (returnUrl != null && !returnUrl.isEmpty()) {
                return "redirect:" + returnUrl;
            }
            return "redirect:/product";
            
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            if (returnUrl != null && !returnUrl.isEmpty()) {
                return "redirect:" + returnUrl;
            }
            return "redirect:/product";
        }
    }

    @GetMapping("/update/{productId}")
    public String updateCartItem(@SessionAttribute(value = "cartId", required = false) Long cartId,
                                 @PathVariable Long productId,
                                 @RequestParam Integer quantity,
                                 RedirectAttributes redirectAttributes) {
        if (cartId == null) {
            redirectAttributes.addFlashAttribute("error", "Giỏ hàng không tồn tại!");
            return "redirect:/cart";
        }

        try {
            cartService.updateProductQuantity(cartId, productId, quantity);
            redirectAttributes.addFlashAttribute("message", "Đã cập nhật số lượng!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cart";
    }

    @GetMapping("/remove/{productId}")
    public String removeCartItem(@SessionAttribute(value = "cartId", required = false) Long cartId,
                                 @PathVariable Long productId,
                                 RedirectAttributes redirectAttributes) {
        if (cartId == null) {
            redirectAttributes.addFlashAttribute("error", "Giỏ hàng không tồn tại!");
            return "redirect:/cart";
        }

        cartService.removeProductFromCart(cartId, productId);
        redirectAttributes.addFlashAttribute("message", "Đã xóa sản phẩm khỏi giỏ hàng!");
        return "redirect:/cart";
    }

    @GetMapping("/clear")
    public String clearCart(@SessionAttribute(value = "cartId", required = false) Long cartId,
                           RedirectAttributes redirectAttributes) {
        if (cartId == null) {
            redirectAttributes.addFlashAttribute("error", "Giỏ hàng không tồn tại!");
            return "redirect:/cart";
        }

        cartService.clearCart(cartId);
        redirectAttributes.addFlashAttribute("message", "Đã xóa toàn bộ giỏ hàng!");
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(@SessionAttribute(value = "cartId", required = false) Long cartId,
                          HttpSession session,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        if (cartId == null) {
            redirectAttributes.addFlashAttribute("error", "Giỏ hàng không tồn tại!");
            return "redirect:/cart";
        }

        Cart cart = cartService.getCartById(cartId).orElse(null);
        if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Giỏ hàng trống! Vui lòng thêm sản phẩm trước khi thanh toán.");
            return "redirect:/cart";
        }

        // Lấy thông tin sản phẩm TRƯỚC KHI XÓA
        Map<Long, Product> productsMap = new HashMap<>();
        List<Product> products = cartService.getCartProducts(cart.getId());
        for (Product product : products) {
            productsMap.put(product.getId(), product);
        }

        // Tạo bản sao cart items để hiển thị
        Map<Long, Integer> cartItemsCopy = new HashMap<>(cart.getItems());

        // Tính tổng tiền
        Double totalAmount = cartService.calculateTotalPrice(cartId);

        // Tạo mã đơn hàng
        String orderId = "ORD" + System.currentTimeMillis();

        // Xóa giỏ hàng NGAY SAU KHI lấy dữ liệu
        cartService.clearCart(cartId);
        session.removeAttribute("cartId");

        // Tạo cart object mới với dữ liệu đã copy
        Cart displayCart = new Cart();
        displayCart.setItems(cartItemsCopy);

        // Lưu thông tin đơn hàng vào model
        model.addAttribute("orderId", orderId);
        model.addAttribute("cart", displayCart);
        model.addAttribute("products", productsMap);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("paymentMethod", "Thanh toán khi nhận hàng");

        return "product/success_payment";
    }
}
