package com.ksgagro.gps.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksgagro.gps.domain.Vehicle;
import com.ksgagro.gps.domain.repository.VehicleRepository;
import com.ksgagro.gps.domain.service.VehicleService;

@Service
public class VehicleServiceImpl implements VehicleService{
	
	@Autowired
	VehicleRepository vehicleRepository;
	
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

}
