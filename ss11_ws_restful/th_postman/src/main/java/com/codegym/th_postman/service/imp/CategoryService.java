package com.codegym.th_postman.service.imp;


import com.codegym.th_postman.entity.Category;
import com.codegym.th_postman.repository.ICategoryRepository;
import com.codegym.th_postman.service.ICategoryService;
import jakarta.persistence.NoResultException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public Page<Category> getAllCategory(int size, int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return categoryRepository.findAll(PageRequest.of(page, size, sort));
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NoResultException("Not found with id: " + id + ""));
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
