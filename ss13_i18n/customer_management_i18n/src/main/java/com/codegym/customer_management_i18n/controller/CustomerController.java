package com.codegym.customer_management_i18n.controller;

import com.codegym.customer_management_i18n.entity.Customer;
import com.codegym.customer_management_i18n.service.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private final ICustomerService customerService;
    private final MessageSource messageSource;

    public CustomerController(ICustomerService customerService, MessageSource messageSource) {
        this.customerService = customerService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public String showCustomers(Model model){
        model.addAttribute("customers", customerService.findAll());
        return "customer/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/create";
    }

    @PostMapping("/create")
    public String addCustomer(@Valid @ModelAttribute("customer") Customer customer,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "customer/create";
        }

        customerService.save(customer);
        String message = messageSource.getMessage("message.success.added", null, LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/customers";
    }

    @GetMapping("/{id}/update")
    public String showUpdateForm(@PathVariable Long id,
                                 Model model,
                                 RedirectAttributes redirectAttributes){
        Customer customer = customerService.findById(id);

        if (customer == null) {
            String message = messageSource.getMessage("message.error.notFound", null, LocaleContextHolder.getLocale());
            redirectAttributes.addFlashAttribute("error", message);
            return "redirect:/customers";
        }

        model.addAttribute("customer", customer);
        return "customer/update";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("customer") Customer customer,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            return "customer/update";
        }

        customerService.save(customer);
        String message = messageSource.getMessage("message.success.updated", null, LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/customers";
    }

    @PostMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes){
        customerService.delete(id);
        String message = messageSource.getMessage("message.success.deleted", null, LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/customers";
    }
//    @GetMapping("/{id}/update")
//    public String showUpdateForm(Model model){
//        model.addAttribute("customer", new Customer());
//    }


}
