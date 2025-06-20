package com.hexaware.vehicleinsurance.controller;

import com.hexaware.vehicleinsurance.dto.PolicyReminderDTO;
import com.hexaware.vehicleinsurance.entity.PolicyReminder;
import com.hexaware.vehicleinsurance.service.PolicyReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reminders")
public class PolicyReminderController {

    @Autowired
    private PolicyReminderService policyReminderService;

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
