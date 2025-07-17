package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.entity.Document;
import com.hexaware.vehicleinsurance.repository.DocumentRepository;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public Document uploadDocument(Document document) {
        return documentRepository.save(document);
    }

    @Override
    public Document getDocumentById(Long id) {
        return documentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Document> getDocumentsByUser(Long userId) {
        return documentRepository.findByUserId(userId);
    }

    @Override
    public List<Document> getDocumentsByPolicy(Long policyId) {
        return documentRepository.findByPolicyId(policyId);
    }

    @Override
    public List<Document> getDocumentsByVehicle(Long vehicleId) {
        return documentRepository.findByVehicleId(vehicleId);
    }

    @Override
    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }
    
    @Override
    public Document uploadDocument(MultipartFile file, Long userId, Long policyId, Long vehicleId) throws IOException {
        Document doc = new Document();
        doc.setName(file.getOriginalFilename());
        doc.setType(file.getContentType());
        doc.setFileData(file.getBytes());
        doc.setUploadDate(LocalDate.now());
        doc.setUserId(userId);
        doc.setPolicyId(policyId);
        doc.setVehicleId(vehicleId);
        return documentRepository.save(doc);
    }

}

