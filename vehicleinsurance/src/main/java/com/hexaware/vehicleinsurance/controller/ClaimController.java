package com.hexaware.vehicleinsurance.controller;

import com.hexaware.vehicleinsurance.dto.ClaimDTO;
import com.hexaware.vehicleinsurance.entity.Claim;
import com.hexaware.vehicleinsurance.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    // Create new claim
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

    // Get claims by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Claim>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(claimService.getClaimsByUser(userId));
    }

    // Get claim by ID
    @GetMapping("/{id}")
    public ResponseEntity<Claim> getById(@PathVariable Long id) {
        return ResponseEntity.ok(claimService.getClaimById(id));
    }

    // Delete claim
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        claimService.deleteClaim(id);
        return ResponseEntity.noContent().build();
    }

    // Get claims by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Claim>> getClaimsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(claimService.getClaimsByStatus(status));
    }

    // Get claims by policy
    @GetMapping("/policy/{policyId}")
    public ResponseEntity<List<Claim>> getByPolicy(@PathVariable Long policyId) {
        return ResponseEntity.ok(claimService.getClaimsByPolicy(policyId));
    }

    // ✅ Update claim (only if status is UNDER_REVIEW)
//    @PutMapping("/update/{id}")
//    public ResponseEntity<Claim> updateClaim(@PathVariable Long id, @RequestBody Claim updatedClaim) {
//        Claim claim = claimService.updateClaim(id, updatedClaim);
//        if (claim == null) return ResponseEntity.badRequest().build();
//        return ResponseEntity.ok(claim);
//    }

    // ✅ Upload supporting document (stub for now)
    @PostMapping("/{claimId}/upload")
    public ResponseEntity<String> uploadProof(@PathVariable Long claimId, @RequestParam("file") MultipartFile file) {
        boolean success = claimService.uploadProof(claimId, file);
        if (success) return ResponseEntity.ok("File uploaded successfully");
        return ResponseEntity.badRequest().body("File upload failed");
    }

    // ✅ Generate claim acknowledgement (for PDF download)
    @GetMapping("/{id}/acknowledgement")
    public ResponseEntity<String> getAcknowledgement(@PathVariable Long id) {
        String content = claimService.generateClaimAcknowledgement(id);
        if (content == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(content);
    }

    // ✅ Get all claims (admin/officer view)
    @GetMapping("/all")
    public ResponseEntity<List<Claim>> getAllClaims() {
        return ResponseEntity.ok(claimService.getAllClaims());
    }
    
//    @PostMapping
//    public ResponseEntity<Claim> createClaim(@RequestBody Claim claim) {
//        // claim.getPolicyId() should now receive the selected policy
//        Claim savedClaim = claimService.saveClaim(claim);
//        return new ResponseEntity<>(savedClaim, HttpStatus.CREATED);
//    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Claim> updateClaim(@PathVariable Long id, @RequestBody Claim updatedClaim) {
        Claim updated = claimService.updateClaim(id, updatedClaim);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}