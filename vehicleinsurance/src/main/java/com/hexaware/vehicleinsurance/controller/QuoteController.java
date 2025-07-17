package com.hexaware.vehicleinsurance.controller;

import com.hexaware.vehicleinsurance.entity.Quote;
import com.hexaware.vehicleinsurance.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    @Autowired
    private QuoteService quoteService;
    
    @PostMapping("/generate/{proposalId}")
    public ResponseEntity<Quote> generateQuoteForProposal(@PathVariable Long proposalId) {
        Quote quote = quoteService.generateQuoteForProposal(proposalId);
        if (quote == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(quote);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Quote>> getUserQuotes(@PathVariable Long userId) {
        return ResponseEntity.ok(quoteService.getQuotesByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quote> getById(@PathVariable Long id) {
        return ResponseEntity.ok(quoteService.getQuoteById(id));
    }

    @GetMapping
    public ResponseEntity<List<Quote>> getAllQuotes() {
        return ResponseEntity.ok(quoteService.getAllQuotes());
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Quote> updateQuote(@PathVariable Long id, @RequestBody Quote quoteDetails) {
//        Quote updated = quoteService.updateQuote(id, quoteDetails);
//        if (updated != null) return ResponseEntity.ok(updated);
//        return ResponseEntity.notFound().build();
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuote(@PathVariable Long id) {
        quoteService.deleteQuote(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Quote>> getQuotesByStatus(@PathVariable String status) {
        List<Quote> quotes = quoteService.getQuotesByStatus(status);
        return ResponseEntity.ok(quotes);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<Quote> updateQuote(@PathVariable Long id, @RequestBody Quote quoteDetails) {
        Quote updatedQuote = quoteService.updateQuote(id, quoteDetails);
        if (updatedQuote != null) {
            return ResponseEntity.ok(updatedQuote);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelQuote(@PathVariable Long id) {
        Quote quote = quoteService.getQuoteById(id);
        if (quote == null) {
            return ResponseEntity.notFound().build();
        }

        quote.setStatus("CANCELLED");
        quoteService.updateQuote(id, quote);
        return ResponseEntity.noContent().build();
    }


}