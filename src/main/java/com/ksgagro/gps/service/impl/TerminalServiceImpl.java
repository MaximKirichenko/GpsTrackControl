package com.ksgagro.gps.service.impl;

import java.util.List;

import com.ksgagro.gps.dto.TerminalDateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksgagro.gps.domain.Terminal;
import com.ksgagro.gps.repository.TerminalRepository;
import com.ksgagro.gps.service.TerminalService;

@Service
public class TerminalServiceImpl implements TerminalService{

	@Autowired TerminalRepository terminalRepository;
	
	@Override
	public Terminal getTerminal(int id) {
		return terminalRepository.get(id);
	}

	@Override
	public List<Terminal> getTerminals(List<Integer> terminalNumbers) {
		return terminalRepository.getTerminals(terminalNumbers);
	}

    @Override
    public List<TerminalDateDTO> getTerminals() {
        return terminalRepository.getTerminals();
    }

	@Override
	public Terminal getTerminalByImei(String imei) {
		return terminalRepository.get(imei);
	}

    @Override
    public List<Terminal> getTerminalsByVehicles(List<Integer> vehicleIds) {
		return terminalRepository.byVehicles(vehicleIds);
    }

    @Override
    public Terminal getTerminalByVehicle(int vehicleId) {
        return terminalRepository.byVehicle(vehicleId);
    }

}
