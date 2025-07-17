package com.hexaware.vehicleinsurance.controller;

import com.hexaware.vehicleinsurance.dto.PolicyReminderDTO;
import com.hexaware.vehicleinsurance.entity.*;
import com.hexaware.vehicleinsurance.entity.PolicyReminder;
import com.hexaware.vehicleinsurance.repository.*;
import com.hexaware.vehicleinsurance.service.PolicyReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reminders")
public class PolicyReminderController {

    @Autowired
    private PolicyReminderService policyReminderService;
    @Autowired 
    private PolicyRepository policyRepo;
    @Autowired 
    private QuoteRepository quoteRepo;
    @Autowired 
    private ClaimRepository claimRepo;
    @Autowired 
    private UserRepository userRepo;

    @PostMapping("/create")
    public ResponseEntity<PolicyReminder> create(@RequestBody PolicyReminderDTO dto) {
        PolicyReminder reminder = new PolicyReminder();
        reminder.setUserId(dto.getUserId());
        reminder.setPolicyId(dto.getPolicyId());
        reminder.setReminderDate(dto.getReminderDate());
        reminder.setStatus(dto.getStatus());
        reminder.setMessage(dto.getMessage());

        return ResponseEntity.ok(policyReminderService.createReminder(reminder));
    }
   

    // Send reminder email or in-app alert
    @PostMapping("/send/{type}/{id}")
    public ResponseEntity<?> sendReminder(@PathVariable String type, @PathVariable Long id) {
        String message = "";

        switch (type.toLowerCase()) {
            case "policy":
                Policy policy = policyRepo.findById(id).orElse(null);
                if (policy != null && policy.getUserId() != null) {
                    message = "Reminder: Your policy #" + policy.getPolicyNumber() + " is expiring on " + policy.getEndDate();
                    // sendEmail(policy.getUser().getEmail(), message); // implement this if needed
                    return ResponseEntity.ok("Policy reminder sent.");
                }
                break;

            case "quote":
                Quote quote = quoteRepo.findById(id).orElse(null);
                if (quote != null && quote.getProposal() != null && quote.getProposal().getUser() != null) {
                    message = "Reminder: Your quote #" + quote.getId() + " is pending payment. Premium: ₹" + quote.getPremiumAmount();
                    // sendEmail(quote.getProposal().getUser().getEmail(), message);
                    return ResponseEntity.ok("Quote reminder sent.");
                }
                break;

            case "claim":
                Claim claim = claimRepo.findById(id).orElse(null);
                if (claim != null && claim.getUserId() != null) {
                    message = "Reminder: Your claim #" + claim.getId() + " is still unsettled. Status: " + claim.getStatus();
                    // sendEmail(claim.getUser().getEmail(), message);
                    return ResponseEntity.ok("Claim reminder sent.");
                }
                break;

            default:
                return ResponseEntity.badRequest().body("Invalid reminder type.");
        }

        return ResponseEntity.badRequest().body("Data not found for ID: " + id);
    }


    @PostMapping("/resolve/{type}/{id}")
    public ResponseEntity<?> resolveReminder(@PathVariable String type, @PathVariable Long id) {
        switch (type.toLowerCase()) {
            case "policy":
                Policy policy = policyRepo.findById(id).orElse(null);
                if (policy != null) {
                    policy.setLastReminderSent(LocalDate.now());
                    policyRepo.save(policy);
                    return ResponseEntity.ok("Policy marked as resolved.");
                }
                break;

            case "quote":
                Quote quote = quoteRepo.findById(id).orElse(null);
                if (quote != null) {
                    quote.setRemarks("Reminder resolved on " + LocalDate.now());
                    quoteRepo.save(quote);
                    return ResponseEntity.ok("Quote marked as resolved.");
                }
                break;

            case "claim":
                Claim claim = claimRepo.findById(id).orElse(null);
                if (claim != null) {
                    claim.setRemarks("Reminder resolved on " + LocalDate.now());
                    claimRepo.save(claim);
                    return ResponseEntity.ok("Claim marked as resolved.");
                }
                break;

            default:
                return ResponseEntity.badRequest().body("Invalid type.");
        }

        return ResponseEntity.badRequest().body("Item not found.");
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PolicyReminder>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(policyReminderService.getRemindersByUser(userId));
    }

    @GetMapping("/policy/{policyId}")
    public ResponseEntity<List<PolicyReminder>> getByPolicy(@PathVariable Long policyId) {
        return ResponseEntity.ok(policyReminderService.getRemindersByPolicy(policyId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PolicyReminder> getById(@PathVariable Long id) {
        return ResponseEntity.ok(policyReminderService.getReminderById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        policyReminderService.deleteReminder(id);
        return ResponseEntity.noContent().build();
    }
}
