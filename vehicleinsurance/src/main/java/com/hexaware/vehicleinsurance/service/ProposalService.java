package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.entity.Proposal;
import com.hexaware.vehicleinsurance.repository.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProposalService {

    @Autowired
    private ProposalRepository proposalRepository;

    public Proposal submitProposal(Proposal proposal) {
        proposal.setProposalDate(LocalDate.now());
        proposal.setStatus("PENDING");
        return proposalRepository.save(proposal);
    }

    public List<Proposal> getAllProposals() {
        return proposalRepository.findAll();
    }

    public List<Proposal> getProposalsByUserId(Long userId) {
        return proposalRepository.findByUserId(userId);
    }

    public Proposal getProposalById(Long id) {
        return proposalRepository.findById(id).orElse(null);
    }

    public Proposal updateProposal(Long id, Proposal updated) {
        Proposal proposal = getProposalById(id);
        if (proposal != null) {
            proposal.setStatus(updated.getStatus());
            proposal.setRemarks(updated.getRemarks());
            return proposalRepository.save(proposal);
        }
        return null;
    }

    public void deleteProposal(Long id) {
        proposalRepository.deleteById(id);
    }
}

