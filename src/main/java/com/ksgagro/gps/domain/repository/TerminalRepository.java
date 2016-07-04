package com.ksgagro.gps.domain.repository;

import java.util.List;

import com.ksgagro.gps.domain.Terminal;

public interface TerminalRepository {
	Terminal getTerminalByVehicleId(int id);
	List<Terminal> getTerminals(List<Integer> terminalNumbers);
}
