package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.dto.DashboardStatsDTO;
import com.hexaware.vehicleinsurance.repository.VehicleRepository;
import com.hexaware.vehicleinsurance.repository.PolicyRepository;
import com.hexaware.vehicleinsurance.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private VehicleRepository vehicleRepo;

    @Autowired
    private PolicyRepository policyRepo;

    @Autowired
    private ClaimRepository claimRepo;

    @Override
    public DashboardStatsDTO getStatsForUser(Long userId) {
        DashboardStatsDTO stats = new DashboardStatsDTO();
        
        stats.setVehicles(vehicleRepo.countByOwnerId(userId));
        stats.setPolicies(policyRepo.countActivePoliciesByUserId(userId));
        stats.setClaims(claimRepo.countByUserId(userId));

        // ✅ Fix this line by converting LocalDate to String
        LocalDate nextExpiry = policyRepo.findNextPolicyExpiryByUserId(userId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        stats.setNextExpiry(nextExpiry != null ? nextExpiry.format(formatter) : "-");

        return stats;
    }

    }

