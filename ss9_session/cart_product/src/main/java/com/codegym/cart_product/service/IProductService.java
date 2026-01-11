package com.codegym.cart_product.service;

import com.codegym.cart_product.entity.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    Boolean save(Product product);
    Boolean deleteById(Long id);
    List<Product> getAllProducts();
    Optional<Product> getProductById(Long id);

}
