package com.crowninteractive.customerproject.data.repositories;

import com.crowninteractive.customerproject.data.models.BillingDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BillingDetailRepository extends JpaRepository<BillingDetail, Long> {

    Optional<BillingDetail> findBillingDetailByAccountNumber(String accountNumber);

}
