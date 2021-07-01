package com.aop.interceptors.demo.web;

import com.aop.interceptors.demo.exception.ResourceNotFoundException;
import com.aop.interceptors.demo.persistence.entity.CustomerEntity;
import com.aop.interceptors.demo.persistence.repository.CustomerRepo;
import com.aop.interceptors.demo.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api",
        produces = {
                APPLICATION_JSON_VALUE,
        })
@Tag(name = "Advise Customer  API ", description = "The Backend API For Customer Data Management")

public class CustomerController {

    @Autowired
    private CustomerRepo customerRepo;

    final CustomerService service;

    @Operation(summary = "Add New Customer")
    @PostMapping(value = "/customer")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerEntity customer){
        return ResponseEntity.ok().body(service.saveUser(customer));

    }

    @Operation(summary = "Get All Customer")
    @GetMapping(value = "/customer")
    public List<CustomerEntity> getAllCustomers() {return service.getAllCustomers();}

    @GetMapping("/customer/{id}")
    public ResponseEntity<CustomerEntity> getCustomerById(@PathVariable(value = "id") Long customerId)
            throws ResourceNotFoundException {
        CustomerEntity customerEntity = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));
        return ResponseEntity.ok().body(customerEntity);
    }


    @PutMapping(value = "/customer/{id}")
    public ResponseEntity<CustomerEntity> updateCustomer(@PathVariable(value = "id") Long customerId,
                                                   @Valid @RequestBody CustomerEntity customerDetails) throws ResourceNotFoundException {
        CustomerEntity customerEntity = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));

        customerEntity.setFirstName(customerDetails.getFirstName());
        customerEntity.setLastName(customerDetails.getLastName());
        customerEntity.setEmailId(customerDetails.getEmailId());
        customerEntity.setPhone(customerDetails.getPhone());
        customerEntity.setBirthdate(customerDetails.getBirthdate());
        customerEntity.setWhatsAppNo(customerDetails.getWhatsAppNo());
        final CustomerEntity updateCustomer = customerRepo.save(customerEntity);
        return ResponseEntity.ok(updateCustomer);
    }

    @DeleteMapping(value = "/customer/{id}")
    public Map<String, Boolean> deleteCustomer(@PathVariable(value = "id") Long customerId)
            throws ResourceNotFoundException {
        CustomerEntity customerEntity =customerRepo.getById(customerId);
              //  .orElseThrow(() -> new ResourceNotFoundException(" Customer not found for this id :: " + customerId));

        customerRepo.delete(customerEntity);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
