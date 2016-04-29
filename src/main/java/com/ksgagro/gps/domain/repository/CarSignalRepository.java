package com.ksgagro.gps.domain.repository;

import java.util.List;

import com.ksgagro.gps.controller.dto.CarSignalDTO;

public interface CarSignalRepository {
	List<CarSignalDTO> gelLastSignal();

}
