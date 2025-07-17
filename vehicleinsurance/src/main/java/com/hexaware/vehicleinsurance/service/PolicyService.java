package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.dto.PolicyDTO;
import com.hexaware.vehicleinsurance.entity.Policy;
import com.hexaware.vehicleinsurance.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;
import com.hexaware.vehicleinsurance.entity.Vehicle;
import com.hexaware.vehicleinsurance.repository.VehicleRepository;



@Service
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;
    
    @Autowired
    private VehicleRepository vehicleRepository;


    public Policy createPolicy(Policy policy) {
        return policyRepository.save(policy);
    }

    public Policy getPolicyById(Long id) {
        return policyRepository.findById(id).orElse(null);
    }

    public Policy getPolicyByVehicleId(Long vehicleId) {
        return policyRepository.findByVehicleId(vehicleId);
    }

    public List<Policy> getPoliciesByUserId(Long userId) {
        return policyRepository.findByUserId(userId);
    }

    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    public Policy getNextExpiringPolicy() {
        return policyRepository.findTopByEndDateAfterOrderByEndDateAsc(LocalDate.now());
    }
    
    public List<Policy> getPoliciesByStatus(String status) {
        return policyRepository.findByStatus(status.toUpperCase());
    }

    public Policy updatePolicy(Long id, Policy updatedPolicy) {
        Policy policy = getPolicyById(id);
        if (policy != null) {
            policy.setStartDate(updatedPolicy.getStartDate());
            policy.setEndDate(updatedPolicy.getEndDate());
            policy.setStatus(updatedPolicy.getStatus());
            policy.setPremiumAmount(updatedPolicy.getPremiumAmount());
            policy.setCoverageDetails(updatedPolicy.getCoverageDetails());
            return policyRepository.save(policy);
        }
        return null;
    }

    public void deletePolicy(Long id) {
        policyRepository.deleteById(id);
    }
    
    public List<Policy> getActivePoliciesByUser(Long userId) {
        return policyRepository.findActivePoliciesByUserId(userId);
    }

    

public List<PolicyDTO> getPolicyDTOsByUserId(Long userId) {
    List<Policy> policies = getPoliciesByUserId(userId);
    return policies.stream().map(policy -> {
        PolicyDTO dto = new PolicyDTO();
        dto.setPolicyNumber(policy.getPolicyNumber());
        dto.setStartDate(policy.getStartDate());
        dto.setEndDate(policy.getEndDate());
        dto.setPremiumAmount(policy.getPremiumAmount());
        dto.setStatus(policy.getStatus());
        dto.setUserId(policy.getUserId());
        dto.setVehicleId(policy.getVehicleId());
        dto.setCreatedDate(policy.getCreatedDate());
        dto.setCoverageDetails(policy.getCoverageDetails());
        dto.setId(policy.getId());

        Vehicle vehicle = vehicleRepository.findById(policy.getVehicleId()).orElse(null);
        if (vehicle != null) {
            dto.setRegistrationNumber(vehicle.getRegistrationNumber());
            dto.setBrand(vehicle.getBrand());
            dto.setModel(vehicle.getModel());
        }

        return dto;
    }).toList();
}

public byte[] generatePolicyPdf(Long policyId) {
    Policy policy = policyRepository.findById(policyId)
        .orElseThrow(() -> new RuntimeException("Policy not found"));

    try {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        document.add(new Paragraph("Vehicle Insurance Policy"));
        document.add(new Paragraph("Policy ID: " + policy.getId()));
        document.add(new Paragraph("Policy Number: " + policy.getPolicyNumber()));
        document.add(new Paragraph("Status: " + policy.getStatus()));
        document.add(new Paragraph("Start Date: " + policy.getStartDate()));
        document.add(new Paragraph("End Date: " + policy.getEndDate()));
        document.add(new Paragraph("Premium Amount: ₹" + policy.getPremiumAmount()));
        document.add(new Paragraph("Coverage Details: " + policy.getCoverageDetails()));

        Vehicle vehicle = vehicleRepository.findById(policy.getVehicleId()).orElse(null);
        if (vehicle != null) {
            document.add(new Paragraph("Vehicle Registration: " + vehicle.getRegistrationNumber()));
            document.add(new Paragraph("Brand: " + vehicle.getBrand()));
            document.add(new Paragraph("Model: " + vehicle.getModel()));
        }

        document.close();
        return out.toByteArray();
    } catch (DocumentException e) {
        throw new RuntimeException("Error generating PDF", e);
    }
}

}


