package com.ksgagro.gps.service;

import java.util.List;

import com.ksgagro.gps.domain.GasTankCalibrationData;
import com.ksgagro.gps.domain.Refueling;
import com.ksgagro.gps.domain.TrackEntity;

public interface RefuelingService {
	List<Refueling> getRefuelings(List<TrackEntity> datasFromTerminal, List<GasTankCalibrationData> calibrationDatas);
}
