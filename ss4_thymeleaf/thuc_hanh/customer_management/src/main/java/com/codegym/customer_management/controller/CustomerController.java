package com.codegym.customer_management.controller;

import com.codegym.customer_management.entity.Customer;
import com.codegym.customer_management.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("")
    public String showPage(@RequestParam(value = "search", required = false) String keyword, Model model){
        List<Customer> customers;
        if (keyword != null && !keyword.trim().isEmpty()) {
            customers = customerService.searchByName(keyword);
            model.addAttribute("keyword", keyword);
        } else {
            customers = customerService.findAll();
        }
        model.addAttribute("customers", customers);
        return "customer/list";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("customer", new Customer());
        return "customer/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("customer") Customer customer, RedirectAttributes redirectAttributes, BindingResult bindingResult){
        if (customer.getName().isEmpty()) {
            bindingResult.rejectValue("name", null, "Name cannot be empty");
        }

        if (customer.getEmail().isEmpty()) {
            bindingResult.rejectValue("email", null, "Email cannot be empty");
        }

        if (customer.getAddress().isEmpty()) {
            bindingResult.rejectValue("address", null, "Address cannot be empty");
        }

        if (bindingResult.hasErrors()) {
            return "customer/create";
        }

        customerService.save(customer);
        redirectAttributes.addFlashAttribute("success", "Customer saved successfully!");
        return "redirect:/customers";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model){
        model.addAttribute("customer", customerService.findById(id));
        return "customer/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("customer") Customer customer, RedirectAttributes redirectAttributes, BindingResult bindingResult){
        if (customer.getName().isEmpty()) {
            bindingResult.rejectValue("name", null, "Name cannot be empty");
        }

        if (customer.getEmail().isEmpty()) {
            bindingResult.rejectValue("email", null, "Email cannot be empty");
        }

        if (customer.getAddress().isEmpty()) {
            bindingResult.rejectValue("address", null, "Address cannot be empty");
        }

        if (bindingResult.hasErrors()) {
            return "customer/update";
        }

        customerService.update(customer.getId(), customer);
        redirectAttributes.addFlashAttribute("success", "Customer updated successfully!");
        return "redirect:/customers";
    }

    @GetMapping("/{id}/delete")
    public String showDelete(@PathVariable("id") Integer id, Model model){
        model.addAttribute("customer", customerService.findById(id));
        return "customer/delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("customer") Customer customer, RedirectAttributes redirectAttributes){
        customerService.remove(customer.getId());

        redirectAttributes.addFlashAttribute("success", "Customer deleted successfully!");
        return "redirect:/customers";
    }

    @GetMapping("/{id}/view")
    public String showView(@PathVariable("id") Integer id, Model model){
        model.addAttribute("customer", customerService.findById(id));
        return "customer/view";
    }

}
