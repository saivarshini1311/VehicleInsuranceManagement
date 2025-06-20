package com.hexaware.vehicleinsurance.repository;

import com.hexaware.vehicleinsurance.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUserId(Long userId);
    List<Payment> findByPolicyId(Long policyId);
}
