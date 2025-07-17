package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.dto.*;
import com.hexaware.vehicleinsurance.entity.*;
import com.hexaware.vehicleinsurance.exception.ResourceNotFoundException;
import com.hexaware.vehicleinsurance.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfficerServiceImpl implements OfficerService {

    @Autowired private ProposalRepository proposalRepo;
    @Autowired private QuoteRepository quoteRepo;
    @Autowired private PolicyRepository policyRepo;
    @Autowired private ClaimRepository claimRepo;
    @Autowired private DocumentRepository documentRepo;
    @Autowired private VehicleRepository vehicleRepo;
    @Autowired private UserRepository userRepo;

    @Autowired private ModelMapper mapper;

    // ========== Proposals ==========
    @Override
    public List<ProposalDTO> getAllProposals() {
        return proposalRepo.findAll().stream()
                .map(p -> mapper.map(p, ProposalDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ProposalDTO getProposalById(Long id) {
        Proposal proposal = proposalRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proposal not found"));
        return mapper.map(proposal, ProposalDTO.class);
    }

    @Override
    public ProposalDTO approveProposal(Long id) {
        Proposal proposal = proposalRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proposal not found"));
        proposal.setStatus("APPROVED");
        proposalRepo.save(proposal);
        return mapper.map(proposal, ProposalDTO.class);
    }

    @Override
    public ProposalDTO rejectProposal(Long id, String reason) {
        Proposal proposal = proposalRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proposal not found"));
        proposal.setStatus("REJECTED");
        proposal.setDescription(proposal.getDescription() + " | Rejected: " + reason);
        proposalRepo.save(proposal);
        return mapper.map(proposal, ProposalDTO.class);
    }

    // ========== Quotes ==========
    @Override
    public List<QuoteDTO> getAllQuotes() {
        return quoteRepo.findAll().stream()
                .map(q -> mapper.map(q, QuoteDTO.class)).collect(Collectors.toList());
    }

    @Override
    public QuoteDTO getQuoteById(Long id) {
        Quote quote = quoteRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quote not found"));
        return mapper.map(quote, QuoteDTO.class);
    }

    @Override
    public QuoteDTO createQuote(Long proposalId, Double premium) {
        Proposal proposal = proposalRepo.findById(proposalId)
                .orElseThrow(() -> new ResourceNotFoundException("Proposal not found"));

        Quote quote = new Quote();
        quote.setProposal(proposal);
        quote.setPremiumAmount(premium);
        quote.setExpiryDate(LocalDate.now().plusDays(15));
        quote.setStatus("PENDING_PAYMENT");

        Quote saved = quoteRepo.save(quote);
        return mapper.map(saved, QuoteDTO.class);
    }

    @Override
    public void deleteQuote(Long id) {
        quoteRepo.deleteById(id);
    }

    // ========== Policies ==========
    @Override
    public List<PolicyDTO> getAllPolicies() {
        return policyRepo.findAll().stream()
                .map(p -> mapper.map(p, PolicyDTO.class)).collect(Collectors.toList());
    }

    @Override
    public PolicyDTO getPolicyById(Long id) {
        Policy policy = policyRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found"));
        return mapper.map(policy, PolicyDTO.class);
    }

    @Override
    public PolicyDTO cancelPolicy(Long id) {
        Policy policy = policyRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found"));
        policy.setStatus("CANCELLED");
        policyRepo.save(policy);
        return mapper.map(policy, PolicyDTO.class);
    }

    @Override
    public PolicyDTO renewPolicy(Long id) {
        Policy policy = policyRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Policy not found"));

        // Set new dates
        policy.setStartDate(LocalDate.now());
        policy.setEndDate(LocalDate.now().plusYears(1));

        policyRepo.save(policy);
        return mapper.map(policy, PolicyDTO.class);
    }


    // ========== Claims ==========
    @Override
    public List<ClaimDTO> getAllClaims() {
        return claimRepo.findAll().stream()
                .map(c -> mapper.map(c, ClaimDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ClaimDTO getClaimById(Long id) {
        Claim claim = claimRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));
        return mapper.map(claim, ClaimDTO.class);
    }

    @Override
    public ClaimDTO approveClaim(Long id) {
        Claim claim = claimRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));
        claim.setStatus("APPROVED");
        claimRepo.save(claim);
        return mapper.map(claim, ClaimDTO.class);
    }

    @Override
    public ClaimDTO rejectClaim(Long id, String reason) {
        Claim claim = claimRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));
        claim.setStatus("REJECTED");
        claim.setDescription(claim.getDescription() + " | Rejected: " + reason);
        claimRepo.save(claim);
        return mapper.map(claim, ClaimDTO.class);
    }

    @Override
    public ClaimDTO settleClaim(Long id) {
        Claim claim = claimRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));
        claim.setStatus("SETTLED");
        claimRepo.save(claim);
        return mapper.map(claim, ClaimDTO.class);
    }

    // ========== Documents ==========
    @Override
    public List<DocumentDTO> getAllDocuments() {
        return documentRepo.findAll().stream()
                .map(d -> mapper.map(d, DocumentDTO.class)).collect(Collectors.toList());
    }

    @Override
    public DocumentDTO getDocumentById(Long id) {
        Document doc = documentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found"));
        return mapper.map(doc, DocumentDTO.class);
    }

    @Override
    public void deleteDocument(Long id) {
        documentRepo.deleteById(id);
    }

    // ========== Vehicles ==========
    @Override
    public List<VehicleDTO> getAllVehicles() {
        return vehicleRepo.findAll().stream()
                .map(v -> mapper.map(v, VehicleDTO.class)).collect(Collectors.toList());
    }

    @Override
    public VehicleDTO getVehicleById(Long id) {
        Vehicle v = vehicleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
        return mapper.map(v, VehicleDTO.class);
    }

    @Override
    public void deleteVehicle(Long id) {
        vehicleRepo.deleteById(id);
    }

    // ========== Users ==========
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepo.findAll().stream()
                .map(u -> mapper.map(u, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return mapper.map(user, UserDTO.class);
    }

    @Override
    public void blockUser(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setRole("BLOCKED");
        userRepo.save(user);
    }

    @Override
    public void unblockUser(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setRole("USER");
        userRepo.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    // ========== Dashboard Stats ==========
    @Override
    public DashboardStatsDTO getDashboardStats() {
        DashboardStatsDTO stats = new DashboardStatsDTO();

        stats.setVehicles((int) vehicleRepo.count());
        stats.setPolicies((int) policyRepo.count());
        stats.setClaims((int) claimRepo.count());

        // Get the next policy expiry date (earliest future date)
        Policy nextExpiryPolicy = policyRepo.findTopByEndDateAfterOrderByEndDateAsc(LocalDate.now());
        if (nextExpiryPolicy != null) {
            stats.setNextExpiry(nextExpiryPolicy.getEndDate().toString());
        } else {
            stats.setNextExpiry("N/A");
        }

        return stats;
    }

}
