package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.entity.Policy;
import com.hexaware.vehicleinsurance.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    public Policy createPolicy(Policy policy) {
        return policyRepository.save(policy);
    }

    public Policy getPolicyById(Long id) {
        return policyRepository.findById(id).orElse(null);
    }

    public Policy getPolicyByVehicleId(Long vehicleId) {
        return policyRepository.findByVehicleId(vehicleId);
    }

    public List<Policy> getPoliciesByUserId(Long userId) {
        return policyRepository.findByUserId(userId);
    }

    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    public Policy updatePolicy(Long id, Policy updatedPolicy) {
        Policy policy = getPolicyById(id);
        if (policy != null) {
            policy.setStartDate(updatedPolicy.getStartDate());
            policy.setEndDate(updatedPolicy.getEndDate());
            policy.setStatus(updatedPolicy.getStatus());
            policy.setPremiumAmount(updatedPolicy.getPremiumAmount());
            policy.setCoverageDetails(updatedPolicy.getCoverageDetails());
            return policyRepository.save(policy);
        }
        return null;
    }

    public void deletePolicy(Long id) {
        policyRepository.deleteById(id);
    }
}


