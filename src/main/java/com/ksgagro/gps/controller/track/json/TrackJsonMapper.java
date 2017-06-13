package com.ksgagro.gps.controller.track.json;

import com.ksgagro.gps.dto.TrackBO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Maxim Kirichenko on 13.06.2017.
 */
@Component
public class TrackJsonMapper {
    public TrackJson toJson(TrackBO bo){
        TrackJson trackJson = new TrackJson();
        return trackJson;
    }

    public List<TrackJson> toJsons(List<TrackBO> date) {
        return null;
    }
}
