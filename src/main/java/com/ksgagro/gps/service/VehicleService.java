package com.ksgagro.gps.service;

import java.util.List;

import com.ksgagro.gps.domain.Terminal;
import com.ksgagro.gps.domain.Vehicle;
import com.ksgagro.gps.domain.VehicleMenuItem;

public interface VehicleService {

	Vehicle getVehicleById(int vehicleId);

	Vehicle getVehicleByTerminalNumber(int terminalNumber);

	List<Vehicle> getFilteredList();

	List<Vehicle> getListFromLocation(int locationId);

	List<VehicleMenuItem> getVehicleMenuItems();

	List<Vehicle> getVehicles(List<Integer> terminalNumbers);

	List<Vehicle> getList();

    List<Vehicle> getVehiclesByTerminalNumbers(List<Terminal> terminals);
}
