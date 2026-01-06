package com.codegym.blog_applications.service;

import com.codegym.blog_applications.entity.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> findAll();
    Category findById(Long id);
    boolean save(Category category);
    boolean update(Category category);
    boolean delete(Long id);
}
