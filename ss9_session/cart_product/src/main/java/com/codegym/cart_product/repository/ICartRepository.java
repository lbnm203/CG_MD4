package com.codegym.cart_product.repository;

import com.codegym.cart_product.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartRepository extends JpaRepository<Cart, Long> {
}
