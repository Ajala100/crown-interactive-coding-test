package com.crowninteractive.customerproject.service.billingDetail;

import com.crowninteractive.customerproject.data.models.BillingDetail;
import com.crowninteractive.customerproject.exception.BillingDetailDoesNotExist;
import com.crowninteractive.customerproject.exception.BillingDetailsAlreadyExists;
import com.crowninteractive.customerproject.exception.InvalidAccountNumberException;
import com.crowninteractive.customerproject.exception.InvalidArgumentException;

public interface BillingDetailService {

    BillingDetail createBillingDetail(String accountNumber, Double tariff) throws BillingDetailsAlreadyExists, InvalidAccountNumberException;


}
