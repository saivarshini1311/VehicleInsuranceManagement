package com.insurance.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quoteId;

    @OneToOne
    @JoinColumn(name = "proposal_id")
    private Proposal proposal;

    private Double premiumAmount;
    private LocalDateTime generatedAt;
}

