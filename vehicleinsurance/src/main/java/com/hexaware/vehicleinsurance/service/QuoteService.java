package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.dto.QuoteDTO;
import com.hexaware.vehicleinsurance.entity.Proposal;
import com.hexaware.vehicleinsurance.entity.Quote;
import com.hexaware.vehicleinsurance.entity.User;
import com.hexaware.vehicleinsurance.entity.Vehicle;
import com.hexaware.vehicleinsurance.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class QuoteService {

    @Autowired
    private QuoteRepository quoteRepository;
    
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ProposalRepository proposalRepository;
    private double calculatePremiumBasedOnVehicle(Vehicle vehicle) {
        String type = vehicle.getType().toLowerCase();

        switch (type) {
            case "car":
                return 4000.0;
            case "bike":
                return 1500.0;
            case "truck":
                return 6000.0;
            case "camper":
                return 5000.0;
            default:
                return 3000.0; // fallback premium
        }
    }
    public Quote generateQuote(Quote quote) {
        quote.setGeneratedDate(LocalDate.now());
        double premium = 5000;
        if ("Comprehensive".equalsIgnoreCase(quote.getCoverageType())) {
            premium += 3000;
        }
        quote.setEstimatedPremium(premium);
        return quoteRepository.save(quote);
    }

    public List<Quote> getQuotesByUserId(Long userId) {
        return quoteRepository.findByUserId(userId);
    }

    public Quote getQuoteById(Long id) {
        return quoteRepository.findById(id).orElse(null);
    }

    public List<Quote> getAllQuotes() {
        return quoteRepository.findAll();
    }

    public Quote updateQuote(Long id, Quote quoteDetails) {
        Quote quote = getQuoteById(id);
        if (quote != null) {
            // Update only non-null fields from quoteDetails
            if (quoteDetails.getPremiumAmount() != null) {
                quote.setPremiumAmount(quoteDetails.getPremiumAmount());
            }

            if (quoteDetails.getStatus() != null) {
                quote.setStatus(quoteDetails.getStatus());
            }

            if (quoteDetails.getRemarks() != null) {
                quote.setRemarks(quoteDetails.getRemarks());
            }

            if (quoteDetails.getCoverageType() != null) {
                quote.setCoverageType(quoteDetails.getCoverageType());
            }

            if (quoteDetails.getGeneratedDate() != null) {
                quote.setGeneratedDate(quoteDetails.getGeneratedDate());
            }

            if (quoteDetails.getExpiryDate() != null) {
                quote.setExpiryDate(quoteDetails.getExpiryDate());
            }

            return quoteRepository.save(quote);
        }
        return null;
    }

    public void deleteQuote(Long id) {
        quoteRepository.deleteById(id);
    }
    
    public Quote generateQuoteForProposal(Long proposalId) {
        // 1. Get the approved proposal
        Proposal proposal = proposalRepository.findById(proposalId).orElse(null);
        if (proposal == null || !proposal.getStatus().equalsIgnoreCase("APPROVED")) {
            return null;
        }

        // 2. Get linked vehicle and user from proposal
        Vehicle vehicle = proposal.getVehicle();
        User user = proposal.getUser();
        if (vehicle == null || user == null) {
            return null;
        }

        // 3. Calculate premium
        double premium = calculatePremiumBasedOnVehicle(vehicle);

        // 4. Create new quote
        Quote quote = new Quote();
        quote.setProposal(proposal);                     // link proposal
        quote.setUser(user);                             // sets user_id via JPA
        quote.setVehicle(vehicle);                       // sets vehicle_id via JPA
        quote.setCoverageType(proposal.getCoverageType());
        quote.setPremiumAmount(premium);
        quote.setGeneratedDate(LocalDate.now());         // today's date
        quote.setExpiryDate(LocalDate.now().plusDays(15)); // 15 days validity
        quote.setStatus("PENDING_PAYMENT");

        return quoteRepository.save(quote);
    }
    
    public Quote generateQuoteFromProposal(Proposal proposal) {
        Quote quote = new Quote();
        quote.setProposal(proposal);
        quote.setUser(proposal.getUser());
        quote.setVehicle(proposal.getVehicle());
        quote.setCoverageType(proposal.getCoverageType());

        double premium = calculatePremiumBasedOnVehicle(proposal.getVehicle());
        quote.setPremiumAmount(premium);

        quote.setStatus("PENDING_PAYMENT");
        quote.setGeneratedDate(LocalDate.now());
        quote.setExpiryDate(LocalDate.now().plusDays(15));

        return quoteRepository.save(quote);
    }
    
    public List<Quote> getQuotesByStatus(String status) {
        return quoteRepository.findByStatusIgnoreCase(status);
    }

    public QuoteDTO mapToDTO(Quote quote) {
        QuoteDTO dto = new QuoteDTO();
        dto.setId(quote.getId());
        dto.setUserName(quote.getUser() != null ? quote.getUser().getName() : "N/A");
        dto.setPremiumAmount(quote.getPremiumAmount());
        dto.setExpiryDate(quote.getExpiryDate());
        dto.setStatus(quote.getStatus());
        return dto;
    }


}

