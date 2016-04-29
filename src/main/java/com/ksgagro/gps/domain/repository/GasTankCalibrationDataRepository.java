package com.ksgagro.gps.domain.repository;

import java.util.List;

import com.ksgagro.gps.domain.GasTankCalibrationData;

public interface GasTankCalibrationDataRepository {
	List<GasTankCalibrationData> getValues();
	List<GasTankCalibrationData> getValues(int terminal);
	List<GasTankCalibrationData> getLeftValues(int terminal);
	List<GasTankCalibrationData> getRightValues(int terminal);
}
