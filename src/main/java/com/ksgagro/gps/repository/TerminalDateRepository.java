package com.ksgagro.gps.repository;

import java.util.List;

import com.ksgagro.gps.domain.TrackEntity;

public interface TerminalDateRepository {
	List<TrackEntity> list(long from, long to, String imei);
	List<TrackEntity> tracks(long millisFrom, long millisTo,
                             List<Integer> terminalNumbers);
	TrackEntity getLastSignal(String imei);
	List<TrackEntity> getLastSignals();
}
