package com.codegym.customer_management_orm.service;

import com.codegym.customer_management_orm.entity.Customer;

import java.util.List;

public interface ICustomerService {
    List<Customer> findAll();
    Boolean save(Customer customer);
    Customer findById(Integer id);
    void update(int id, Customer customer);
    void remove(int id);
    List<Customer> searchByName(String keyword);
    List<Customer> searchByRangeAge(int minAge, int maxAge);
}
