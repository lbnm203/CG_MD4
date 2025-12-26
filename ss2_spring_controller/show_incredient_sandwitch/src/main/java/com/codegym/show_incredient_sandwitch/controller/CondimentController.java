package com.codegym.show_incredient_sandwitch.controller;


import com.codegym.show_incredient_sandwitch.service.ICondimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("")
public class CondimentController {
    private final ICondimentService condimentService;

    @Autowired
    public CondimentController(ICondimentService condimentService) {
        this.condimentService = condimentService;
    }

    @GetMapping("")
    public String ingredient() {
        return "ingredient";
    }

    @PostMapping("/ingredient")
    public String save(@RequestParam(value = "condiment", required = false) String[] condiment, Model model) {
        String[] condiments = condimentService.save(condiment);
        model.addAttribute("condiments", condiments);
        return "result";
    }
}
