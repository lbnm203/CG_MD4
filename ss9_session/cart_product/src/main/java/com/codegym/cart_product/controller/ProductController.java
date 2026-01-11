package com.codegym.cart_product.controller;

import com.codegym.cart_product.entity.Cart;
import com.codegym.cart_product.entity.Product;
import com.codegym.cart_product.service.ICartService;
import com.codegym.cart_product.service.IProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final IProductService productService;
    private final ICartService cartService;

    public ProductController(IProductService productService, ICartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    @GetMapping
    public String showProduct(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product/list";
    }

    @GetMapping("/detail/{id}")
    public String showProductDetail(@PathVariable Long id, Model model) {
        Optional<Product> productOptional = productService.getProductById(id);
        if (productOptional.isPresent()) {
            model.addAttribute("product", productOptional.get());
            return "product/detail";
        } else {
            return "redirect:/product";
        }
    }

}
