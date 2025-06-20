package com.hexaware.vehicleinsurance.repository;

import com.hexaware.vehicleinsurance.entity.PolicyReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PolicyReminderRepository extends JpaRepository<PolicyReminder, Long> {
    List<PolicyReminder> findByUserId(Long userId);
    List<PolicyReminder> findByPolicyId(Long policyId);
}