package com.hexaware.vehicleinsurance.dto;

public class QuoteDTO {
    private String coverageType;
    private String remarks;
    private Long userId;
    private Long vehicleId;

    public String getCoverageType() {
        return coverageType;
    }
    public void setCoverageType(String coverageType) {
        this.coverageType = coverageType;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getVehicleId() {
        return vehicleId;
    }
    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }
}
