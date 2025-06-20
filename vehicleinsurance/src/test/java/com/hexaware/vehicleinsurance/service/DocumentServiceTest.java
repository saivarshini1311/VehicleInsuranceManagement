package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.entity.Document;
import com.hexaware.vehicleinsurance.repository.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentService documentService;

    @Test
    void testUploadDocument() {
        Document doc = new Document();
        doc.setName("license.pdf");

        when(documentRepository.save(any(Document.class))).thenReturn(doc);

        Document saved = documentService.uploadDocument(doc);
        assertEquals("license.pdf", saved.getName());
    }

    @Test
    void testGetById() {
        Document doc = new Document();
        doc.setId(1L);

        when(documentRepository.findById(1L)).thenReturn(Optional.of(doc));

        Document found = documentService.getDocumentById(1L);
        assertEquals(1L, found.getId());
    }

    @Test
    void testGetByUser() {
        when(documentRepository.findByUserId(1L)).thenReturn(List.of(new Document(), new Document()));

        List<Document> docs = documentService.getDocumentsByUser(1L);
        assertEquals(2, docs.size());
    }

    @Test
    void testGetByPolicy() {
        when(documentRepository.findByPolicyId(101L)).thenReturn(List.of(new Document()));

        List<Document> docs = documentService.getDocumentsByPolicy(101L);
        assertEquals(1, docs.size());
    }

    @Test
    void testGetByVehicle() {
        when(documentRepository.findByVehicleId(55L)).thenReturn(List.of(new Document()));

        List<Document> docs = documentService.getDocumentsByVehicle(55L);
        assertEquals(1, docs.size());
    }
}

