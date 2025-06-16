package com.insurance.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
    private String aadhaarNumber;
    private String panNumber;
    private String dob;
}

