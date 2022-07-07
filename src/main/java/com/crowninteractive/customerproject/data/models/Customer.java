package com.crowninteractive.customerproject.data.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "first name cannot be blank")
    private String firstName;

    @NotBlank(message = "last name cannot be blank")
    private String lastName;

    @Email
    @NotBlank(message = "Email cannot be blank")
    @Size(max = 50)
    @Column(unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private BillingDetail billingDetail;
}
