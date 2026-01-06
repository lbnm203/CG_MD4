package com.codegym.blog_applications.repository;

import com.codegym.blog_applications.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAllByTitleContaining(String title);
    List<Blog> findAllByOrderByCreatedDateDesc();
}
