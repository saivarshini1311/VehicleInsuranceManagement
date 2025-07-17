package com.hexaware.vehicleinsurance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexaware.vehicleinsurance.entity.User;
import com.hexaware.vehicleinsurance.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testRegisterUser() {
        User user = new User();
        user.setName("Alice");
        user.setEmail("alice@example.com");

        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.registerUser(user);

        assertEquals("Alice", result.getName());
        assertEquals("alice@example.com", result.getEmail());
    }

    @Test
    void testGetUserByEmail() {
        User user = new User();
        user.setEmail("bob@example.com");

        when(userRepository.findByEmail("bob@example.com")).thenReturn(Optional.of(user));

        User result = userService.getUserByEmail("bob@example.com");

        assertEquals("bob@example.com", result.getEmail());
    }
}

