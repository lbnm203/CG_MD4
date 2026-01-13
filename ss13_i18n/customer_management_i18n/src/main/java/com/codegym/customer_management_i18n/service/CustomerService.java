package com.codegym.customer_management_i18n.service;

import com.codegym.customer_management_i18n.entity.Customer;
import com.codegym.customer_management_i18n.repository.ICustomerRepostory;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService{
    private final ICustomerRepostory customerRepostory;

    public CustomerService(ICustomerRepostory customerRepostory) {
        this.customerRepostory = customerRepostory;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepostory.findAll();
    }

    @Override
    public Customer findById(Long id) {
        return customerRepostory.findById(id).orElseThrow(() -> new NoResultException("Customer not found with id: " + id + ""));
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepostory.save(customer);
    }

    @Override
    public Boolean delete(Long id) {
        if (customerRepostory.existsById(id)) {
            customerRepostory.deleteById(id);
            return true;
        }
        return false;
    }
}
