package com.hexaware.vehicleinsurance;

import org.springframework.boot.SpringApplication;

public class TestVehicleinsuranceApplication {

	public static void main(String[] args) {
		SpringApplication.from(VehicleinsuranceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
