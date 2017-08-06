package com.ksgagro.gps.service;

import java.util.List;

import com.ksgagro.gps.controller.JSON.TerminalDateJSON;
import com.ksgagro.gps.domain.Terminal;
import com.ksgagro.gps.dto.TerminalDateDTO;

public interface TerminalService {

    Terminal getTerminal(int id);

    List<Terminal> getTerminals(List<Integer> terminalNumbers);

    List<TerminalDateDTO> getTerminals();

    Terminal getTerminalByImei(String imei);

    List<Terminal> getTerminalsByVehicles(List<Integer> vehicleIds);

    Terminal getTerminalByVehicle(int vehicleId);

    List<Terminal> all();
}
