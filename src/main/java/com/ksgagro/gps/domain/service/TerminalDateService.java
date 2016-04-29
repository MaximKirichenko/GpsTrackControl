package com.ksgagro.gps.domain.service;

import java.util.Date;
import java.util.List;

import com.ksgagro.gps.domain.Refueling;
import com.ksgagro.gps.domain.TerminalDate;

public interface TerminalDateService {
	public List<TerminalDate> getVehicleFromPeriod(long millisFrom, long millisTo, int terminalNumber);
	
	public List<TerminalDate> getVehicleFromPeriod(
			String dateFrom, String timeFrom, String dateTo, String timeTo, int terminalNumber);
	public double getPathLength(List<TerminalDate> terminalDate);
	public Date getLastSignalDate(int terminalNumber);
	public TerminalDate getLastSignal(int terminalNumber);
	public List<TerminalDate> filterData(List<TerminalDate> inputList);
	public double getCanConsumption(List<TerminalDate> terminalDates);
	public Date getStartMovementTime(List<TerminalDate> terminalDate);
	public Date getFinishMovementTime(List<TerminalDate> terminalDate);
	public List<List<TerminalDate>> getStops(long millisFrom, long millisTo, int terminalNumber);
	public List<Refueling> getRefulingDate(List<List<TerminalDate>> stops, int terminalNumber);

}