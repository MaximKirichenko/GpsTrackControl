package com.ksgagro.gps.service.impl;

import com.ksgagro.gps.domain.Terminal;
import com.ksgagro.gps.domain.TrackEntity;
import com.ksgagro.gps.domain.Vehicle;
import com.ksgagro.gps.domain.VehicleMenuItem;
import com.ksgagro.gps.repository.VehicleRepository;
import com.ksgagro.gps.service.RoleFilter;
import com.ksgagro.gps.service.TrackService;
import com.ksgagro.gps.service.TerminalService;
import com.ksgagro.gps.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService{
	
	@Autowired
	VehicleRepository vehicleRepository;
	
	@Autowired
	TrackService trackService;
	
	@Autowired
	TerminalService terminalService;
	
	@Autowired
	RoleFilter roleFilter;
	
	public List<Vehicle> getFilteredList() {
		List<Vehicle> vehicles = vehicleRepository.getList();
		//vehicles.stream().forEach(System.out::println);
		List<Vehicle> result = new ArrayList<>();
		List<Integer> permistVehicelsId = roleFilter.getVehiclesList(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		for(Integer permistId: permistVehicelsId){
			for(Vehicle vehicle: vehicles){
				if(vehicle.getId()==permistId){
					result.add(vehicle);
				}
			}
			
		}
		
		return result;
	}
	
	public List<Vehicle> getList(){
		return vehicleRepository.getList();
	}
 

	@Override
	public Vehicle getVehicleById(int vehicleId) {
		return vehicleRepository.getVehicleById(vehicleId);
	}

    @Override
    public Vehicle getVehicleByTerminalNumber(int terminalNumber) {
		return vehicleRepository.getVehicleById(terminalService.getTerminal(terminalNumber).getVehicle());
    }

    @Override
	public List<Vehicle> getListFromLocation(int locationId) {
		return vehicleRepository.getListFromLocation(locationId);
	}

	@Override
	public List<VehicleMenuItem> getVehicleMenuItems() {
		
		
		List<Vehicle> listVehicle = getFilteredList();
		List<TrackEntity> listOfLastSignals = trackService.last();
		List<VehicleMenuItem> vehicleMenuItems = new ArrayList<>();
		
		for(Vehicle vehicle: listVehicle){
			try{
				TrackEntity terminalDate = getItemByVehicleFromLastSignalDate(vehicle, listOfLastSignals);
				vehicleMenuItems.add(new VehicleMenuItem(vehicle, terminalDate));
			}catch(RuntimeException exception){
				System.err.println(exception.getMessage());
				//exception.printStackTrace(new PrintStream(System.out));
			}
		}
		return vehicleMenuItems;
	}
	
	private TrackEntity getItemByVehicleFromLastSignalDate(Vehicle vehicle, List<TrackEntity> terminalDates){
		Terminal terminal = terminalService.getTerminalByVehicle(vehicle.getId());
		if(terminal==null){
			throw new NullPointerException("No terminal on vehicle with ID: " + vehicle.getId());
		}
		for(TrackEntity date: terminalDates){
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

	@Override
	public List<Vehicle> getVehiclesByTerminalNumbers(List<Terminal> terminals) {
		return vehicleRepository.getVehicles(terminalNumbers(terminals));
	}

	private List<Integer> terminalNumbers(List<Terminal> terminals) {
		List<Integer> ret = new ArrayList<>();
		for(Terminal terminal: terminals)
			ret.add(terminal.getId());
		return ret;
	}

}
