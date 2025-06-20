package com.hexaware.vehicleinsurance.controller;

import com.hexaware.vehicleinsurance.dto.ProposalDTO;
import com.hexaware.vehicleinsurance.entity.Proposal;
import com.hexaware.vehicleinsurance.service.ProposalService;
import com.hexaware.vehicleinsurance.repository.UserRepository;
import com.hexaware.vehicleinsurance.repository.VehicleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/proposals")
public class ProposalController {

    @Autowired
    private ProposalService proposalService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @PostMapping("/submit")
    public ResponseEntity<Proposal> submit(@RequestBody ProposalDTO dto) {
        Proposal proposal = new Proposal();
        proposal.setProposalNumber(dto.getProposalNumber());
        proposal.setStatus("PENDING");
        proposal.setProposalDate(LocalDate.now());
        proposal.setRemarks(dto.getRemarks());

        // Set user and vehicle
        userRepository.findById(dto.getUserId()).ifPresent(proposal::setUser);
        vehicleRepository.findById(dto.getVehicleId()).ifPresent(proposal::setVehicle);

        return ResponseEntity.ok(proposalService.submitProposal(proposal));
    }

    @GetMapping
    public ResponseEntity<List<Proposal>> getAll() {
        return ResponseEntity.ok(proposalService.getAllProposals());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Proposal>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(proposalService.getProposalsByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proposal> getById(@PathVariable Long id) {
        Proposal p = proposalService.getProposalById(id);
        if (p != null) return ResponseEntity.ok(p);
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proposal> update(@PathVariable Long id, @RequestBody ProposalDTO dto) {
        Proposal updated = new Proposal();
        updated.setStatus(dto.getStatus());
        updated.setRemarks(dto.getRemarks());
        return ResponseEntity.ok(proposalService.updateProposal(id, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        proposalService.deleteProposal(id);
        return ResponseEntity.noContent().build();
    }
}


