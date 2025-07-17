package com.hexaware.vehicleinsurance.controller;

import com.hexaware.vehicleinsurance.dto.UserDTO;
import com.hexaware.vehicleinsurance.dto.PasswordDTO;
import com.hexaware.vehicleinsurance.entity.User;
import com.hexaware.vehicleinsurance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/register-officer")
    public ResponseEntity<User> registerOfficer(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        // Optional: validate email domain
        if (!user.getEmail().endsWith("@officer.com") && !user.getEmail().endsWith("@admin.com")) {
            return ResponseEntity.badRequest().body(null);
        }

        user.setRole("OFFICER");

        User savedOfficer = userService.registerOfficer(user);
        return ResponseEntity.ok(savedOfficer);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<String> changePassword(
        @PathVariable Long id,
        @RequestBody PasswordDTO passwordDTO
    ) {
        userService.changePassword(id, passwordDTO);
        return ResponseEntity.ok("Password updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully.");
    }
}
