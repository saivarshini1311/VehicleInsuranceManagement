package com.hexaware.vehicleinsurance.service;

import com.hexaware.vehicleinsurance.dto.VehicleDTO;
import com.hexaware.vehicleinsurance.entity.Policy;
import com.hexaware.vehicleinsurance.entity.User;
import com.hexaware.vehicleinsurance.entity.Vehicle;
import com.hexaware.vehicleinsurance.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PolicyRepository policyRepository;

    @Override
    public Vehicle addVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with ID: " + id));
    }

    @Override
    public List<Vehicle> getVehiclesByOwnerId(Long ownerId) {
        return vehicleRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle updateVehicle(Long id, VehicleDTO dto) {
        Vehicle existingVehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with ID: " + id));

        existingVehicle.setRegistrationNumber(dto.getRegistrationNumber());
        existingVehicle.setType(dto.getType());
        existingVehicle.setBrand(dto.getBrand());
        existingVehicle.setModel(dto.getModel());
        existingVehicle.setPurchaseDate(dto.getPurchaseDate());
        existingVehicle.setOwnerId(dto.getOwnerId());
        existingVehicle.setYear(dto.getYear());

        return vehicleRepository.save(existingVehicle);
    }

    @Override
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }
    
    @Override
    public List<VehicleDTO> getAllVehicleDetails() {
        List<Vehicle> vehicles = vehicleRepository.findAll();

        return vehicles.stream().map(vehicle -> {
            VehicleDTO dto = new VehicleDTO();
            dto.setId(vehicle.getId());
            dto.setBrand(vehicle.getBrand());
            dto.setModel(vehicle.getModel());
            dto.setRegistrationNumber(vehicle.getRegistrationNumber());
            dto.setType(vehicle.getType());
            dto.setYear(vehicle.getYear());
            dto.setPurchaseDate(vehicle.getPurchaseDate());
            dto.setOwnerId(vehicle.getOwnerId());

            // ✅ Fix owner name mapping
            if (vehicle.getOwnerId() != null) {
                Optional<User> user = userRepository.findById(vehicle.getOwnerId());
                dto.setOwnerName(user.map(User::getName).orElse("(Unknown)"));
            } else {
                dto.setOwnerName("(Unknown)");
            }
            
            Policy policy = policyRepository.findByVehicleId(vehicle.getId());
            dto.setPolicyStatus(policy != null ? policy.getStatus() : "Not Issued");

            return dto;
        }).collect(Collectors.toList());
    }

}

