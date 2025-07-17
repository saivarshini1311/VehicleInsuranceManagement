package com.hexaware.vehicleinsurance.dto;

public class JwtResponse {
    private String token;
    private String role;
    private Long id; 
    private String name;
    
    public JwtResponse(String token, String role, Long id) {
        this.token = token;
        this.role = role;
        this.id = id;
    }    
    public JwtResponse(String token, String role, Long id, String name) {
        this.token = token;
        this.role = role;
        this.id = id;
        this.name = name;
    }

    public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }

    public Long getId() {
        return id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
