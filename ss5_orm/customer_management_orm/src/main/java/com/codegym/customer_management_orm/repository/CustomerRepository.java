package com.codegym.customer_management_orm.repository;

import com.codegym.customer_management_orm.entity.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerRepository {
    private static List<Customer> customers = new ArrayList<>();

    static {
        customers.add(new Customer(1, "Nam", "nam@gmail.com", "Ha Noi", 18));
        customers.add(new Customer(2, "Mai", "mai@gmail.com", "Ha Noi", 18));
        customers.add(new Customer(3, "Nguyet", "nguyet@gmail.com", "Ha Noi", 19));
        customers.add(new Customer(4, "Hoang", "hoang@gmail.com", "Ha Noi", 22));
        customers.add(new Customer(5, "Phuc", "phuc@gmail.com", "Ha Noi", 20));
    }

    public List<Customer> findAll() {
        List<Customer> customers = BaseRepository.entityManager.createQuery("SELECT c FROM customers c", Customer.class).getResultList();
        return customers;
    }

    public Boolean save(Customer customer) {
        EntityTransaction transaction = BaseRepository.entityManager.getTransaction();
        try {
            transaction.begin();
            BaseRepository.entityManager.persist(customer);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Customer findById(Integer id) {
        Customer customer = BaseRepository.entityManager.find(Customer.class, id);
        return customer;
    }

    public void update(int id, Customer customer) {
        EntityTransaction transaction = BaseRepository.entityManager.getTransaction();
        try {
            transaction.begin();
            customer.setId(id);
            BaseRepository.entityManager.merge(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void remove(int id) {
        EntityTransaction transaction = BaseRepository.entityManager.getTransaction();
        try {
            transaction.begin();
            Customer customer = BaseRepository.entityManager.find(Customer.class, id);
            BaseRepository.entityManager.remove(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
        
    public List<Customer> searchByName(String nameProduct) {
        String jpql = "SELECT c FROM customers c WHERE LOWER(c.name) LIKE :keyword";
        return BaseRepository.entityManager
                .createQuery(jpql, Customer.class)
                .setParameter("keyword", "%" + nameProduct.toLowerCase().trim() + "%")
                .getResultList();
    }

    public List<Customer> searchByRangeAge(int minAge, int maxAge) {
        String jpql = "SELECT c FROM customers c WHERE c.age BETWEEN :minAge AND :maxAge";

        return BaseRepository.entityManager
                .createQuery(jpql, Customer.class)
                .setParameter("minAge", minAge)
                .setParameter("maxAge", maxAge)
                .getResultList();
    }
}
