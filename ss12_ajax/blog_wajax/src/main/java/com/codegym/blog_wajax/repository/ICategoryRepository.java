package com.codegym.blog_wajax.repository;

import com.codegym.blog_wajax.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
//    Page<Category> findAllCategory(Pageable pageable);
}
