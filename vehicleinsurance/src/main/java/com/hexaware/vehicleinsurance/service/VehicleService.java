package com.hexaware.vehicleinsurance.service;


import com.hexaware.vehicleinsurance.entity.Vehicle;
import com.hexaware.vehicleinsurance.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle addVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getVehiclesByOwnerId(Long userId) {
        return vehicleRepository.findByOwnerId(userId);
    }
}

