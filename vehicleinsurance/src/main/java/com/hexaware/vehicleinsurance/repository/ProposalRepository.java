package com.hexaware.vehicleinsurance.repository;

import com.hexaware.vehicleinsurance.entity.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {
    List<Proposal> findByUserId(Long userId);
    void deleteByVehicleId(Long vehicleId);
    List<Proposal> findByStatusIgnoreCase(String status);

}
