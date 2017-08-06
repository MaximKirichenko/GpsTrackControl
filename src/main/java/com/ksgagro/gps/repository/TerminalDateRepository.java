package com.ksgagro.gps.repository;

import java.util.List;

import com.ksgagro.gps.domain.LastDeviceDateEntity;
import com.ksgagro.gps.domain.TrackEntity;

public interface TerminalDateRepository {
	List<TrackEntity> list(long from, long to, String imei);

	List<TrackEntity> tracksByTerminalNumbers(long millisFrom, long millisTo, List<Integer> terminalNumbers);

	List<TrackEntity> tracksByImeis(long millisFrom, long millisTo, List<String> imeis);

	TrackEntity getLastSignal(String imei);

	List<LastDeviceDateEntity> getLastSignals();
}
