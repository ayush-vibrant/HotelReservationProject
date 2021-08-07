package com.company.service;

import com.company.exception.CustomerNotFoundException;
import com.company.model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    private static final CustomerService SINGLETON = new CustomerService();
    private static Map<String, Customer> customers = new HashMap<>();

    private CustomerService() {
    }

    public static CustomerService getInstance() {
        return SINGLETON;
    }

    public static void addCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName, email);
        if(customers.containsKey(email))
            System.out.println("We already have an account with email: " + email);
        else
            customers.put(email, customer);
    }

    public static Customer getCustomer(String email) throws CustomerNotFoundException {
        Customer customer = customers.get(email);
        if(customer != null)
            return customer;
        else
            throw new CustomerNotFoundException("Customer not found with email: " + email);
    }

    public static Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
