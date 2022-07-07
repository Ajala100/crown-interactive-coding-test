package com.crowninteractive.customerproject.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponsePayload {

    private boolean isSuccessful;
    private String message;
    private int responseCode;
    private LocalDate timeStamp = LocalDate.now();
}
