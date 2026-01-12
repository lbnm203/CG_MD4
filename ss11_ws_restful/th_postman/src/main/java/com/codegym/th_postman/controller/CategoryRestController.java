package com.codegym.th_postman.controller;

import com.codegym.th_postman.entity.Blog;
import com.codegym.th_postman.entity.Category;
import com.codegym.th_postman.service.IBlogService;
import com.codegym.th_postman.service.ICategoryService;
import com.codegym.th_postman.service.imp.BlogService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {
    private final ICategoryService categoryService;
    private final IBlogService blogService;

    public CategoryRestController(ICategoryService categoryService, BlogService blogService) {
        this.categoryService = categoryService;
        this.blogService = blogService;
    }

    @GetMapping("")
    public ResponseEntity<Page<Category>> getAllCategory(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size){
        Page<Category> categories = categoryService.getAllCategory(size, page);
        return ResponseEntity.ok(categories);
    }

    // GET: Lấy category theo id
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id){
        Category category = categoryService.findById(id);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/{categoryId}/blogs")
    public ResponseEntity<Page<Blog>> getBlogsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
//        Page<Blog> blogs = blogService.findByCategoryId(size, page, categoryId);
        Category category = categoryService.findById(categoryId);
        Page<Blog> blogs = blogService.findByCategoryId(size, page, categoryId);
//        Page<BlogDTO> blogDTOs = blogs.map(BlogDTO::new);

        if (blogs.hasContent()) {
            return new ResponseEntity<>(blogs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
