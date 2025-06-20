package com.hexaware.vehicleinsurance.repository;

import com.hexaware.vehicleinsurance.entity.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {
    List<Proposal> findByUserId(Long userId);
}
