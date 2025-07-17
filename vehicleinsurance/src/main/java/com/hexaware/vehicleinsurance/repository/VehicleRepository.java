package com.hexaware.vehicleinsurance.repository;

import com.hexaware.vehicleinsurance.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByOwnerId(Long ownerId);
    int countByOwnerId(Long ownerId);
}

