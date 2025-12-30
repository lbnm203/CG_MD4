package com.codegym.customer_management.repository;

import com.codegym.customer_management.entity.Customer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerRepository {
    private static List<Customer> customers = new ArrayList<>();

    static {
        customers.add(new Customer(1, "Nam", "nam@gmail.com", "Ha Noi"));
        customers.add(new Customer(2, "Mai", "mai@gmail.com", "Ha Noi"));
        customers.add(new Customer(3, "Nguyet", "nguyet@gmail.com", "Ha Noi"));
        customers.add(new Customer(4, "Hoang", "hoang@gmail.com", "Ha Noi"));
        customers.add(new Customer(5, "Phuc", "phuc@gmail.com", "Ha Noi"));
    }

    public List<Customer> findAll() {
        return customers;
    }

    public void save(Customer customer) {
        customer.setId(customers.get(customers.size() - 1).getId() + 1);
        customers.add(customer);
//        customers.add(customer.getId(), customer);
    }

    public Customer findById(Integer id) {
        for (Customer customer : customers) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }
        return null;
//        return customers.get(id);
    }

    public void update(int id, Customer customer) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId() == id) {
                customer.setId(id);
                customers.set(i, customer);
                return;
            }
        }
//        customers.add(id, customer);
    }

    public void remove(int id) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId() == id) {
                customers.remove(i);
                return;
            }
        }
//        customers.remove(id);
        }
        
    public List<Customer> searchByName(String nameProduct) {
        String lowerKeyword = nameProduct.toLowerCase().trim();
        List<Customer> result = new ArrayList<>();
        for (Customer customer : customers) {
            if (customer.getName().toLowerCase().contains(lowerKeyword)) {
                result.add(customer);
            }
        }
        return result;
    }
}
