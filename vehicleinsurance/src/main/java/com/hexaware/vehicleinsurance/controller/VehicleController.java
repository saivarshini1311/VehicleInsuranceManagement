package com.hexaware.vehicleinsurance.controller;

import com.hexaware.vehicleinsurance.dto.VehicleDTO;
import com.hexaware.vehicleinsurance.entity.Vehicle;
import com.hexaware.vehicleinsurance.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/register")
    public ResponseEntity<Vehicle> registerVehicle(@RequestBody VehicleDTO dto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber(dto.getRegistrationNumber());
        vehicle.setType(dto.getType());
        vehicle.setBrand(dto.getBrand());
        vehicle.setModel(dto.getModel());
        vehicle.setPurchaseDate(dto.getPurchaseDate());
        vehicle.setOwnerId(dto.getOwnerId());
        vehicle.setYear(dto.getYear());
        Vehicle saved = vehicleService.addVehicle(vehicle);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Vehicle>> getVehiclesByOwner(@PathVariable Long ownerId) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByOwnerId(ownerId);
        return ResponseEntity.ok(vehicles);
    }
}
