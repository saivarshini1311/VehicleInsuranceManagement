package com.insurance.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long claimId;

    @ManyToOne
    @JoinColumn(name = "policy_id")
    private Policy policy;

    private LocalDate claimDate;
    private String description;

    @Enumerated(EnumType.STRING)
    private ClaimStatus status;
}

