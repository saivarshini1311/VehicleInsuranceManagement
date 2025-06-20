package com.hexaware.vehicleinsurance.repository;

import com.hexaware.vehicleinsurance.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
    Policy findByVehicleId(Long vehicleId);
    List<Policy> findByUserId(Long userId);
}
