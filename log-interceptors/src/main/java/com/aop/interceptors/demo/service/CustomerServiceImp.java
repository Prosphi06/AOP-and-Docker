package com.aop.interceptors.demo.service;

import com.aop.interceptors.demo.persistence.entity.CustomerEntity;
import com.aop.interceptors.demo.persistence.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImp implements CustomerService{

    @Autowired
    CustomerRepo repo;

    @Override
   public List<CustomerEntity> getAllCustomers(){
        return repo.findAll();
    }
    @Override
    public CustomerEntity saveUser(CustomerEntity customerEntity){
        return repo.save(customerEntity);
    }
}
