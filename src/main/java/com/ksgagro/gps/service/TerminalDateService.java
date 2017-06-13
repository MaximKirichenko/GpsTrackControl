package com.ksgagro.gps.service;

import java.util.Date;
import java.util.List;

import com.ksgagro.gps.dto.MultiTrackQuery;
import com.ksgagro.gps.dto.TrackBO;
import com.ksgagro.gps.domain.Refueling;
import com.ksgagro.gps.domain.TrackEntity;

public interface TerminalDateService {

	List<TrackEntity> tracks(long millisFrom, long millisTo, int terminalNumber);

	List<TrackBO> tracks(MultiTrackQuery query);

	List<TrackEntity> tracks(String dateFrom, String timeFrom, String dateTo, String timeTo, int terminalNumber);

	double getPathLength(List<TrackEntity> terminalDate);

	List<TrackEntity> filterData(List<TrackEntity> inputList);

	double getCanConsumption(List<TrackEntity> terminalDates);

	Date getStartMovementTime(List<TrackEntity> terminalDate);

	Date getFinishMovementTime(List<TrackEntity> terminalDate);

	List<List<TrackEntity>> stopList(long millisFrom, long millisTo, int terminalNumber);

	List<Refueling> getRefulingDate(List<List<TrackEntity>> stops, int terminalNumber);

	List<TrackEntity> last();

	TrackEntity last(int vehicleId);

	TrackEntity last(String imei);
}
