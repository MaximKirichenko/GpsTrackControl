package com.ksgagro.gps.domain.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksgagro.gps.domain.Terminal;
import com.ksgagro.gps.domain.TerminalDate;
import com.ksgagro.gps.domain.Vehicle;
import com.ksgagro.gps.domain.VehicleMenuItem;
import com.ksgagro.gps.domain.repository.VehicleRepository;
import com.ksgagro.gps.domain.service.TerminalDateService;
import com.ksgagro.gps.domain.service.TerminalService;
import com.ksgagro.gps.domain.service.VehicleService;

@Service
public class VehicleServiceImpl implements VehicleService{
	
	@Autowired
	VehicleRepository vehicleRepository;
	
	@Autowired
	TerminalDateService terminalDateService;
	
	@Autowired
	TerminalService terminalService;
	
	public List<Vehicle> getList() {
		return vehicleRepository.getList();
	}

	@Override
	public Vehicle getVehicleById(int vehicleId) {
		return vehicleRepository.getVehicleById(vehicleId);
	}

	@Override
	public List<Vehicle> getListFromLocation(int locationId) {
		return vehicleRepository.getListFromLocation(locationId);
	}

	@Override
	public List<VehicleMenuItem> getVehicleMenuItems() {
		
		
		List<Vehicle> listVehicle = getList();
		List<TerminalDate> listOfLastSignals = terminalDateService.getLastSignals();
		List<VehicleMenuItem> vehicleMenuItems = new ArrayList<>();
		
		for(Vehicle vehicle: listVehicle){
			try{
				TerminalDate terminalDate = getItemByVehicleFromLastSignalDate(vehicle, listOfLastSignals);
				vehicleMenuItems.add(new VehicleMenuItem(vehicle, terminalDate));
			}catch(RuntimeException exception){
				System.err.println(exception.getMessage());
				//exception.printStackTrace(new PrintStream(System.out));
			}
		}
		return vehicleMenuItems;
	}
	
	private TerminalDate getItemByVehicleFromLastSignalDate(Vehicle vehicle, List<TerminalDate> terminalDates){
		Terminal terminal = terminalService.getTerminal(vehicle.getId());
		if(terminal==null){
			throw new NullPointerException("No terminal on vehicle with ID: " + vehicle.getId());
		}
		for(TerminalDate date: terminalDates){
			if(terminal.getImei().equals(date.getImei())){
				return date;
			}
				
		}
		throw new RuntimeException("Not dates for vehicle with id: " + vehicle.getId());
	}

	@Override
	public List<Vehicle> getVehicles(List<Integer> terminalNumbers) {
		
		return vehicleRepository.getVehicles(terminalNumbers);
	}

}
