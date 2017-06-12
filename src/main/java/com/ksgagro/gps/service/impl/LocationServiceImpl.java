package com.ksgagro.gps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksgagro.gps.domain.Location;
import com.ksgagro.gps.repository.LocationRepository;
import com.ksgagro.gps.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService{

	@Autowired
	LocationRepository locationRepository;
	
	@Override
	public List<Location> getList() {
		return locationRepository.getList();
	}

}
