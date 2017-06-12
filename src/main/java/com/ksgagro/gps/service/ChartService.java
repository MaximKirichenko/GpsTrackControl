package com.ksgagro.gps.service;

import com.ksgagro.gps.dto.FuelChartDTO;
import com.ksgagro.gps.dto.MultiTrackResponseDto;

public interface ChartService {
	FuelChartDTO getFuelChartData(int numberTerminal, long from, long to);
	MultiTrackResponseDto buildMultiTrackResponseDto(int numberTerminal, long from, long to);
}
