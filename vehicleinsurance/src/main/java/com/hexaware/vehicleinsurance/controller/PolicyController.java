package com.hexaware.vehicleinsurance.controller;

import com.hexaware.vehicleinsurance.dto.PolicyDTO;
import com.hexaware.vehicleinsurance.entity.Policy;
import com.hexaware.vehicleinsurance.service.PolicyService;
import org.springframework.http.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @PostMapping("/create")
    public ResponseEntity<Policy> createPolicy(@Valid @RequestBody PolicyDTO dto) {
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
    public ResponseEntity<List<PolicyDTO>> getPoliciesByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(policyService.getPolicyDTOsByUserId(userId));
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

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Policy>> getPoliciesByStatus(@PathVariable String status) {
        return ResponseEntity.ok(policyService.getPoliciesByStatus(status));
    }
    
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadPolicyPdf(@PathVariable Long id) {
        byte[] pdfData = policyService.generatePolicyPdf(id);
        if (pdfData == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "policy_" + id + ".pdf");

        return new ResponseEntity<>(pdfData, headers, HttpStatus.OK);
    }



   
    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<Policy>> getActivePoliciesByUser(@PathVariable Long userId) {
        List<Policy> activePolicies = policyService.getActivePoliciesByUser(userId);
        return ResponseEntity.ok(activePolicies);
    }

    
}
