package com.hexaware.vehicleinsurance.controller;

import com.hexaware.vehicleinsurance.dto.ClaimDTO;
import com.hexaware.vehicleinsurance.entity.Claim;
import com.hexaware.vehicleinsurance.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @PostMapping("/create")
    public ResponseEntity<Claim> createClaim(@RequestBody ClaimDTO dto) {
        Claim claim = new Claim();
        claim.setClaimNumber(dto.getClaimNumber());
        claim.setDescription(dto.getDescription());
        claim.setClaimDate(dto.getClaimDate());
        claim.setStatus(dto.getStatus());
        claim.setAmountClaimed(dto.getAmountClaimed());
        claim.setUserId(dto.getUserId());
        claim.setPolicyId(dto.getPolicyId());

        return ResponseEntity.ok(claimService.createClaim(claim));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Claim>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(claimService.getClaimsByUser(userId));
    }

    @GetMapping("/policy/{policyId}")
    public ResponseEntity<List<Claim>> getByPolicy(@PathVariable Long policyId) {
        return ResponseEntity.ok(claimService.getClaimsByPolicy(policyId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Claim> getById(@PathVariable Long id) {
        return ResponseEntity.ok(claimService.getClaimById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        claimService.deleteClaim(id);
        return ResponseEntity.noContent().build();
    }
}
