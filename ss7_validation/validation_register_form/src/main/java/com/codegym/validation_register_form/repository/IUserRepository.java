package com.codegym.validation_register_form.repository;

import com.codegym.validation_register_form.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
}
