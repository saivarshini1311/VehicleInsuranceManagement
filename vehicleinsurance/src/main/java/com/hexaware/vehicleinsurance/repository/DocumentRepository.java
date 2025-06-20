package com.hexaware.vehicleinsurance.repository;

import com.hexaware.vehicleinsurance.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByUserId(Long userId);
    List<Document> findByPolicyId(Long policyId);
    List<Document> findByVehicleId(Long vehicleId);
}
