package com.crowninteractive.customerproject.payload.response;

import com.crowninteractive.customerproject.data.models.Customer;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class CustomerResponsePayload extends BaseResponsePayload{

    private Customer customer;

    public CustomerResponsePayload(boolean isSuccessful, String message, int responseCode, LocalDate timeStamp, Customer storedCustomer){
        super(isSuccessful, message, responseCode, timeStamp);
        this.customer = storedCustomer;
    }

}
