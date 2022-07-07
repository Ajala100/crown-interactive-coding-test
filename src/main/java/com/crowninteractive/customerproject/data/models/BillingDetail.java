package com.crowninteractive.customerproject.data.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BillingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 13, max = 13)
    @NotBlank
    @Column(unique = true)
    private String accountNumber;

    @NotNull
    private Double tariff;

}
