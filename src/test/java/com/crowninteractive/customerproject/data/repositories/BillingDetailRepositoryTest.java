package com.crowninteractive.customerproject.data.repositories;

import com.crowninteractive.customerproject.data.models.BillingDetail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BillingDetailRepositoryTest {

    @Autowired
    BillingDetailRepository billingDetailRepository;

    @Test
    @DisplayName("Test that billing detail can be found by account number")
    void findBillingDetailByEmail(){

        //given
        String accountNumber = "1234567899-01";
        Double tariff = 20.00;
        BillingDetail billingDetail = BillingDetail.builder()
                .tariff(tariff)
                .accountNumber(accountNumber).
                build();
        billingDetailRepository.save(billingDetail);

        //when
       BillingDetail retrievedBillingDetail =  billingDetailRepository.findBillingDetailByAccountNumber(accountNumber).
               orElse(null);

       //then
        assertThat(retrievedBillingDetail).isNotNull();
        assertThat(retrievedBillingDetail.getId()).isNotNull();
        assertThat(retrievedBillingDetail.getAccountNumber()).isEqualTo(accountNumber);
        assertThat(retrievedBillingDetail.getTariff()).isEqualTo(tariff);

    }
}
