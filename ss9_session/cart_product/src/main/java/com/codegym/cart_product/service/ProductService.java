package com.codegym.cart_product.service;

import com.codegym.cart_product.entity.Product;
import com.codegym.cart_product.repository.IProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService{
    private final IProductRepository productRepository;
    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Boolean save(Product product) {
        if (product.getId() == null) {
            productRepository.save(product);
            return true;
        } else {
            if (productRepository.existsById(product.getId())) {
                return false;
            } else {
                productRepository.save(product);
                return true;
            }
        }
    }

    @Override
    public Boolean deleteById(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
}
