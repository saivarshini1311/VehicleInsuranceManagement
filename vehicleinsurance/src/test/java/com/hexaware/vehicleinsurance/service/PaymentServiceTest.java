package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.entity.Payment;
import com.hexaware.vehicleinsurance.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void testMakePayment() {
        Payment payment = new Payment();
        payment.setAmount(2000.0);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment result = paymentService.makePayment(payment);
        assertEquals(2000.0, result.getAmount());
    }

    @Test
    void testGetByUserId() {
        when(paymentRepository.findByUserId(1L)).thenReturn(List.of(new Payment(), new Payment()));
        List<Payment> list = paymentService.getPaymentsByUser(1L);
        assertEquals(2, list.size());
    }

    @Test
    void testGetByPolicyId() {
        when(paymentRepository.findByPolicyId(10L)).thenReturn(List.of(new Payment()));
        List<Payment> list = paymentService.getPaymentsByPolicy(10L);
        assertEquals(1, list.size());
    }

    @Test
    void testGetById() {
        Payment payment = new Payment();
        payment.setId(1L);
        when(paymentRepository.findById(1L)).thenReturn(java.util.Optional.of(payment));
        Payment result = paymentService.getPaymentById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void testDelete() {
        doNothing().when(paymentRepository).deleteById(1L);
        paymentService.deletePayment(1L);
        verify(paymentRepository, times(1)).deleteById(1L);
    }
}

