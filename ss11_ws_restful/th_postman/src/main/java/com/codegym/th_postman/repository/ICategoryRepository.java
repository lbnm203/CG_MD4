package com.codegym.blog_applications.repository;

import com.codegym.blog_applications.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
}
