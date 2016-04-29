package com.ksgagro.gps.domain.repository;

import java.util.List;

import com.ksgagro.gps.domain.Vehicle;

public interface VehicleRepository {
	List<Vehicle> getList();
	List<Vehicle> getListFromGroup(int grouId);
	List<Vehicle> getListFromLocation(int locationId);
	Vehicle getVehicleByNumberTerminal(int terminalNumber);

}
