package com.insurance.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policyId;

    private String policyName;
    private String description;
    private Double basePremium;
    private String addOnFeatures;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
}

