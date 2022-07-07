package com.crowninteractive.customerproject.data.repositories;

import com.crowninteractive.customerproject.data.models.BillingDetail;
import com.crowninteractive.customerproject.data.models.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BillingDetailRepository billingDetailRepository;

    @Test
    @DisplayName("Test that customer can be found via email")
    void getCustomerByEmail(){

        //given
        BillingDetail billingDetail = BillingDetail.builder().accountNumber("1234567899-01").tariff(10.0).build();

        BillingDetail savedBillingDetail = billingDetailRepository.save(billingDetail);

        String email = "ahmadajala100@gmail.com";
        String firstName = "Ahmad";
        Customer customer = Customer.builder()
                .lastName("Ajala")
                .firstName(firstName)
                .email(email)
                .billingDetail(savedBillingDetail)
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        //when
        Customer retrievedCustomer = customerRepository.findCustomerByEmail(email).orElse(null);

        //then
        assertThat(retrievedCustomer).isNotNull();
        assertThat(retrievedCustomer.getId()).isNotNull();
        assertThat(retrievedCustomer.getEmail()).isEqualTo(email);
        assertThat(retrievedCustomer.getBillingDetail()).isNotNull();
        assertThat(retrievedCustomer).isEqualTo(savedCustomer);
    }

}
