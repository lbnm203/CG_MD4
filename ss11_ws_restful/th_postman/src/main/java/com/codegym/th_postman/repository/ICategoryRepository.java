package com.codegym.th_postman.repository;

import com.codegym.th_postman.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
//    Page<Category> findAllCategory(Pageable pageable);
}
