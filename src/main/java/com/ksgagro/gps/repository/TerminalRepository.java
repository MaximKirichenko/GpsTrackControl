package com.ksgagro.gps.repository;

import com.ksgagro.gps.domain.Terminal;
import com.ksgagro.gps.dto.TerminalDateDTO;

import java.util.List;

public interface TerminalRepository {

    Terminal get(int id);

    List<Terminal> getTerminals(List<Integer> terminalNumbers);

    List<TerminalDateDTO> getTerminals();
}
