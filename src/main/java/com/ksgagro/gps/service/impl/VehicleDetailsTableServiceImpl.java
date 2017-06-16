package com.ksgagro.gps.service.impl;

import com.ksgagro.gps.domain.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksgagro.gps.domain.TrackEntity;
import com.ksgagro.gps.domain.VehicleDetailsTable;
import com.ksgagro.gps.service.GasTankCalibrationDataService;
import com.ksgagro.gps.service.TrackService;
import com.ksgagro.gps.service.TerminalService;
import com.ksgagro.gps.service.VehicleDetailsTableService;
import com.ksgagro.gps.service.VehicleService;

@Service
public class VehicleDetailsTableServiceImpl implements VehicleDetailsTableService{

	@Autowired private TrackService trackService;
	@Autowired private VehicleService vehicleService;
	@Autowired private TerminalService terminalService;
	@Autowired private GasTankCalibrationDataService calibrationDataService;

	@Override
	public VehicleDetailsTable createVehicleDetailsTableById(int vehicleId) {
		VehicleDetailsTable table = new VehicleDetailsTable();
		Terminal terminal = terminalService.getTerminalByVehicle(vehicleId);
		TrackEntity terminalDate = trackService.last(terminal.getImei());
		table.setTerminalDate(terminalDate);
		table.setVehicle(vehicleService.getVehicleById(vehicleId));
		table.setFuelLevelLeft(calibrationDataService.getFuelLevel(vehicleId, 1, terminalDate.getLeftGasTank()));
		table.setFuelLevelRight(calibrationDataService.getFuelLevel(vehicleId, 2, terminalDate.getRightGasTank()));
		table.setTerminal(terminal);
		return table;
	}

}
