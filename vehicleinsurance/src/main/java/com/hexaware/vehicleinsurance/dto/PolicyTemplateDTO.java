package com.hexaware.vehicleinsurance.dto;

public class PolicyTemplateDTO {
    private Long id;
    private String name;
    private String description;
    private double basePremium;
    
    public PolicyTemplateDTO(Long id, String name, String description, double basePremium) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.basePremium = basePremium;
    }
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getBasePremium() {
		return basePremium;
	}
	public void setBasePremium(double basePremium) {
		this.basePremium = basePremium;
	}

    
}
