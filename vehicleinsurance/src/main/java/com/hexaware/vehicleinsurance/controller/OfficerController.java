package com.hexaware.vehicleinsurance.controller;

import com.hexaware.vehicleinsurance.dto.*;
import com.hexaware.vehicleinsurance.entity.Quote;
import com.hexaware.vehicleinsurance.service.OfficerService;
import com.hexaware.vehicleinsurance.service.QuoteService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/officer")
@PreAuthorize("hasRole('OFFICER')")
public class OfficerController {

    @Autowired
    private OfficerService officerService;
    
    @Autowired
    private QuoteService quoteService;

    // ======= PROPOSALS =======
    @GetMapping("/proposals")
    public List<ProposalDTO> getAllProposals() {
        return officerService.getAllProposals();
    }

    @GetMapping("/proposals/{id}")
    public ProposalDTO getProposal(@PathVariable Long id) {
        return officerService.getProposalById(id);
    }

    @PostMapping("/proposals/{id}/approve")
    public ProposalDTO approveProposal(@PathVariable Long id) {
        return officerService.approveProposal(id);
    }

    @PostMapping("/proposals/{id}/reject")
    public ProposalDTO rejectProposal(@PathVariable Long id, @RequestParam String reason) {
        return officerService.rejectProposal(id, reason);
    }

    // ======= QUOTES =======
    @GetMapping("/quotes")
    public List<QuoteDTO> getAllQuotes() {
        return officerService.getAllQuotes();
    }

    @GetMapping("/quotes/{id}")
    public QuoteDTO getQuote(@PathVariable Long id) {
        return officerService.getQuoteById(id);
    }

    @PostMapping("/quotes")
    public QuoteDTO createQuote(@RequestParam Long proposalId, @RequestParam Double premium) {
        return officerService.createQuote(proposalId, premium);
    }

    @DeleteMapping("/quotes/{id}")
    public ResponseEntity<Void> deleteQuote(@PathVariable Long id) {
        officerService.deleteQuote(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<QuoteDTO>> getQuotesByStatus(@PathVariable String status) {
        List<Quote> quotes = quoteService.getQuotesByStatus(status);
        List<QuoteDTO> dtos = quotes.stream()
                                    .map(quoteService::mapToDTO)
                                    .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // ======= POLICIES =======
    @GetMapping("/policies")
    public List<PolicyDTO> getAllPolicies() {
        return officerService.getAllPolicies();
    }

    @GetMapping("/policies/{id}")
    public PolicyDTO getPolicy(@PathVariable Long id) {
        return officerService.getPolicyById(id);
    }

    @PutMapping("/policies/{id}/cancel")
    public PolicyDTO cancelPolicy(@PathVariable Long id) {
        return officerService.cancelPolicy(id);
    }

    @PutMapping("/policies/{id}/renew")
    public PolicyDTO renewPolicy(@PathVariable Long id) {
        return officerService.renewPolicy(id);
    }

    // ======= CLAIMS =======
    @GetMapping("/claims")
    public List<ClaimDTO> getAllClaims() {
        return officerService.getAllClaims();
    }

    @GetMapping("/claims/{id}")
    public ClaimDTO getClaim(@PathVariable Long id) {
        return officerService.getClaimById(id);
    }
    
    @PostMapping("/claims/{id}/approve")
    public ResponseEntity<ClaimDTO> approveClaim(@PathVariable Long id) {
        try {
            ClaimDTO result = officerService.approveClaim(id);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/claims/{id}/reject")
    public ClaimDTO rejectClaim(@PathVariable Long id, @RequestParam String reason) {
        return officerService.rejectClaim(id, reason);
    }

    @PostMapping("/claims/{id}/settle")
    public ClaimDTO settleClaim(@PathVariable Long id) {
        return officerService.settleClaim(id);
    }

    // ======= DOCUMENTS =======
    @GetMapping("/documents")
    public List<DocumentDTO> getAllDocuments() {
        return officerService.getAllDocuments();
    }

    @GetMapping("/documents/{id}")
    public DocumentDTO getDocument(@PathVariable Long id) {
        return officerService.getDocumentById(id);
    }

    @DeleteMapping("/documents/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        officerService.deleteDocument(id);
        return ResponseEntity.ok().build();
    }

    // ======= VEHICLES =======
    @GetMapping("/vehicles")
    public List<VehicleDTO> getAllVehicles() {
        return officerService.getAllVehicles();
    }

    @GetMapping("/vehicles/{id}")
    public VehicleDTO getVehicle(@PathVariable Long id) {
        return officerService.getVehicleById(id);
    }

    @DeleteMapping("/vehicles/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        officerService.deleteVehicle(id);
        return ResponseEntity.ok().build();
    }

    // ======= USERS =======
    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return officerService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return officerService.getUserById(id);
    }

    @PutMapping("/users/{id}/block")
    public ResponseEntity<Void> blockUser(@PathVariable Long id) {
        officerService.blockUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{id}/unblock")
    public ResponseEntity<Void> unblockUser(@PathVariable Long id) {
        officerService.unblockUser(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        officerService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    // ======= STATS =======
    @GetMapping("/stats")
    public DashboardStatsDTO getDashboardStats() {
        return officerService.getDashboardStats();
    }
}
