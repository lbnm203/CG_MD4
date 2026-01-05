package com.codegym.customer_management_orm.entity;

import javax.persistence.*;

@Entity(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name_student", length = 100, columnDefinition = "VARCHAR(100)")
    private String name;
    @Column(name = "email", length = 100, columnDefinition = "VARCHAR(100)")
    private String email;
    @Column(name = "address", length = 255, columnDefinition = "VARCHAR(255)")
    private String address;
    @Column(name = "age")
    private Integer age;


    public Customer() {

    }

    public Customer(Integer id, String name, String email, String address, Integer age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
