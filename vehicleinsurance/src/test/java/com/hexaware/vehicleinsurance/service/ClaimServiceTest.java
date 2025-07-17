package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.entity.Claim;
import com.hexaware.vehicleinsurance.repository.ClaimRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClaimServiceTest {

    @Mock
    private ClaimRepository claimRepository;

    @InjectMocks
    private ClaimService claimService;

    @Test
    void testCreateClaim() {
        Claim claim = new Claim();
        claim.setClaimNumber("CLM001");
        when(claimRepository.save(any(Claim.class))).thenReturn(claim);

        Claim result = claimService.createClaim(claim);
        assertEquals("CLM001", result.getClaimNumber());
    }

    @Test
    void testGetByUserId() {
        when(claimRepository.findByUserId(1L)).thenReturn(List.of(new Claim(), new Claim()));
        List<Claim> list = claimService.getClaimsByUser(1L);
        assertEquals(2, list.size());
    }

    @Test
    void testGetByPolicyId() {
        when(claimRepository.findByPolicyId(10L)).thenReturn(List.of(new Claim()));
        List<Claim> list = claimService.getClaimsByPolicy(10L);
        assertEquals(1, list.size());
    }

    @Test
    void testGetById() {
        Claim claim = new Claim();
        claim.setId(1L);
        when(claimRepository.findById(1L)).thenReturn(java.util.Optional.of(claim));
        Claim result = claimService.getClaimById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void testDelete() {
        doNothing().when(claimRepository).deleteById(1L);
        claimService.deleteClaim(1L);
        verify(claimRepository, times(1)).deleteById(1L);
    }
}

