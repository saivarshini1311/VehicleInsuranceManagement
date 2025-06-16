package com.insurance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String fullName;

    @Column(unique = true)
    private String email;

    private String password; // This stores hashed password

    private String address;

    private String aadhaarNumber;

    private String panNumber;

    private String dob; // You can change to LocalDate if preferred

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime createdAt;

    // Relationships
    @OneToMany(mappedBy = "user")
    private List<Vehicle> vehicles;

    @OneToMany(mappedBy = "user")
    private List<Proposal> proposals;

    @OneToMany(mappedBy = "createdBy")
    private List<Policy> createdPolicies;
}

