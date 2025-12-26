package com.codegym.base_dictionary.repository;

import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
public class DictionaryRepository {
    private final Map<String, String> dictionary = new HashMap<>();

    public DictionaryRepository() {
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

    public String findWord(String word) {
        return dictionary.get(word);
    }
}
