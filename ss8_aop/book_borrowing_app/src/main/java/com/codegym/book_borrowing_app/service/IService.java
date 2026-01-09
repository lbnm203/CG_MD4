package com.codegym.book_borrowing_app.service;

import java.util.List;

public interface IService<T> {
    List<T> findAll();
    T findById(Long id);
    Boolean save(T t);
    Boolean delete(Long id);
}
