package com.crowninteractive.customerproject.web;

import com.crowninteractive.customerproject.data.models.Customer;
import com.crowninteractive.customerproject.exception.BillingDetailsAlreadyExists;
import com.crowninteractive.customerproject.exception.CustomerAlreadyExistsException;
import com.crowninteractive.customerproject.exception.CustomerDoesNotExistException;
import com.crowninteractive.customerproject.exception.InvalidAccountNumberException;
import com.crowninteractive.customerproject.payload.request.StoreNewCustomerRequest;
import com.crowninteractive.customerproject.payload.response.BaseResponsePayload;
import com.crowninteractive.customerproject.payload.response.CustomerResponsePayload;
import com.crowninteractive.customerproject.service.customer.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<?> storeNewCustomer(@RequestBody StoreNewCustomerRequest storeNewCustomerRequest){

        try{
           Customer storedCustomer = customerService.createCustomer(storeNewCustomerRequest);

           return new ResponseEntity<>(new CustomerResponsePayload(true, "Successfully stored new customer",
                   HttpStatus.CREATED.value(), LocalDate.now(), storedCustomer), HttpStatus.CREATED );

        } catch (InvalidAccountNumberException e) {

            return new ResponseEntity<>(new BaseResponsePayload(false, "Account number must have 10 digits",
                    HttpStatus.BAD_REQUEST.value(), LocalDate.now()), HttpStatus.BAD_REQUEST);

        } catch (CustomerAlreadyExistsException e) {

            return new ResponseEntity<>(new BaseResponsePayload(false, "Customer with email already exists",
                    HttpStatus.BAD_REQUEST.value(), LocalDate.now()), HttpStatus.BAD_REQUEST);

        } catch (BillingDetailsAlreadyExists e) {

            return new ResponseEntity<>(new BaseResponsePayload(false, "Customer with account number" +
                    " already exists", HttpStatus.BAD_REQUEST.value(), LocalDate.now()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCustomerById(@PathVariable Long id){
        try{

            Customer retrievedUser = customerService.findCustomerById(id);

            return new ResponseEntity<>(new CustomerResponsePayload(true, "Successful", HttpStatus.OK.value(),
                    LocalDate.now(), retrievedUser), HttpStatus.OK);

        } catch (CustomerDoesNotExistException e) {

            return new ResponseEntity<>(new BaseResponsePayload(false, "Customer does not exist",
                    HttpStatus.NOT_FOUND.value(), LocalDate.now()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> findCustomerByEmail(@RequestParam String email){

        try{
            Customer retrievedCustomer = customerService.findCustomerByEmail(email);

            return new ResponseEntity<>(new CustomerResponsePayload(true, "Successful", HttpStatus.OK.value(),
                    LocalDate.now(), retrievedCustomer), HttpStatus.OK);

        } catch (CustomerDoesNotExistException e) {

            return new ResponseEntity<>(new BaseResponsePayload(false, "Customer does not exist",
                    HttpStatus.NOT_FOUND.value(), LocalDate.now()), HttpStatus.NOT_FOUND);
        }

    }

}
