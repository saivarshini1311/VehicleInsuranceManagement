package com.hexaware.vehicleinsurance.controller;

import com.hexaware.vehicleinsurance.entity.Document;
import com.hexaware.vehicleinsurance.repository.DocumentRepository;
import com.hexaware.vehicleinsurance.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "http://localhost:3000")
public class DocumentController {

    @Autowired
    private DocumentService documentService;
    
    @Autowired
    private DocumentRepository documentRepository;

    @PostMapping("/upload")
    public ResponseEntity<Document> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId,
            @RequestParam(required = false) Long policyId,
            @RequestParam(required = false) Long vehicleId
    ) throws IOException {
        Document uploaded = documentService.uploadDocument(file, userId, policyId, vehicleId);
        return ResponseEntity.ok(uploaded);
    }


    @GetMapping("/user/{userId}")
    public List<Document> getDocumentsByUser(@PathVariable Long userId) {
        return documentService.getDocumentsByUser(userId);
    }

    @GetMapping("/policy/{policyId}")
    public List<Document> getDocumentsByPolicy(@PathVariable Long policyId) {
        return documentService.getDocumentsByPolicy(policyId);
    }

    @GetMapping("/vehicle/{vehicleId}")
    public List<Document> getDocumentsByVehicle(@PathVariable Long vehicleId) {
        return documentService.getDocumentsByVehicle(vehicleId);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable Long id) {
        Document doc = documentService.getDocumentById(id);
        if (doc == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getName() + "\"")
                .contentType(MediaType.parseMediaType(doc.getType()))
                .body(doc.getFileData());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.ok("Document deleted successfully");
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<Document> updateStatus(@PathVariable Long id, @RequestParam String status) {
        Document doc = documentService.getDocumentById(id);
        if (doc == null) {
            return ResponseEntity.notFound().build();
        }
        doc.setStatus(status);
        Document updated = documentService.uploadDocument(doc); // save updated doc
        return ResponseEntity.ok(updated);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDocument(@PathVariable Long id, @RequestBody Document updatedDoc) {
        Optional<Document> optionalDoc = documentRepository.findById(id);
        if (optionalDoc.isPresent()) {
            Document existingDoc = optionalDoc.get();

            // Update only the allowed fields (like status)
            existingDoc.setStatus(updatedDoc.getStatus());

            // Save the updated document
            Document savedDoc = documentRepository.save(existingDoc);
            return ResponseEntity.ok(savedDoc);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document not found with ID: " + id);
        }
    }


    
    
}
