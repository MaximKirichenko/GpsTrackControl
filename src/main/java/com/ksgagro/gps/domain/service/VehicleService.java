package com.ksgagro.gps.domain.service;

import java.util.List;

import com.ksgagro.gps.domain.Vehicle;
import com.ksgagro.gps.domain.VehicleMenuItem;

public interface VehicleService {
	List<Vehicle> getList();
	Vehicle getVehicleById(int vehicleId);
	List<Vehicle> getListFromLocation(int locationId);
	List<VehicleMenuItem> getVehicleMenuItems();
	List<Vehicle> getVehicles(List<Integer> terminalNumbers);
}
