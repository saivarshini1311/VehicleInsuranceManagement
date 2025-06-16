package com.insurance.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    private String registrationNumber;
    private int manufactureYear;
    private String model;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
