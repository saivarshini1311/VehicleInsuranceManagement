package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.dto.PaymentRequest;
import com.hexaware.vehicleinsurance.entity.Payment;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PaymentService {

    // Save a payment manually (used internally or admin side)
    Payment makePayment(Payment payment);

    // Pay for a quote by user (main user flow)
    ResponseEntity<String> payForQuote(Long quoteId, PaymentRequest request);

    // Retrieve payment history by user (used in user dashboard)
    List<Payment> getPaymentsByUser(Long userId);

    // Retrieve payment history by policy (admin or backend use)
    List<Payment> getPaymentsByPolicy(Long policyId);

    // Get a single payment by its ID
    Payment getPaymentById(Long id);

    // Delete a payment (admin only or for cleanup)
    void deletePayment(Long id);
}
