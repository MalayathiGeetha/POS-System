package com.geetha.service.impl;

import com.geetha.modal.Customer;
import com.geetha.repository.CustomerRepository;
import com.geetha.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) throws Exception {
        Customer customer1=customerRepository.findById(id).orElseThrow(()->new Exception("Customer not found"));
        customer1.setFullName(customer1.getFullName());
        customer1.setEmail(customer1.getEmail());
        customer1.setPhone(customer1.getPhone());
        return customerRepository.save(customer1);
    }

    @Override
    public void deleteCustomer(Long id) throws Exception {
        Customer customer1=customerRepository.findById(id).orElseThrow(()->new Exception("Customer not found"));
        customerRepository.delete(customer1);
    }

    @Override
    public Customer getCustomer(Long id) throws Exception {
        return customerRepository.findById(id).orElseThrow(()->new Exception("Customer not found"));

    }

    @Override
    public List<Customer> getAllCustomers() throws Exception {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> searchCustomers(String keyword) throws Exception {
        return customerRepository.findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword,keyword);
    }
}
