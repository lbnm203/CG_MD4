package com.codegym.customer_management.controller;

import com.codegym.customer_management.entity.Customer;
import com.codegym.customer_management.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String save(Customer customer, RedirectAttributes redirectAttributes){
        customer.setId((int) (Math.random() * 10000));
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
    public String update(Customer customer, RedirectAttributes redirectAttributes){
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
    public String delete(Customer customer, RedirectAttributes redirectAttributes){
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
