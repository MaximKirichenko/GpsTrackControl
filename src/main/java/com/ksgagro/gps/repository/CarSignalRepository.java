package com.ksgagro.gps.repository;

import java.util.List;

import com.ksgagro.gps.dto.CarSignalDTO;

public interface CarSignalRepository {
	List<CarSignalDTO> gelLastSignal();

}
