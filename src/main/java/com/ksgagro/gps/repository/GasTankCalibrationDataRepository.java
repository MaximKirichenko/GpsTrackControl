package com.ksgagro.gps.repository;

import java.util.List;

import com.ksgagro.gps.domain.GasTankCalibrationData;

public interface GasTankCalibrationDataRepository {
	List<GasTankCalibrationData> getValues();
	List<GasTankCalibrationData> getCalibrationDataByVehicleId(int terminal);
	List<GasTankCalibrationData> getLeftTankCalibrationDataValues(int terminal, long date);
	List<GasTankCalibrationData> getRighTankCalibrationDatatValues(int terminal, long date);
	List<GasTankCalibrationData> getTables(List<Integer> terminalNumbers, long millisFrom);
}
