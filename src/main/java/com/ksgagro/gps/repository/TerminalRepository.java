package com.ksgagro.gps.repository;

import com.ksgagro.gps.domain.Terminal;
import com.ksgagro.gps.dto.TerminalDateDTO;

import java.util.List;

public interface TerminalRepository {

    Terminal get(int id);

    Terminal get(String imei);

    List<Terminal> getTerminals(List<Integer> terminalNumbers);

    List<Terminal> byVehicles(List<Integer> vehicleIds);

    List<TerminalDateDTO> getTerminals();

    Terminal byVehicle(int vehicleId);

    List<Terminal> all();
}
