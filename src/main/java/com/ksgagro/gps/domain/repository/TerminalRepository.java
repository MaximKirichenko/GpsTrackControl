package com.ksgagro.gps.domain.repository;

import com.ksgagro.gps.domain.Terminal;

public interface TerminalRepository {
	Terminal getTerminalByVehicleId(int id);
}
