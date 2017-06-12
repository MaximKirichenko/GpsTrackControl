package com.ksgagro.gps.service;

import java.util.List;

import com.ksgagro.gps.domain.Vehicle;
import com.ksgagro.gps.domain.VehicleMenuItem;

public interface VehicleService {
	List<Vehicle> getFilteredList();
	Vehicle getVehicleById(int vehicleId);
	List<Vehicle> getListFromLocation(int locationId);
	List<VehicleMenuItem> getVehicleMenuItems();
	List<Vehicle> getVehicles(List<Integer> terminalNumbers);
	List<Vehicle> getList();
}
