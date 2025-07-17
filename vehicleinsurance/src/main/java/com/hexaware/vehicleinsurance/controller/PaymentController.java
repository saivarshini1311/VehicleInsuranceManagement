package com.hexaware.vehicleinsurance.controller;

import com.hexaware.vehicleinsurance.dto.*;
import com.itextpdf.text.Document;
import com.hexaware.vehicleinsurance.entity.Payment;
import com.hexaware.vehicleinsurance.entity.Policy;
import com.hexaware.vehicleinsurance.entity.Quote;
import com.hexaware.vehicleinsurance.entity.User;
import com.hexaware.vehicleinsurance.repository.PaymentRepository;
import com.hexaware.vehicleinsurance.repository.PolicyRepository;
import com.hexaware.vehicleinsurance.repository.QuoteRepository;
import com.hexaware.vehicleinsurance.repository.UserRepository;
import com.hexaware.vehicleinsurance.service.PaymentService;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PolicyRepository policyRepository;
    
    @Autowired
    private QuoteRepository quoteRepository;
    
    @Autowired
    private PaymentRepository paymentRepo;



    @PostMapping("/make")
    public ResponseEntity<Payment> makePayment(@RequestBody PaymentDTO dto) {
        Payment payment = new Payment();
        payment.setAmount(dto.getAmount());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setStatus(dto.getStatus());
        payment.setTransactionId(dto.getTransactionId());
        payment.setPaymentDate(dto.getPaymentDate());

        // Fetch User and Policy from DB
        User user = userRepository.findById(dto.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        Policy policy = policyRepository.findById(dto.getPolicyId())
            .orElseThrow(() -> new RuntimeException("Policy not found"));
		Quote quote = quoteRepository.findById(dto.getQuoteId()).orElse(null);
        payment.setQuote(quote);

        payment.setUser(user);
        payment.setPolicy(policy);

        return ResponseEntity.ok(paymentService.makePayment(payment));
    }
    @GetMapping("/receipt/{paymentId}")
    public void downloadReceipt(@PathVariable Long paymentId, HttpServletResponse response) throws Exception {
        Payment payment = paymentRepo.findById(paymentId).orElseThrow();

        Document document = new Document();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=receipt_" + paymentId + ".pdf");

        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(new Paragraph("Receipt ID: " + payment.getId()));
        document.add(new Paragraph("Date: " + payment.getPaymentDate()));
        document.add(new Paragraph("Amount: ₹" + payment.getAmount()));
        document.add(new Paragraph("Method: " + payment.getPaymentMethod()));
        document.add(new Paragraph("Status: " + payment.getStatus()));
        document.add(new Paragraph("User: " + payment.getUser().getName()));
        document.close();
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Payment>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(paymentService.getPaymentsByUser(userId));
    }

    @GetMapping("/policy/{policyId}")
    public ResponseEntity<List<Payment>> getByPolicy(@PathVariable Long policyId) {
        return ResponseEntity.ok(paymentService.getPaymentsByPolicy(policyId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
