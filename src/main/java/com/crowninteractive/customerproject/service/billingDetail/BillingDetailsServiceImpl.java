package com.crowninteractive.customerproject.service.billingDetail;

import com.crowninteractive.customerproject.data.models.BillingDetail;
import com.crowninteractive.customerproject.data.repositories.BillingDetailRepository;
import com.crowninteractive.customerproject.exception.BillingDetailsAlreadyExists;
import com.crowninteractive.customerproject.exception.InvalidAccountNumberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillingDetailsServiceImpl implements BillingDetailService {

    private final BillingDetailRepository billingDetailRepository;

    @Override
    public BillingDetail createBillingDetail(String accountNumber, Double tariff) throws BillingDetailsAlreadyExists, InvalidAccountNumberException {

        if(accountNumber == null) throw new IllegalArgumentException("Account number cannot be null");

        if(accountNumber.length() != 10) throw new
                InvalidAccountNumberException("Account number must contain 10 digits");

        String accountNo = accountNumber + "-01";

        if(tariff == null) throw new IllegalArgumentException("Tariff cannot be null");

        if(billingDetailRepository.findBillingDetailByAccountNumber(accountNo).isPresent()) throw new
                BillingDetailsAlreadyExists("Billing detail with account number already exists");

        BillingDetail billingDetail = BillingDetail.builder()
                .accountNumber(accountNo)
                .tariff(tariff).build();

        BillingDetail savedBillingDetail = billingDetailRepository.save(billingDetail);

        return savedBillingDetail;
    }


}
