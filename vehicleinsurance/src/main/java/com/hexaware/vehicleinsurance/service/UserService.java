package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.dto.PasswordDTO;
import com.hexaware.vehicleinsurance.dto.UserDTO;
import com.hexaware.vehicleinsurance.entity.User;

import java.util.List;

public interface UserService {
    User registerUser(User user);
    User getUserById(Long id);
    User registerOfficer(User user);
    List<User> getAllUsers();
    User updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
    User getUserByEmail(String email);
    User getAddress(String address);
    void changePassword(Long id, PasswordDTO passwordDTO);
}
