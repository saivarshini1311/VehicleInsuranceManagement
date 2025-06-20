package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.entity.PolicyReminder;
import com.hexaware.vehicleinsurance.repository.PolicyReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PolicyReminderService {

    @Autowired
    private PolicyReminderRepository policyReminderRepository;

    public PolicyReminder createReminder(PolicyReminder reminder) {
        return policyReminderRepository.save(reminder);
    }

    public List<PolicyReminder> getRemindersByUser(Long userId) {
        return policyReminderRepository.findByUserId(userId);
    }

    public List<PolicyReminder> getRemindersByPolicy(Long policyId) {
        return policyReminderRepository.findByPolicyId(policyId);
    }

    public PolicyReminder getReminderById(Long id) {
        return policyReminderRepository.findById(id).orElse(null);
    }

    public void deleteReminder(Long id) {
        policyReminderRepository.deleteById(id);
    }
}
