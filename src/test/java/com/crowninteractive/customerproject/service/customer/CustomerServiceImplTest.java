package com.crowninteractive.customerproject.service.customer;

import com.crowninteractive.customerproject.data.models.BillingDetail;
import com.crowninteractive.customerproject.data.models.Customer;
import com.crowninteractive.customerproject.data.repositories.CustomerRepository;
import com.crowninteractive.customerproject.exception.BillingDetailsAlreadyExists;
import com.crowninteractive.customerproject.exception.CustomerAlreadyExistsException;
import com.crowninteractive.customerproject.exception.CustomerDoesNotExistException;
import com.crowninteractive.customerproject.exception.InvalidAccountNumberException;
import com.crowninteractive.customerproject.payload.request.StoreNewCustomerRequest;
import com.crowninteractive.customerproject.service.billingDetail.BillingDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BillingDetailService billingDetailService;

    Customer customer;

    StoreNewCustomerRequest storeNewCustomerRequest;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        customerService = new CustomerServiceImpl(customerRepository, billingDetailService);

        storeNewCustomerRequest = StoreNewCustomerRequest.builder()
                .accountNumber("2345678901").email("bola@gmail.com").firstName("ahmad").lastName("bolakale")
                .tariff(23.00).build();

        BillingDetail billingDetail = BillingDetail.builder().accountNumber("1234567890-01").tariff(23.0).id(2L).build();

        customer = Customer.builder().billingDetail(billingDetail).
                email(storeNewCustomerRequest.getEmail())
                .firstName(storeNewCustomerRequest.getFirstName())
                .lastName(storeNewCustomerRequest.getLastName())
                .id(33L).build();
    }

    @Test
    @DisplayName("Test that customer can be created")
    void createCustomer() throws InvalidAccountNumberException, BillingDetailsAlreadyExists, CustomerAlreadyExistsException {

        //given
        given(customerRepository.save(any(Customer.class))).willReturn(customer);

        //when
        Customer createdCustomer = customerService.createCustomer(storeNewCustomerRequest);

        //then
        verify(customerRepository, times(1)).findCustomerByEmail(anyString());
        verify(billingDetailService, times(1)).createBillingDetail(anyString(), anyDouble());
        verify(customerRepository, times(1)).save(any(Customer.class));
        assertThat(createdCustomer).isNotNull();
        assertThat(createdCustomer.getId()).isNotNull();
        assertThat(createdCustomer.getEmail()).isEqualTo(storeNewCustomerRequest.getEmail());
    }

    @Test
    @DisplayName("Test that customer can be found by email")
    void findCustomerByEmail() throws CustomerDoesNotExistException {

        //given
        given(customerRepository.findCustomerByEmail(anyString())).willReturn(Optional.ofNullable(customer));

        //when
        Customer customer = customerService.findCustomerByEmail(storeNewCustomerRequest.getEmail());

        //then
        verify(customerRepository, times(1)).findCustomerByEmail(anyString());
        assertThat(customer).isNotNull();
    }

    @Test
    void findCustomerById() throws CustomerDoesNotExistException {

        //given

        given(customerRepository.findById(anyLong())).willReturn(Optional.ofNullable(customer));

        //when

        Customer customer = customerService.findCustomerById(anyLong());

        //then

        verify(customerRepository, times(1)).findById(anyLong());
        assertThat(customer).isNotNull();
    }

    @Test
    void getAllCustomers() {
        //given

        List<Customer> listOfCustomers = new ArrayList<>();
        listOfCustomers.add(customer);
        given(customerRepository.findAll()).willReturn(listOfCustomers);

        //when
        List<Customer> customers = customerService.getAllCustomers();

        //then

        verify(customerRepository, times(1)).findAll();
        assertThat(customers).isNotNull();
    }

    @Test
    @DisplayName("Test that exception is thrown when customer with email already exists")
    void noDuplicateEmailTest() throws InvalidAccountNumberException, BillingDetailsAlreadyExists, CustomerAlreadyExistsException {

        //given

        given(customerRepository.findCustomerByEmail(anyString())).willReturn(Optional.ofNullable(customer));

        //when
        //then
        assertThatThrownBy(()-> customerService.createCustomer(storeNewCustomerRequest)).isInstanceOf(CustomerAlreadyExistsException.class)
                .hasMessageContaining("Customer with given email already exists");
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    @DisplayName("Test that exception is thrown when user with given email does not exist")
    void noInvalidEmailTest(){
        //given
        //when
        //then
        assertThatThrownBy(()-> customerService.findCustomerByEmail(anyString())).isInstanceOf(CustomerDoesNotExistException.class)
                .hasMessageContaining("Customer does not exist");
    }
    @Test
    @DisplayName("Test that exception is thrown when user with given Id does not exist")
    void noInvalidIdTest(){
        //given
        //when
        //then
        assertThatThrownBy(()-> customerService.findCustomerById(anyLong())).isInstanceOf(CustomerDoesNotExistException.class)
                .hasMessageContaining("Customer does not exist");
    }
}