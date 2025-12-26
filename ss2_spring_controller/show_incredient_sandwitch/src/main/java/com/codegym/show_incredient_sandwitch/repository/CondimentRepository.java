package com.codegym.show_incredient_sandwitch.repository;

import org.springframework.stereotype.Repository;

@Repository
public class CondimentRepository {
    public String[] save(String[] condiments) {
        if (condiments == null || condiments.length == 0) {
            return new String[0];
        }
        return condiments;
    }
}
