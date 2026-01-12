package com.codegym.blog_wajax.controller;

import com.codegym.blog_wajax.entity.Blog;
import com.codegym.blog_wajax.service.IBlogService;
import com.codegym.blog_wajax.service.ICategoryService;
import com.codegym.blog_wajax.service.imp.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/blogs")
public class BlogRestController {
    private final IBlogService blogService;
    private final ICategoryService categoryService;

    public BlogRestController(IBlogService blogService, CategoryService categoryService) {
        this.blogService = blogService;
        this.categoryService = categoryService;
    }

    // GET: /api/blogs?page=0&size=5 (lấy các blog có phân trang)
    @GetMapping("")
    public ResponseEntity<Page<Blog>> getAllBlogs(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "5") int size){
        Page<Blog> blogs = blogService.getAllBlog(size, page);
//        Page<BlogDTO> blogDTOs = blogs.map(BlogDTO::new);
        if (blogs.hasContent()) {
            return new ResponseEntity<>(blogs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET: lấy blog theo id
    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable Long id){
        Blog blog = blogService.findById(id);
        if (blog != null) {
//            BlogDTO blogDTO = new BlogDTO(blog);
            return new ResponseEntity<>(blog, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET: Lấy blogs theo category


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
