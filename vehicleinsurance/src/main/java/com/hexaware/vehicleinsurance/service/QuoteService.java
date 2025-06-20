package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.entity.Quote;
import com.hexaware.vehicleinsurance.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class QuoteService {

    @Autowired
    private QuoteRepository quoteRepository;

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
            quote.setCoverageType(quoteDetails.getCoverageType());
            quote.setEstimatedPremium(quoteDetails.getEstimatedPremium());
            quote.setRemarks(quoteDetails.getRemarks());
            quote.setGeneratedDate(quoteDetails.getGeneratedDate());
            quote.setUser(quoteDetails.getUser());
            quote.setVehicle(quoteDetails.getVehicle());
            return quoteRepository.save(quote);
        }
        return null;
    }

    public void deleteQuote(Long id) {
        quoteRepository.deleteById(id);
    }
}

