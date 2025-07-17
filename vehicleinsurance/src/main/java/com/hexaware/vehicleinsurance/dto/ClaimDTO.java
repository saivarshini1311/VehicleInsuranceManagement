package com.hexaware.vehicleinsurance.dto;

import java.time.LocalDate;
import java.util.List;

public class ClaimDTO {
    private String claimNumber;
    private String description;
    private LocalDate claimDate;
    private String status;
    private Double amountClaimed;
    private Long userId;
    private Long policyId;
    private String remarks;
    private String policyType;
    private String userName;
    private List<String> proofFiles;

    public String getClaimNumber() { return claimNumber; }
    public void setClaimNumber(String claimNumber) { this.claimNumber = claimNumber; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getClaimDate() { return claimDate; }
    public void setClaimDate(LocalDate claimDate) { this.claimDate = claimDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getAmountClaimed() { return amountClaimed; }
    public void setAmountClaimed(Double amountClaimed) { this.amountClaimed = amountClaimed; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getPolicyId() { return policyId; }
    public void setPolicyId(Long policyId) { this.policyId = policyId; }
    
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getProofFiles() {
        return proofFiles;
    }

    public void setProofFiles(List<String> proofFiles) {
        this.proofFiles = proofFiles;
    }
}
