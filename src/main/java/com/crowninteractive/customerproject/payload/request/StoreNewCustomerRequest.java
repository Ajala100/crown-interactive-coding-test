package com.crowninteractive.customerproject.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@Setter
@Builder
public class StoreNewCustomerRequest {

    @NotBlank(message = "first name cannot be null")
    private String firstName;

    @NotBlank(message = "last name cannot be blank")
    private String lastName;

    @Email
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "account number cannot be blank")
    @Size(min = 10, max = 10)
    private String accountNumber;

    @NotBlank(message = "Tariff cannot be blank")
    private Double tariff;
}
