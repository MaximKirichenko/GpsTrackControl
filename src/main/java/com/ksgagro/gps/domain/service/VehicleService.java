package com.ksgagro.gps.domain.service;

import java.util.List;

import com.ksgagro.gps.domain.Vehicle;

public interface VehicleService {
	List<Vehicle> getList();
	Vehicle getVehicleById(int vehicleId);
	List<Vehicle> getListFromLocation(int locationId);
}
