package com.codegym.customer_management_i18n.repository;

import com.codegym.customer_management_i18n.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRepostory extends JpaRepository<Customer, Long> {
}
