package com.codegym.blog_applications.service.imp;

import com.codegym.blog_applications.entity.Category;
import com.codegym.blog_applications.repository.ICategoryRepository;
import com.codegym.blog_applications.service.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    private final ICategoryRepository categoryRepository;

    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public boolean save(Category category) {
        if (category.getId() == null) {
            categoryRepository.save(category);
            return true;
        } else {
            if (categoryRepository.existsById(category.getId())) {
                return false;
            } else  {
                categoryRepository.save(category);
                return true;
            }
        }
    }

    @Override
    public boolean update(Category category) {
        if (categoryRepository.existsById(category.getId())) {
            categoryRepository.save(category);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if(categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
