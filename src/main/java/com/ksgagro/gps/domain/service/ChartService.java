package com.ksgagro.gps.domain.service;

import com.ksgagro.gps.controller.dto.FuelChartDTO;

public interface ChartService {
	FuelChartDTO getFuelChartData(int numberTerminal, long from, long to);
}
