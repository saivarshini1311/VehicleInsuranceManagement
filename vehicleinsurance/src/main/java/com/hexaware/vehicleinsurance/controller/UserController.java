package com.hexaware.vehicleinsurance.controller;

import com.hexaware.vehicleinsurance.dto.UserDTO;
import com.hexaware.vehicleinsurance.entity.User;
import com.hexaware.vehicleinsurance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setDob(userDTO.getDob());
        user.setAadharNumber(userDTO.getAadharNumber());
        user.setPanNumber(userDTO.getPanNumber());
        user.setRole(userDTO.getRole());

        User saved = userService.registerUser(user);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }
}


