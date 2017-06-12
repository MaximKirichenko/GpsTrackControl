package com.ksgagro.gps.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksgagro.gps.domain.TerminalDate;
import com.ksgagro.gps.domain.VehicleDetailsTable;
import com.ksgagro.gps.service.GasTankCalibrationDataService;
import com.ksgagro.gps.service.TerminalDateService;
import com.ksgagro.gps.service.TerminalService;
import com.ksgagro.gps.service.VehicleDetailsTableService;
import com.ksgagro.gps.service.VehicleService;

@Service
public class VehicleDetailsTableServiceImpl implements VehicleDetailsTableService{
	@Autowired
	TerminalDateService terminalDateService;
	
	@Autowired
	private VehicleService vehicleService;
	
	@Autowired
	private GasTankCalibrationDataService calibrationDataService;
	
	@Autowired
	private TerminalService terminalService;
	
	@Override
	public VehicleDetailsTable createVehicleDetailsTableById(int vehicleId) {
		VehicleDetailsTable table = new VehicleDetailsTable();
		initVehicleDetailsTable(table, vehicleId);
		return table;
	}
	
	private void initVehicleDetailsTable(VehicleDetailsTable table, int vehicleId){
		TerminalDate terminalDate = terminalDateService.getLastSignal(vehicleId);	
		table.setTerminalDate(terminalDateService.getLastSignal(vehicleId));
		table.setVehicle(vehicleService.getVehicleById(vehicleId));
		table.setFuelLevelLeft(calibrationDataService.getFuelLevel(vehicleId, 1, terminalDate.getLeftGasTank()));
		table.setFuelLevelRight(calibrationDataService.getFuelLevel(vehicleId, 2, terminalDate.getRightGasTank()));
		table.setTerminal(terminalService.getTerminal(vehicleId));
	}

}
