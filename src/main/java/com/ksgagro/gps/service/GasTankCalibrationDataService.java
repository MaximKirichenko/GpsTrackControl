package com.ksgagro.gps.service;

import java.util.List;
import java.util.Map;

import com.ksgagro.gps.domain.GasTankCalibrationData;

public interface GasTankCalibrationDataService {
	double getFuelLevel(int terminalNumber, int gasTankPosition, int data);
	List<GasTankCalibrationData> getValues(int terminal);
	List<GasTankCalibrationData> getLeftTankCalibrationDataValues(int vehicleId, long data);
	List<GasTankCalibrationData> getRighTankCalibrationDatatValues(int vehicleId, long data);
	Double getFuelLevel(int data, List<GasTankCalibrationData> calibrationData);
	Map<Integer, Map<Integer, GasTankCalibrationData>> getCalibrationTables(List<Integer> terminalNumbers, long millisFrom);
}
