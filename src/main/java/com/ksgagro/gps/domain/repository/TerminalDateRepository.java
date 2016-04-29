package com.ksgagro.gps.domain.repository;

import java.util.List;

import com.ksgagro.gps.domain.TerminalDate;

public interface TerminalDateRepository {
	List<TerminalDate> getListFromPeriod(long from, long to, String imei);
	TerminalDate getLastSignal(String imei);
}
