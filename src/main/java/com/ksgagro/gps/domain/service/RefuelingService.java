package com.ksgagro.gps.domain.service;

import java.util.List;

import com.ksgagro.gps.domain.GasTankCalibrationData;
import com.ksgagro.gps.domain.Refueling;
import com.ksgagro.gps.domain.TerminalDate;

public interface RefuelingService {
	List<Refueling> getRefuelings(List<TerminalDate> datasFromTerminal, List<GasTankCalibrationData> calibrationDatas);
}
