package com.codegym.blog_wajax.service;


import com.codegym.blog_wajax.entity.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICategoryService {
    List<Category> findAll();
    Page<Category> getAllCategory(int size, int page);
    Category findById(Long id);
    boolean save(Category category);
    boolean update(Category category);
    boolean delete(Long id);
}
