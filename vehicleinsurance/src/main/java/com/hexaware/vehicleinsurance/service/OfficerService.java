package com.hexaware.vehicleinsurance.service;
import com.hexaware.vehicleinsurance.dto.*;

import java.util.List;

public interface OfficerService {

    // Proposals
    List<ProposalDTO> getAllProposals();
    ProposalDTO getProposalById(Long id);
    ProposalDTO approveProposal(Long id);
    ProposalDTO rejectProposal(Long id, String reason);

    // Quotes
    List<QuoteDTO> getAllQuotes();
    QuoteDTO getQuoteById(Long id);
    QuoteDTO createQuote(Long proposalId, Double premium);
    void deleteQuote(Long id);

    // Policies
    List<PolicyDTO> getAllPolicies();
    PolicyDTO getPolicyById(Long id);
    PolicyDTO cancelPolicy(Long id);
    PolicyDTO renewPolicy(Long id);

    // Claims
    List<ClaimDTO> getAllClaims();
    ClaimDTO getClaimById(Long id);
    ClaimDTO approveClaim(Long id);
    ClaimDTO rejectClaim(Long id, String reason);
    ClaimDTO settleClaim(Long id);

    // Documents
    List<DocumentDTO> getAllDocuments();
    DocumentDTO getDocumentById(Long id);
    void deleteDocument(Long id);

    // Vehicles
    List<VehicleDTO> getAllVehicles();
    VehicleDTO getVehicleById(Long id);
    void deleteVehicle(Long id);

    // Users
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    void blockUser(Long id);
    void unblockUser(Long id);
    void deleteUser(Long id);

    // Stats
    DashboardStatsDTO getDashboardStats();
}
