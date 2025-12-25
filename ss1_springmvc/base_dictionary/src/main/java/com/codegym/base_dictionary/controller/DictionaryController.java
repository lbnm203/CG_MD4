package com.codegym.base_dictionary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class DictionaryController {
    
    // Từ điển Anh - Việt
    private static final Map<String, String> dictionary = new HashMap<>();
    
    static {
        dictionary.put("hello", "xin chào");
        dictionary.put("world", "thế giới");
        dictionary.put("computer", "máy tính");
        dictionary.put("book", "sách");
        dictionary.put("pen", "bút");
        dictionary.put("table", "bàn");
        dictionary.put("chair", "ghế");
        dictionary.put("student", "học sinh");
        dictionary.put("teacher", "giáo viên");
        dictionary.put("school", "trường học");
        dictionary.put("house", "ngôi nhà");
        dictionary.put("car", "xe hơi");
        dictionary.put("phone", "điện thoại");
        dictionary.put("water", "nước");
        dictionary.put("food", "thức ăn");
        dictionary.put("friend", "bạn bè");
        dictionary.put("family", "gia đình");
        dictionary.put("love", "tình yêu");
        dictionary.put("life", "cuộc sống");
        dictionary.put("time", "thời gian");
    }
    
    @GetMapping("")
    public String showDictionary() {
        return "dictionary";
    }
    
    @PostMapping("")
    public String searchDictionary(@RequestParam("word") String word, Model model) {
        String searchWord = word.trim().toLowerCase();
        String meaning = dictionary.get(searchWord);

        model.addAttribute("searchWord", word);
        
        if (meaning != null) {
            model.addAttribute("meaning", meaning);
        } else {
            model.addAttribute("notFound", true);
        }
        
        return "dictionary";
    }
}
