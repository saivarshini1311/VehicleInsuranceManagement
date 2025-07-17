package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.dto.PaymentRequest;
import com.hexaware.vehicleinsurance.entity.Payment;
import com.hexaware.vehicleinsurance.entity.Policy;
import com.hexaware.vehicleinsurance.entity.Quote;
import com.hexaware.vehicleinsurance.entity.User;
import com.hexaware.vehicleinsurance.repository.PaymentRepository;
import com.hexaware.vehicleinsurance.repository.PolicyRepository;
import com.hexaware.vehicleinsurance.repository.QuoteRepository;
import com.hexaware.vehicleinsurance.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private QuoteRepository quoteRepository;

    @Override
    public Payment makePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public ResponseEntity<String> payForQuote(Long quoteId, PaymentRequest request) {
        Quote quote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new RuntimeException("Quote not found"));

        if (!quote.getStatus().equals("PENDING_PAYMENT")) {
            return ResponseEntity.badRequest().body("Quote already paid");
        }

        User user = quote.getProposal().getUser();

        Payment payment = new Payment();
        payment.setQuote(quote);
        payment.setAmount(quote.getPremiumAmount());
        payment.setPaymentMethod(request.getPaymentMethod()); // Note: changed from .setMethod()
        payment.setPaymentDate(LocalDate.now());
        payment.setStatus("PAID");
        payment.setUser(user);

        paymentRepository.save(payment);

        quote.setStatus("PAID");
        quoteRepository.save(quote);

        return ResponseEntity.ok("Payment Successful");
    }

    @Override
    public List<Payment> getPaymentsByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return paymentRepository.findByUser(user);
    }

    @Override
    public List<Payment> getPaymentsByPolicy(Long policyId) {
        Policy policy = policyRepository.findById(policyId).orElseThrow();
        return paymentRepository.findByPolicy(policy);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
