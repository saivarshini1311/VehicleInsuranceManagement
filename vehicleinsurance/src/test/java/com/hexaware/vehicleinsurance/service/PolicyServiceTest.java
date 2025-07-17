package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.entity.Policy;
import com.hexaware.vehicleinsurance.repository.PolicyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PolicyServiceTest {

    @Mock
    private PolicyRepository policyRepository;

    @InjectMocks
    private PolicyService policyService;

    @Test
    void testCreatePolicy() {
        Policy policy = new Policy();
        policy.setPolicyNumber("POL123");
        policy.setPremiumAmount(10000.0);

        when(policyRepository.save(any(Policy.class))).thenReturn(policy);

        Policy result = policyService.createPolicy(policy);

        assertEquals("POL123", result.getPolicyNumber());
        assertEquals(10000.0, result.getPremiumAmount());
    }

    @Test
    void testGetPolicyById() {
        Policy policy = new Policy();
        policy.setId(1L);
        when(policyRepository.findById(1L)).thenReturn(Optional.of(policy));

        Policy result = policyService.getPolicyById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetPolicyByVehicleId() {
        Policy policy = new Policy();
        policy.setVehicleId(1L);
        when(policyRepository.findByVehicleId(1L)).thenReturn(policy);

        Policy result = policyService.getPolicyByVehicleId(1L);
        assertNotNull(result);
        assertEquals(1L, result.getVehicleId());
    }

    @Test
    void testGetPoliciesByUserId() {
        Policy policy1 = new Policy();
        Policy policy2 = new Policy();
        when(policyRepository.findByUserId(1L)).thenReturn(Arrays.asList(policy1, policy2));

        List<Policy> result = policyService.getPoliciesByUserId(1L);
        assertEquals(2, result.size());
    }

    @Test
    void testGetAllPolicies() {
        when(policyRepository.findAll()).thenReturn(Arrays.asList(new Policy(), new Policy()));
        List<Policy> policies = policyService.getAllPolicies();
        assertEquals(2, policies.size());
    }

    @Test
    void testUpdatePolicy() {
        Policy existing = new Policy();
        existing.setId(1L);
        existing.setStatus("ACTIVE");

        Policy updated = new Policy();
        updated.setStatus("LAPSED");

        when(policyRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(policyRepository.save(any(Policy.class))).thenReturn(existing);

        Policy result = policyService.updatePolicy(1L, updated);
        assertEquals("LAPSED", result.getStatus());
    }

    @Test
    void testDeletePolicy() {
        doNothing().when(policyRepository).deleteById(1L);
        policyService.deletePolicy(1L);
        verify(policyRepository, times(1)).deleteById(1L);
    }
}



