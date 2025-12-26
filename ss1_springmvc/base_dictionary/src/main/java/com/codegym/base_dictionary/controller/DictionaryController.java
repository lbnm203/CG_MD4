package com.codegym.base_dictionary.controller;

import com.codegym.base_dictionary.service.DictionaryService;
import com.codegym.base_dictionary.service.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DictionaryController {
    private final DictionaryService dictionaryService;
    
    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }
    
    @GetMapping("")
    public String showDictionary() {
        return "dictionary";
    }
    
    @PostMapping("")
    public String searchDictionary(@RequestParam("word") String word, Model model) {
        String meaning = dictionaryService.searchWord(word);
        
        model.addAttribute("searchWord", word);
        
        if (meaning != null) {
            model.addAttribute("meaning", meaning);
        } else {
            model.addAttribute("notFound", true);
        }
        
        return "dictionary";
    }
}
