package com.ksgagro.gps.domain.service;

import java.util.List;

import com.ksgagro.gps.domain.GasTankCalibrationData;

public interface GasTankCalibrationDataService {
	double getFuelLevel(int terminalNumber, int gasTankPosition, int data);
	List<GasTankCalibrationData> getValues(int terminal);
	List<GasTankCalibrationData> getLeftValues(int terminal);
	List<GasTankCalibrationData> getRightValues(int terminal);
	Double getFuelLevel(int data, List<GasTankCalibrationData> calibrationData);
}
