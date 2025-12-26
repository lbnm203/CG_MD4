package com.codegym.show_incredient_sandwitch.service;

import com.codegym.show_incredient_sandwitch.repository.CondimentRepository;
import org.springframework.stereotype.Service;

@Service
public class CondimentService implements ICondimentService{
    private final CondimentRepository condimentRepository;

    public CondimentService(CondimentRepository condimentRepository) {
        this.condimentRepository = condimentRepository;
    }

    @Override
    public String[] save(String[] condiment) {
        return condimentRepository.save(condiment);
    }
}
