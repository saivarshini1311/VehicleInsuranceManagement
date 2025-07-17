package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.entity.Document;

import java.io.IOException;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    Document uploadDocument(Document document);
    Document getDocumentById(Long id);
    List<Document> getDocumentsByUser(Long userId);
    List<Document> getDocumentsByPolicy(Long policyId);
    List<Document> getDocumentsByVehicle(Long vehicleId);
    void deleteDocument(Long id);
    Document uploadDocument(MultipartFile file, Long userId, Long policyId, Long vehicleId) throws IOException;
}
    
