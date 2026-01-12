package com.codegym.blog_wajax.repository;

import com.codegym.blog_wajax.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IBlogRepository extends JpaRepository<Blog, Long> {

    @Query(value = "select b from Blog b")
    Page<Blog> findAllBlog(Pageable pageable);
    Page<Blog> findBlogById(Long id, Pageable pageable);
    Page<Blog> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Blog> findByCategoryId(Long categoryId, Pageable pageable);
    Page<Blog> findByTitleContainingIgnoreCaseAndCategoryId(String title, Long categoryId, Pageable pageable);
}