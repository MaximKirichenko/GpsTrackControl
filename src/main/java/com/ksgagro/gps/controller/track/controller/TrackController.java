package com.ksgagro.gps.controller.track.controller;

import com.ksgagro.gps.controller.track.json.TrackJson;
import com.ksgagro.gps.controller.track.json.TrackJsonMapper;
import com.ksgagro.gps.dto.MultiTrackQuery;
import com.ksgagro.gps.dto.TrackBO;
import com.ksgagro.gps.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Maxim Kirichenko on 13.06.2017.
 */
@Controller
public class TrackController {

    @Autowired private TrackService trackService;
    @Autowired private TrackJsonMapper mapper;

    @RequestMapping(method = RequestMethod.POST, value = "/buildTracks")
    public @ResponseBody
    List<TrackJson> buildTracks(@RequestBody MultiTrackQuery trackQuery){
        List<TrackBO> date = trackService.tracks(trackQuery);
        return mapper.toJsons(date);
    }
}
