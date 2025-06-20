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

    @PostMapping("/generate")
    public ResponseEntity<Quote> generate(@RequestBody Quote quote) {
        return ResponseEntity.ok(quoteService.generateQuote(quote));
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

    @PutMapping("/{id}")
    public ResponseEntity<Quote> updateQuote(@PathVariable Long id, @RequestBody Quote quoteDetails) {
        Quote updated = quoteService.updateQuote(id, quoteDetails);
        if (updated != null) return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuote(@PathVariable Long id) {
        quoteService.deleteQuote(id);
        return ResponseEntity.noContent().build();
    }
}