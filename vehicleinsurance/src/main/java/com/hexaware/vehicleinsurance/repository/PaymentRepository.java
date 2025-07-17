package com.hexaware.vehicleinsurance.repository;

import com.hexaware.vehicleinsurance.entity.Payment;
import com.hexaware.vehicleinsurance.entity.Policy;
import com.hexaware.vehicleinsurance.entity.Quote;
import com.hexaware.vehicleinsurance.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUserId(Long userId);
    List<Payment> findByPolicyId(Long policyId);
    List<Payment> findByQuote(Quote quote);
    List<Payment> findByUser(User user);
    List<Payment> findByPolicy(Policy policy);
}
