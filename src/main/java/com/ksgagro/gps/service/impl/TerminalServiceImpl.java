package com.ksgagro.gps.service.impl;

import java.util.List;

import com.ksgagro.gps.controller.JSON.TerminalDateJSON;
import com.ksgagro.gps.dto.TerminalDateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksgagro.gps.domain.Terminal;
import com.ksgagro.gps.repository.TerminalRepository;
import com.ksgagro.gps.service.TerminalService;

@Service
public class TerminalServiceImpl implements TerminalService{

	@Autowired
	TerminalRepository terminalRepository;
	
	@Override
	public Terminal getTerminal(int id) {
		return terminalRepository.getTerminalByVehicleId(id);
	}

	@Override
	public List<Terminal> getTerminals(List<Integer> terminalNumbers) {
		return terminalRepository.getTerminals(terminalNumbers);
	}

    @Override
    public List<TerminalDateDTO> getTerminals() {
        return terminalRepository.getTerminals();
    }

}
