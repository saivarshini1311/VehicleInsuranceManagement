package com.hexaware.vehicleinsurance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import com.hexaware.vehicleinsurance.entity.Vehicle;
import com.hexaware.vehicleinsurance.repository.VehicleRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleService vehicleService;

    @Test
    void testAddVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand("Honda");

        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        Vehicle result = vehicleService.addVehicle(vehicle);
        assertEquals("Honda", result.getBrand());
    }

    @Test
    void testGetVehiclesByOwnerId() {
        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();

        when(vehicleRepository.findByOwnerId(1L)).thenReturn(List.of(vehicle1, vehicle2));

        List<Vehicle> vehicles = vehicleService.getVehiclesByOwnerId(1L);
        assertEquals(2, vehicles.size());
    }
}
