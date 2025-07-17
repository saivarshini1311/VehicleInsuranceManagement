package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.dto.PasswordDTO;
import com.hexaware.vehicleinsurance.dto.UserDTO;
import com.hexaware.vehicleinsurance.entity.User;
import com.hexaware.vehicleinsurance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setDob(userDTO.getDob());
        existingUser.setAddress(userDTO.getAddress());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @Override
    public void changePassword(Long id, PasswordDTO passwordDTO) {
        User user = getUserById(id);
        if (!passwordEncoder.matches(passwordDTO.getCurrent(), user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }
        if (!passwordDTO.getNewPassword().equals(passwordDTO.getConfirm())) {
            throw new RuntimeException("New passwords do not match");
        }
        user.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public User getAddress(String address) {
        return userRepository.findByAddress(address)
                .orElseThrow(() -> new RuntimeException("User not found with address: " + address));
    }
    
    @Override
    public User registerOfficer(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("OFFICER");

        // Skip optional fields like address, aadhar, pan, etc.
        return userRepository.save(user);
    }

}
