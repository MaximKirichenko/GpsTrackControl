package com.ksgagro.gps.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksgagro.gps.controller.dto.CarSignalDTO;
import com.ksgagro.gps.domain.repository.CarSignalRepository;
import com.ksgagro.gps.domain.service.CarSignalService;

@Service
public class CarSignalServiceImpl implements CarSignalService{
	
	@Autowired
	CarSignalRepository carSignal;
	
	public List<CarSignalDTO> gelLastSignal() {
		return carSignal.gelLastSignal();
	}
	

}
