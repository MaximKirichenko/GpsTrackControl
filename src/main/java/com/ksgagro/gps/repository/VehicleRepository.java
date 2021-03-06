package com.ksgagro.gps.repository;

import java.util.List;

import com.ksgagro.gps.domain.Vehicle;

public interface VehicleRepository {
	List<Vehicle> getList();
	List<Vehicle> getListFromGroup(int grouId);
	List<Vehicle> getListFromLocation(int locationId);
	Vehicle getVehicleById(int vehicleId);
	List<Vehicle> getVehicles(List<Integer> terminalNumbers);

}
