package com.aop.interceptors.demo.service;


import com.aop.interceptors.demo.persistence.entity.CustomerEntity;

import java.util.List;

public interface CustomerService {

    List<CustomerEntity> getAllCustomers();
     CustomerEntity saveUser(CustomerEntity customerEntity);
}
