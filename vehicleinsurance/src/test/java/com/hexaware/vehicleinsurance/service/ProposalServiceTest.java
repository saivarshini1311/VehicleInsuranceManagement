package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.entity.Proposal;
import com.hexaware.vehicleinsurance.repository.ProposalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProposalServiceTest {

    @Mock
    private ProposalRepository proposalRepository;

    @InjectMocks
    private ProposalService proposalService;

    @Test
    void testSubmitProposal() {
        Proposal proposal = new Proposal();
        proposal.setProposalNumber("PR123");
        proposal.setRemarks("User wants fast approval");

        when(proposalRepository.save(any(Proposal.class))).thenReturn(proposal);

        Proposal result = proposalService.submitProposal(proposal);

        assertEquals("PR123", result.getProposalNumber());
        assertEquals("User wants fast approval", result.getRemarks());
        assertEquals("PENDING", result.getStatus()); // default set in service
    }

    @Test
    void testGetAllProposals() {
        when(proposalRepository.findAll()).thenReturn(List.of(new Proposal(), new Proposal()));
        List<Proposal> proposals = proposalService.getAllProposals();
        assertEquals(2, proposals.size());
    }

    @Test
    void testGetProposalsByUserId() {
        when(proposalRepository.findByUserId(1L)).thenReturn(List.of(new Proposal(), new Proposal()));
        List<Proposal> proposals = proposalService.getProposalsByUserId(1L);
        assertEquals(2, proposals.size());
    }

    @Test
    void testGetProposalById() {
        Proposal proposal = new Proposal();
        proposal.setId(1L);

        when(proposalRepository.findById(1L)).thenReturn(Optional.of(proposal));
        Proposal result = proposalService.getProposalById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testUpdateProposal() {
        Proposal existing = new Proposal();
        existing.setId(1L);
        existing.setStatus("PENDING");

        Proposal updated = new Proposal();
        updated.setStatus("APPROVED");
        updated.setRemarks("Verified and approved");

        when(proposalRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(proposalRepository.save(any(Proposal.class))).thenReturn(updated);

        Proposal result = proposalService.updateProposal(1L, updated);

        assertEquals("APPROVED", result.getStatus());
        assertEquals("Verified and approved", result.getRemarks());
    }

    @Test
    void testDeleteProposal() {
        doNothing().when(proposalRepository).deleteById(1L);
        proposalService.deleteProposal(1L);
        verify(proposalRepository, times(1)).deleteById(1L);
    }
}

