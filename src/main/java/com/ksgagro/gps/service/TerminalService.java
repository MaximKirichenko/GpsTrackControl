package com.ksgagro.gps.service;

import java.util.List;

import com.ksgagro.gps.domain.Terminal;

public interface TerminalService {
	Terminal getTerminal(int id);
	List<Terminal> getTerminals(List<Integer> terminalNumbers);
}
