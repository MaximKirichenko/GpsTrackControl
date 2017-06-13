package com.ksgagro.gps.service;

import com.ksgagro.gps.dto.FuelChartDTO;
import com.ksgagro.gps.dto.TrackBO;

public interface ChartService {
	FuelChartDTO getFuelChartData(int numberTerminal, long from, long to);
	TrackBO buildMultiTrackResponseDto(int numberTerminal, long from, long to);
}
