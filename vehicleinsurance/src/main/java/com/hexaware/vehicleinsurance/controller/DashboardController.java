package com.hexaware.vehicleinsurance.controller;

import com.hexaware.vehicleinsurance.dto.DashboardStatsDTO;
import com.hexaware.vehicleinsurance.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/stats/{userId}")
    public ResponseEntity<DashboardStatsDTO> getStats(@PathVariable Long userId) {
        DashboardStatsDTO stats = dashboardService.getStatsForUser(userId);
        return ResponseEntity.ok(stats);
    }
}
