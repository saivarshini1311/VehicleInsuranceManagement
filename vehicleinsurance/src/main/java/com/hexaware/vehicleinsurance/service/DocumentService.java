package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.entity.Document;
import com.hexaware.vehicleinsurance.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public Document uploadDocument(Document document) {
        return documentRepository.save(document);
    }

    public Document getDocumentById(Long id) {
        return documentRepository.findById(id).orElse(null);
    }

    public List<Document> getDocumentsByUser(Long userId) {
        return documentRepository.findByUserId(userId);
    }

    public List<Document> getDocumentsByPolicy(Long policyId) {
        return documentRepository.findByPolicyId(policyId);
    }

    public List<Document> getDocumentsByVehicle(Long vehicleId) {
        return documentRepository.findByVehicleId(vehicleId);
    }
}