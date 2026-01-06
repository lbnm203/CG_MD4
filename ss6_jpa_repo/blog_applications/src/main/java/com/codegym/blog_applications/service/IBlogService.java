package com.codegym.blog_applications.service;


import com.codegym.blog_applications.entity.Blog;

import java.util.List;

public interface IBlogService {
    List<Blog> findAll();
    Blog findById(Long id);
    boolean save(Blog blog);
    boolean update(Blog blog);
    boolean delete(Long id);
    List<Blog> searchByTitle(String title);
}
