package com.crowninteractive.customerproject.service.customer;

import com.crowninteractive.customerproject.data.models.BillingDetail;
import com.crowninteractive.customerproject.data.models.Customer;
import com.crowninteractive.customerproject.data.repositories.CustomerRepository;
import com.crowninteractive.customerproject.exception.*;
import com.crowninteractive.customerproject.payload.request.StoreNewCustomerRequest;
import com.crowninteractive.customerproject.service.billingDetail.BillingDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;

    private final BillingDetailService billingDetailService;

    @Override
    public Customer createCustomer(StoreNewCustomerRequest storeNewCustomerRequest) throws InvalidAccountNumberException, CustomerAlreadyExistsException, BillingDetailsAlreadyExists {

        if(storeNewCustomerRequest == null) throw new IllegalArgumentException("request cannot be null");

        if(customerRepository.findCustomerByEmail(storeNewCustomerRequest.getEmail()).isPresent()) throw new
                CustomerAlreadyExistsException("Customer with given email already exists");

        BillingDetail billingDetail = billingDetailService.createBillingDetail(storeNewCustomerRequest.getAccountNumber(),
                storeNewCustomerRequest.getTariff());

        Customer customer = Customer.builder()
                .billingDetail(billingDetail)
                .email(storeNewCustomerRequest.getEmail())
                .firstName(storeNewCustomerRequest.getFirstName())
                .lastName(storeNewCustomerRequest.getLastName())
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        return savedCustomer;
    }

    @Override
    public Customer findCustomerByEmail(String email) throws CustomerDoesNotExistException {

        return customerRepository.findCustomerByEmail(email).orElseThrow(()-> new CustomerDoesNotExistException("Customer" +
                " does not exist"));
    }

    @Override
    public Customer findCustomerById(Long id) throws CustomerDoesNotExistException {

        return customerRepository.findById(id).orElseThrow(()-> new CustomerDoesNotExistException("Customer does not exist"));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
