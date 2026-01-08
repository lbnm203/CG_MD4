package com.codegym.validation_register_form.service;

import com.codegym.validation_register_form.entity.User;
import com.codegym.validation_register_form.repository.IUserRepository;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Boolean save(User user) {
        if (user.getId() == null) {
            userRepository.save(user);
            return true;
        } else {
            if (userRepository.existsById(user.getId())) {
                return false;
            } else {
                userRepository.save(user);
                return true;
            }
        }
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoResultException("Not found user with id: " + id + ""));
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


}
