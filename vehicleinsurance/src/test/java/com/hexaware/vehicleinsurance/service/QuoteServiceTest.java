package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.entity.Quote;
import com.hexaware.vehicleinsurance.repository.QuoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuoteServiceTest {

    @Mock
    private QuoteRepository quoteRepository;

    @InjectMocks
    private QuoteService quoteService;

    @Test
    void testGenerateQuote() {
        Quote quote = new Quote();
        quote.setCoverageType("Comprehensive");
        quote.setRemarks("Test Remarks");

        when(quoteRepository.save(any(Quote.class))).thenAnswer(invocation -> {
            Quote q = invocation.getArgument(0);
            q.setId(1L);
            return q;
        });

        Quote result = quoteService.generateQuote(quote);

        assertNotNull(result.getGeneratedDate());
        assertEquals(8000.0, result.getEstimatedPremium());
        assertEquals("Comprehensive", result.getCoverageType());
    }

    @Test
    void testGetQuoteById() {
        Quote quote = new Quote();
        quote.setId(1L);
        when(quoteRepository.findById(1L)).thenReturn(Optional.of(quote));

        Quote found = quoteService.getQuoteById(1L);
        assertEquals(1L, found.getId());
    }

    @Test
    void testGetAllQuotes() {
        when(quoteRepository.findAll()).thenReturn(List.of(new Quote(), new Quote()));

        List<Quote> quotes = quoteService.getAllQuotes();
        assertEquals(2, quotes.size());
    }

    @Test
    void testDeleteQuote() {
        quoteService.deleteQuote(5L);
        verify(quoteRepository, times(1)).deleteById(5L);
    }
}
