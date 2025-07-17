package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.dto.VehicleDTO;
import com.hexaware.vehicleinsurance.entity.Vehicle;

import java.util.List;

public interface VehicleService {
    Vehicle addVehicle(Vehicle vehicle);
    Vehicle getVehicleById(Long id);
    List<Vehicle> getVehiclesByOwnerId(Long ownerId);
    List<Vehicle> getAllVehicles();
    Vehicle updateVehicle(Long id, VehicleDTO dto);
    void deleteVehicle(Long id);
    List<VehicleDTO> getAllVehicleDetails();

}
