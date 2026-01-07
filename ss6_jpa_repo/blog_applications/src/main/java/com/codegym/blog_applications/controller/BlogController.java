package com.codegym.blog_applications.controller;

import com.codegym.blog_applications.entity.Blog;
import com.codegym.blog_applications.entity.Category;
import com.codegym.blog_applications.service.IBlogService;
import com.codegym.blog_applications.service.ICategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/blogs")
public class BlogController {
    private final IBlogService blogService;
    private final ICategoryService categoryService;

    public BlogController(IBlogService blogService, ICategoryService categoryService) {
        this.blogService = blogService;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String showAllBlogs(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size) {
        model.addAttribute("blogs", blogService.getAllBlog(size, page));
        model.addAttribute("categories", categoryService.findAll());
        return "blog/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("blog", new Blog());
        model.addAttribute("categories", categoryService.findAll());
        return "blog/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("blog") Blog blog, @RequestParam("categoryId") Long categoryId, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (blog.getTitle().isEmpty()) {
            bindingResult.rejectValue("title", null, "Name title cannot be empty");
        }

        if (blog.getAuthor().isEmpty()) {
            bindingResult.rejectValue("author", null, "Name author cannot be empty");
        }

        if(bindingResult.hasErrors()) {
            return "blog/create";
        }

        Category category = categoryService.findById(categoryId);
        blog.setCategory(category);

        blogService.save(blog);
        redirectAttributes.addFlashAttribute("message", "Blog created successfully");
        return "redirect:/blogs";
    }

    @GetMapping("/{id}/detail")
    public String showDetail(@PathVariable("id") Long id, Model model){
        model.addAttribute("blog", blogService.findById(id));
        return "blog/details";
    }

    @GetMapping("/{id}/update")
    public String showEditBlog(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Blog blog = blogService.findById(id);

        if (blog == null) {
            redirectAttributes.addFlashAttribute("message", "Không tìm thấy blog!");
            return "redirect:/blogs";
        }

        model.addAttribute("blog", blogService.findById(id));
        model.addAttribute("categories", categoryService.findAll());
        return "blog/update";
    }

    @GetMapping("/categories/{id}")
    public String showBlogsByCategory(@PathVariable Long id,
                                      Model model,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "6") int size) {
        Category category = categoryService.findById(id);

        if (category == null) {
            return "redirect:/blogs";
        }

        Page<Blog> blogPage = blogService.findByCategoryId(size, page, id);

        model.addAttribute("blogs", blogPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", blogPage.getTotalPages());
        model.addAttribute("selectedCategory", category);
        model.addAttribute("categories", categoryService.findAll());
        return "blog/list";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                         @RequestParam(value = "categoryId", required = false) Long categoryId,
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "size", defaultValue = "6") int size,
                         Model model){
        Page<Blog> blogPage;

        if ((keyword != null && !keyword.trim().isEmpty()) && categoryId != null) {
            blogPage = blogService.searchByTitleAndCategory(size, page, keyword, categoryId);
        }
        else if (keyword != null && !keyword.trim().isEmpty()) {
            blogPage = blogService.searchByTitle(size, page, keyword);
        }
        else if (categoryId != null) {
            blogPage = blogService.findByCategoryId(size, page, categoryId);
        }
        else {
            blogPage = blogService.getAllBlog(size, page);
        }
        model.addAttribute("blogs", blogPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", blogPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("categories", categoryService.findAll());
        return "blog/list";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("blog") Blog blog, @RequestParam("categoryId") Long categoryId, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (blog.getTitle().isEmpty()) {
            bindingResult.rejectValue("title", null, "Title title cannot be empty");
        }

        if (blog.getAuthor().isEmpty()) {
            bindingResult.rejectValue("author", null, "Author author cannot be empty");
        }


        if(bindingResult.hasErrors()) {
            return "blog/update";
        }

        Blog existingBlog = blogService.findById(blog.getId());

        if (existingBlog != null) {
            blog.setCreatedDate(existingBlog.getCreatedDate());
            blog.setUpdatedDate(LocalDateTime.now());
        }

        Category category = categoryService.findById(categoryId);
        blog.setCategory(category);

        blogService.update(blog);
        redirectAttributes.addFlashAttribute("message", "Blog updated successfully");
        return "redirect:/blogs";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Blog blog = blogService.findById(id);

        if (blog != null) {
            blogService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Delete Blog successfully!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Blog not found!");
        }

        return "redirect:/blogs";
    }
}
