package com.ksgagro.gps.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksgagro.gps.domain.VehicleGroup;
import com.ksgagro.gps.domain.repository.VehicleGroupRepository;
import com.ksgagro.gps.domain.service.VehicleGroupService;

@Service
public class VehicleGroupImpl implements VehicleGroupService{
	@Autowired
	VehicleGroupRepository vehicleGroupRepository;
	
	@Override
	public List<VehicleGroup> getList() {
		// TODO Auto-generated method stub
		return vehicleGroupRepository.getList();
	}

}
