package com.ksgagro.gps.domain.service;

import java.util.List;

import com.ksgagro.gps.controller.dto.CarSignalDTO;


public interface CarSignalService {
	List<CarSignalDTO> gelLastSignal();
}
