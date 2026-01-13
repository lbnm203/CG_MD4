package com.codegym.customer_management_i18n.service;

import com.codegym.customer_management_i18n.entity.Customer;

import java.util.List;

public interface ICustomerService {
    List<Customer> findAll();
    Customer findById(Long id);
    Customer save(Customer customer);
    Boolean delete(Long id);
}
