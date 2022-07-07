package com.crowninteractive.customerproject.service.customer;

import com.crowninteractive.customerproject.data.models.Customer;
import com.crowninteractive.customerproject.exception.BillingDetailsAlreadyExists;
import com.crowninteractive.customerproject.exception.CustomerAlreadyExistsException;
import com.crowninteractive.customerproject.exception.CustomerDoesNotExistException;
import com.crowninteractive.customerproject.exception.InvalidAccountNumberException;
import com.crowninteractive.customerproject.payload.request.StoreNewCustomerRequest;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(StoreNewCustomerRequest storeNewCustomerRequest) throws InvalidAccountNumberException, CustomerAlreadyExistsException, BillingDetailsAlreadyExists;
    Customer findCustomerByEmail(String email) throws CustomerDoesNotExistException;
    Customer findCustomerById(Long id) throws CustomerDoesNotExistException;
    List<Customer> getAllCustomers();
}
