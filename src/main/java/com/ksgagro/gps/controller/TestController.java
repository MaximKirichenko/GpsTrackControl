package com.ksgagro.gps.controller;

import com.ksgagro.gps.tracks.menu.dto.VehicleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by kma on 29.09.2016.
 */
@Controller
public class TestController {
    @Autowired
    VehicleDTO vehicleDTO;

    @RequestMapping("/time")
    @ResponseBody VehicleDTO testStatus() {
        return vehicleDTO;
    }
}
