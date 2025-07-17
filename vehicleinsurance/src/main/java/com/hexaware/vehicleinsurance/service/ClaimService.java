package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.entity.Claim;
import com.hexaware.vehicleinsurance.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClaimService {

    @Autowired
    private ClaimRepository claimRepository;

    // Submit a new claim
    public Claim createClaim(Claim claim) {
        claim.setClaimDate(LocalDate.now());
        claim.setStatus("UNDER_REVIEW");
        return claimRepository.save(claim);
    }
    
    public Claim saveClaim(Claim claim) {
        claim.setClaimDate(LocalDate.now());
        claim.setStatus("UNDER_REVIEW");
        return claimRepository.save(claim);
    }


    // Get all claims for a specific user
    public List<Claim> getClaimsByUser(Long userId) {
        return claimRepository.findByUserId(userId);
    }

    // Get claims by status (for officer/admin)
    public List<Claim> getClaimsByStatus(String status) {
        return claimRepository.findByStatus(status.toUpperCase());
    }

    // Get claims by policy
    public List<Claim> getClaimsByPolicy(Long policyId) {
        return claimRepository.findByPolicyId(policyId);
    }

    // Get claim by ID
    public Claim getClaimById(Long id) {
        return claimRepository.findById(id).orElse(null);
    }

    // Cancel/Delete claim
    public void deleteClaim(Long id) {
        claimRepository.deleteById(id);
    }

    // ✅ Update existing claim (only if still UNDER_REVIEW)
    public Claim updateClaim(Long id, Claim updatedClaim) {
        Optional<Claim> optionalClaim = claimRepository.findById(id);
        if (optionalClaim.isPresent()) {
            Claim existingClaim = optionalClaim.get();
            if ("UNDER_REVIEW".equalsIgnoreCase(existingClaim.getStatus())) {
                existingClaim.setDescription(updatedClaim.getDescription());
                existingClaim.setAmountClaimed(updatedClaim.getAmountClaimed());
                existingClaim.setPolicyId(updatedClaim.getPolicyId());
                existingClaim.setRemarks(updatedClaim.getRemarks()); // <-- Add this
                return claimRepository.save(existingClaim);
            }
        }
        return null;
    }


    // ✅ Upload additional proof (stub - actual file handling logic to be implemented)
    public boolean uploadProof(Long claimId, MultipartFile file) {
        // In real use-case: save file to local or cloud (S3, etc.), then store URL/filename in DB
        if (!file.isEmpty()) {
            System.out.println("File uploaded for claim " + claimId + ": " + file.getOriginalFilename());
            return true;
        }
        return false;
    }

    // ✅ Generate acknowledgment content (PDF logic in controller/service layer later)
    public String generateClaimAcknowledgement(Long claimId) {
        Claim claim = claimRepository.findById(claimId).orElse(null);
        if (claim == null) return null;

        StringBuilder ack = new StringBuilder();
        ack.append("Claim Acknowledgement\n");
        ack.append("Claim Number: ").append(claim.getClaimNumber()).append("\n");
        ack.append("Status: ").append(claim.getStatus()).append("\n");
        ack.append("Claim Date: ").append(claim.getClaimDate()).append("\n");
        ack.append("Amount Claimed: ₹").append(claim.getAmountClaimed()).append("\n");
        return ack.toString();
    }

    // ✅ Get all claims (admin/officer use)
    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }
}
