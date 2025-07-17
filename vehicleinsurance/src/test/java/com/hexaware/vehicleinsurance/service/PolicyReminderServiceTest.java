package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.entity.PolicyReminder;
import com.hexaware.vehicleinsurance.repository.PolicyReminderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PolicyReminderServiceTest {

    @Mock
    private PolicyReminderRepository repo;

    @InjectMocks
    private PolicyReminderService service;

    @Test
    void testCreateReminder() {
        PolicyReminder reminder = new PolicyReminder();
        reminder.setMessage("Renew Soon");
        when(repo.save(any())).thenReturn(reminder);
        PolicyReminder result = service.createReminder(reminder);
        assertEquals("Renew Soon", result.getMessage());
    }

    @Test
    void testGetByUser() {
        when(repo.findByUserId(1L)).thenReturn(List.of(new PolicyReminder(), new PolicyReminder()));
        assertEquals(2, service.getRemindersByUser(1L).size());
    }

    @Test
    void testGetByPolicy() {
        when(repo.findByPolicyId(10L)).thenReturn(List.of(new PolicyReminder()));
        assertEquals(1, service.getRemindersByPolicy(10L).size());
    }

    @Test
    void testGetById() {
        PolicyReminder reminder = new PolicyReminder();
        reminder.setId(1L);
        when(repo.findById(1L)).thenReturn(java.util.Optional.of(reminder));
        assertEquals(1L, service.getReminderById(1L).getId());
    }

    @Test
    void testDelete() {
        doNothing().when(repo).deleteById(1L);
        service.deleteReminder(1L);
        verify(repo, times(1)).deleteById(1L);
    }
}

