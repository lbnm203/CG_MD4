package com.codegym.customer_management_orm.service;

import com.codegym.customer_management_orm.entity.Customer;
import com.codegym.customer_management_orm.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService{
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Boolean save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer findById(Integer id) {
        return customerRepository.findById(id);
    }

    @Override
    public void update(int id, Customer customer) {
        customerRepository.update(id, customer);
    }

    @Override
    public void remove(int id) {
        customerRepository.remove(id);
    }

    @Override
    public List<Customer> searchByName(String keyword) {
        return customerRepository.searchByName(keyword);
    }

    @Override
    public List<Customer> searchByRangeAge(int minAge, int maxAge) {
        return customerRepository.searchByRangeAge(minAge, maxAge);
    }
}
