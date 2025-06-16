package com.insurance.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "quote_id")
    private Quote quote;

    private LocalDate paymentDate;
    private Double amountPaid;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
