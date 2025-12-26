package com.codegym.base_dictionary.service;

import com.codegym.base_dictionary.repository.DictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DictionaryService implements IDictionaryService {
    private final DictionaryRepository dictionaryRepository;
    
    @Autowired
    public DictionaryService(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    public String searchWord(String word) {
        if (word == null || word.trim().isEmpty()) {
            return null;
        }

        String normalizedWord = word.trim().toLowerCase();
        return dictionaryRepository.findWord(normalizedWord);
    }
}
