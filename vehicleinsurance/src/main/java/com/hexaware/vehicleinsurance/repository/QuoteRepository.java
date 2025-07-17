package com.hexaware.vehicleinsurance.repository;

import com.hexaware.vehicleinsurance.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    List<Quote> findByUserId(Long userId);
    List<Quote> findByStatusIgnoreCase(String status);

}
