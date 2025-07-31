package com.bank.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.entity.CustomerEntity;
import com.bank.mapper.CustomerMapper;
import com.bank.model.CustomerDTO;
import com.bank.service.ICustomerService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@RestController
@RequestMapping("/api/customers/v1")
@Slf4j
public class CustomerController {

	
    private final ICustomerService customerService;

    private final CustomerMapper customerMapper;
    
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customer) {
    	log.info("createCustomer started customer={}", customer);
    	CustomerEntity customerEntity = customerMapper.toEntity(customer);
        return ResponseEntity.ok(customerMapper.toDto(customerService.createCustomer(customerEntity)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
    	log.info("getCustomerById started id={}", id);
        return customerService.getCustomerById(id)
                .map(customerMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerMapper.toDtoList(customerService.getAllCustomers()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customer) {
        try {
            return ResponseEntity.ok(customerMapper.toDto(customerService.updateCustomer(id, customerMapper.toEntity(customer))));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/searchByDate")
    public List<CustomerDTO> getCustomersByDescriptionAndCreationDateBetween(
            @RequestParam String description,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<CustomerEntity> customers = customerService.getCustomersByDescriptionAndCreationDateBetween(description, startDate, endDate);
        return customerMapper.toDtoList(customers);
    }
}
