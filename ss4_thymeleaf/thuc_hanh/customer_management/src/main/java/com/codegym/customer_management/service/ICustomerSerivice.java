package com.codegym.customer_management.service;

import com.codegym.customer_management.entity.Customer;

import java.util.List;

public interface ICustomerSerivice {
    List<Customer> findAll();
    void save(Customer customer);
    Customer findById(Integer id);
    void update(int id, Customer customer);
    void remove(int id);
    List<Customer> searchByName(String keyword);
}
