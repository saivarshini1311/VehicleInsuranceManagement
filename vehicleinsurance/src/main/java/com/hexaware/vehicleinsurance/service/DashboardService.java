package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.dto.DashboardStatsDTO;

public interface DashboardService {
    DashboardStatsDTO getStatsForUser(Long userId);
}
