package com.ksgagro.gps.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksgagro.gps.domain.Terminal;
import com.ksgagro.gps.domain.repository.TerminalRepository;
import com.ksgagro.gps.domain.service.TerminalService;

@Service
public class TerminalServiceImpl implements TerminalService{

	@Autowired
	TerminalRepository terminalRepository;
	
	@Override
	public Terminal getTerminal(int id) {
		return terminalRepository.getTerminalById(id);
	}

}
