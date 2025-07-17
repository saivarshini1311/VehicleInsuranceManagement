package com.hexaware.vehicleinsurance.repository;

import com.hexaware.vehicleinsurance.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
    List<Claim> findByPolicyId(Long policyId);
    int countByUserId(Long userId);
    long countByStatus(String status);
    List<Claim> findByStatus(String status);
    List<Claim> findByUserIdAndStatus(Long userId, String status);
    List<Claim> findByUserIdOrderByClaimDateDesc(Long userId);
    List<Claim> findByUserId(Long userId);

}

