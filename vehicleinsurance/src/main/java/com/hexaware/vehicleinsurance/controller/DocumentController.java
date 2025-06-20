package com.hexaware.vehicleinsurance.controller;

import com.hexaware.vehicleinsurance.dto.DocumentDTO;
import com.hexaware.vehicleinsurance.entity.Document;
import com.hexaware.vehicleinsurance.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    // ✅ Upload document with file and metadata
    @PostMapping("/upload")
    public ResponseEntity<DocumentDTO> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId,
            @RequestParam(value = "policyId", required = false) Long policyId,
            @RequestParam(value = "vehicleId", required = false) Long vehicleId
    ) throws IOException {

        Document doc = new Document();
        doc.setName(file.getOriginalFilename());
        doc.setType(file.getContentType());
        doc.setFileData(file.getBytes());
        doc.setUserId(userId);
        doc.setPolicyId(policyId);
        doc.setVehicleId(vehicleId);
        doc.setUploadDate(LocalDate.now());

        Document saved = documentService.uploadDocument(doc);

        DocumentDTO response = toDTO(saved);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable Long id) {
        Document doc = documentService.getDocumentById(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getName() + "\"")
                .body(doc.getFileData());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DocumentDTO>> getByUser(@PathVariable Long userId) {
        List<DocumentDTO> docs = documentService.getDocumentsByUser(userId)
                .stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(docs);
    }

    @GetMapping("/policy/{policyId}")
    public ResponseEntity<List<DocumentDTO>> getByPolicy(@PathVariable Long policyId) {
        List<DocumentDTO> docs = documentService.getDocumentsByPolicy(policyId)
                .stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(docs);
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<DocumentDTO>> getByVehicle(@PathVariable Long vehicleId) {
        List<DocumentDTO> docs = documentService.getDocumentsByVehicle(vehicleId)
                .stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(docs);
    }

    private DocumentDTO toDTO(Document doc) {
        DocumentDTO dto = new DocumentDTO();
        dto.setName(doc.getName());
        dto.setType(doc.getType());
        dto.setUploadDate(doc.getUploadDate());
        dto.setUserId(doc.getUserId());
        dto.setPolicyId(doc.getPolicyId());
        dto.setVehicleId(doc.getVehicleId());
        return dto;
    }
}

