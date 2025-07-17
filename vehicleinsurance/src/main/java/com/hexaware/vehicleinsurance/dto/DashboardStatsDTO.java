package com.hexaware.vehicleinsurance.dto;

public class DashboardStatsDTO {
    private int vehicles;
    private int policies;
    private int claims;
    private String nextExpiry;
	public int getVehicles() {
		return vehicles;
	}
	public void setVehicles(int vehicles) {
		this.vehicles = vehicles;
	}
	public int getPolicies() {
		return policies;
	}
	public void setPolicies(int policies) {
		this.policies = policies;
	}
	public int getClaims() {
		return claims;
	}
	public void setClaims(int claims) {
		this.claims = claims;
	}
	public String getNextExpiry() {
		return nextExpiry;
	}
	public void setNextExpiry(String nextExpiry) {
		this.nextExpiry = nextExpiry;
	}

    
}
