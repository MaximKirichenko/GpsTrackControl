package com.ksgagro.gps.domain.repository;

import java.util.List;

import com.ksgagro.gps.domain.TerminalDate;

public interface TerminalDateRepository {
	List<TerminalDate> getListFromPeriod(long from, long to, String imei);
	List<TerminalDate> getTerminalDateAboutVehiclesFromPeriod(long millisFrom, long millisTo,
			List<Integer> terminalNumbers);
	TerminalDate getLastSignal(String imei);
	List<TerminalDate> getLastSignals();
}
