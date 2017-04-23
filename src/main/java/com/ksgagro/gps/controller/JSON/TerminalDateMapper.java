package com.ksgagro.gps.controller.JSON;

import com.ksgagro.gps.dto.TerminalDateDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Maxim Kirichenko on 24.02.17.
 */
@Component
public class TerminalDateMapper {

    public TerminalDateJSON toJSON(TerminalDateDTO dto) {
        TerminalDateJSON ret = new TerminalDateJSON();
        ret.setDate(new Date(dto.getDate()));
        ret.setImei(dto.getImei());
        return ret;
    }

    public List<TerminalDateJSON> toJSONs(List<TerminalDateDTO> terminals) {
        List<TerminalDateJSON> ret = new ArrayList<>();
        System.out.println(terminals);
        for(TerminalDateDTO terminal: terminals)
            ret.add(toJSON(terminal));
        return ret;
    }
}
