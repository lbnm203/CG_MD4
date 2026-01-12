package com.codegym.blog_wajax.service;


import com.codegym.blog_wajax.entity.Blog;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IBlogService {
    List<Blog> findAll();
    Page<Blog> getAllBlog(int size, int page);
    Blog findById(Long id);
    boolean save(Blog blog);
    boolean update(Blog blog);
    boolean delete(Long id);
    Page<Blog> searchByTitle(int size, int page, String title);
    Page<Blog> findByCategoryId(int size, int page, Long categoryId);
//    Optional<Blog> findBlogById(Long id);
    Page<Blog> searchByTitleAndCategory(int size, int page, String title, Long categoryId);
}
