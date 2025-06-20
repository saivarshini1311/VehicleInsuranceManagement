package com.hexaware.vehicleinsurance.controller;

import com.hexaware.vehicleinsurance.dto.PolicyDTO;
import com.hexaware.vehicleinsurance.entity.Policy;
import com.hexaware.vehicleinsurance.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @PostMapping("/create")
    public ResponseEntity<Policy> createPolicy(@RequestBody PolicyDTO dto) {
        Policy policy = new Policy();
        policy.setPolicyNumber(dto.getPolicyNumber());
        policy.setStartDate(dto.getStartDate());
        policy.setEndDate(dto.getEndDate());
        policy.setPremiumAmount(dto.getPremiumAmount());
        policy.setStatus(dto.getStatus());
        policy.setUserId(dto.getUserId());
        policy.setVehicleId(dto.getVehicleId());
        policy.setCreatedDate(dto.getCreatedDate());
        policy.setCoverageDetails(dto.getCoverageDetails());

        return ResponseEntity.ok(policyService.createPolicy(policy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Policy> getPolicyById(@PathVariable Long id) {
        return ResponseEntity.ok(policyService.getPolicyById(id));
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<Policy> getPolicyByVehicleId(@PathVariable Long vehicleId) {
        return ResponseEntity.ok(policyService.getPolicyByVehicleId(vehicleId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Policy>> getPoliciesByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(policyService.getPoliciesByUserId(userId));
    }

    @GetMapping
    public ResponseEntity<List<Policy>> getAllPolicies() {
        return ResponseEntity.ok(policyService.getAllPolicies());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Policy> updatePolicy(@PathVariable Long id, @RequestBody Policy updated) {
        return ResponseEntity.ok(policyService.updatePolicy(id, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id) {
        policyService.deletePolicy(id);
        return ResponseEntity.noContent().build();
    }
}

