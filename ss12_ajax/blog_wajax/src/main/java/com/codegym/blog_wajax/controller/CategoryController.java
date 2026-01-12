package com.codegym.blog_wajax.controller;


import com.codegym.blog_wajax.entity.Category;
import com.codegym.blog_wajax.service.ICategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String showAllCategories(Model model){
        model.addAttribute("categories", categoryService.findAll());
        return "category/list";
    }

    @GetMapping("/create")
    public String createCategory(Model model){
        model.addAttribute("category", new Category());
        return "category/create";
    }

    @GetMapping("/{id}/update")
    public String updateCategory(@PathVariable() Long id, Model model){
        model.addAttribute("category", categoryService.findById(id));
        return "category/update";
    }

    @PostMapping("/create")
    public String saveCategory(@ModelAttribute("category") Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (category.getName().isEmpty()) {
            bindingResult.rejectValue("name", null, "Name cannot be empty");
        }

        if(bindingResult.hasErrors()){
            return "category/create";
        }

        categoryService.save(category);
        redirectAttributes.addFlashAttribute("message", "Category created successfully!");
        return "redirect:/categories";
    }

    @PostMapping("/update")
    public String updateCategory(@ModelAttribute("category") Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (category.getName().isEmpty()) {
            bindingResult.rejectValue("name", null, "Name cannot be empty");
        }

        if(bindingResult.hasErrors()){
            return "category/update";
        }

        categoryService.update(category);
        redirectAttributes.addFlashAttribute("message", "Category updated successfully!");
        return "redirect:/categories";
    }

    @PostMapping("/{id}/delete")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes){
        Category category = categoryService.findById(id);

        if(category != null){
            categoryService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Category deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Category cannot be deleted!");
        }

        return "redirect:/categories";
    }
}
