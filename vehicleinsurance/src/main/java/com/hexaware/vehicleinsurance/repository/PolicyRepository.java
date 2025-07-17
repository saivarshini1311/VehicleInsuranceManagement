package com.hexaware.vehicleinsurance.repository;

import com.hexaware.vehicleinsurance.entity.Policy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PolicyRepository extends JpaRepository<Policy, Long> {

    Policy findByVehicleId(Long vehicleId);
    Policy findTopByEndDateAfterOrderByEndDateAsc(LocalDate date);
    

    List<Policy> findByUserId(Long userId);

    @Query("SELECT COUNT(p) FROM Policy p WHERE p.userId = :userId AND p.endDate > CURRENT_DATE")
    int countActivePoliciesByUserId(@Param("userId") Long userId);

    @Query("SELECT MIN(p.endDate) FROM Policy p WHERE p.userId = :userId AND p.endDate > CURRENT_DATE")
    LocalDate findNextPolicyExpiryByUserId(@Param("userId") Long userId);
    
    @Query("SELECT p FROM Policy p WHERE p.endDate > CURRENT_DATE ORDER BY p.endDate ASC")
    Policy findTopByEndDateAfterOrderByEndDateAsc();
    
    List<Policy> findByStatus(String status);
    List<Policy> findByUserIdAndStatus(Long userId, String status);
    
    @Query("SELECT p FROM Policy p WHERE p.userId = :userId AND p.endDate > CURRENT_DATE")
    List<Policy> findActivePoliciesByUserId(@Param("userId") Long userId);


}
