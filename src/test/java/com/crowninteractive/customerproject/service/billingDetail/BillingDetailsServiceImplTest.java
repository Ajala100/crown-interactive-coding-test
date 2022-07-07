package com.crowninteractive.customerproject.service.billingDetail;

import com.crowninteractive.customerproject.data.models.BillingDetail;
import com.crowninteractive.customerproject.data.repositories.BillingDetailRepository;
import com.crowninteractive.customerproject.exception.BillingDetailsAlreadyExists;
import com.crowninteractive.customerproject.exception.InvalidAccountNumberException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BillingDetailsServiceImplTest {

    private BillingDetailService billingDetailService;

    @Mock
    private BillingDetailRepository billingDetailRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        billingDetailService = new BillingDetailsServiceImpl(billingDetailRepository);
    }

    @Test
    void createBillingDetail() throws InvalidAccountNumberException, BillingDetailsAlreadyExists {

        //given

        String accountNumber = "1234567898";
        Double tariff = 30.00;

        BillingDetail billingDetail = BillingDetail.builder().accountNumber(accountNumber).tariff(tariff).id(2L).build();

        given(billingDetailRepository.findBillingDetailByAccountNumber(anyString())).willReturn(Optional.empty());
        when(billingDetailRepository.save(any(BillingDetail.class))).thenReturn(billingDetail);

        //when
        billingDetailService.createBillingDetail(accountNumber, tariff);

        //then
        verify(billingDetailRepository, times(1)).save(any(BillingDetail.class));
        verify(billingDetailRepository, times(1)).findBillingDetailByAccountNumber(anyString());

    }

    @Test
    @DisplayName("Test that exception is thrown when invalid account number is supplied")
    void invalidAccountNumberTest() throws InvalidAccountNumberException, BillingDetailsAlreadyExists {

        //given
        String accountNumber = "123764";
        Double tariff = 30.00;

        //when
        //then
        assertThatThrownBy(()->billingDetailService.createBillingDetail(accountNumber, tariff)).
                isInstanceOf(InvalidAccountNumberException.class).hasMessageContaining("Account number must contain 10 digits");

        verify(billingDetailRepository, never()).findBillingDetailByAccountNumber(anyString());
        verify(billingDetailRepository, never()).save(any(BillingDetail.class));

    }
//
    @Test
    @DisplayName("Test that exception is thrown when billing detail with given account number already exists")
    void noDuplicateAccountNumberTest(){

        //given
        BillingDetail billingDetail = BillingDetail.builder().accountNumber("1234567895-01").tariff(23.00).id(33L).build();

        given(billingDetailRepository.findBillingDetailByAccountNumber(anyString())).willReturn(Optional.ofNullable(billingDetail));

        //when
        //then
        assertThatThrownBy(()-> billingDetailService.createBillingDetail("1234567895", 30.00))
                .isInstanceOf(BillingDetailsAlreadyExists.class).hasMessageContaining("Billing detail with account number " +
                        "already exists");
        verify(billingDetailRepository, never()).save(any(BillingDetail.class));
    }

}