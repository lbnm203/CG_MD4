package com.codegym.th_postman.dto;

import com.codegym.th_postman.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;

    public CategoryDTO() {

    }

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
