package com.codegym.validation_register_form.service;

import com.codegym.validation_register_form.entity.User;

public interface IUserService {
    Boolean save(User user);
    User findById(Long id);
    Boolean existsByEmail(String email);
}
