package com.ksgagro.gps.controller.track.json;

import com.ksgagro.gps.domain.TrackEntity;
import com.ksgagro.gps.domain.Vehicle;
import com.ksgagro.gps.dto.TrackBO;
import com.ksgagro.gps.service.TerminalService;
import com.ksgagro.gps.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Maxim Kirichenko on 13.06.2017.
 */
@Component
public class TrackJsonMapper {

    public TrackJson toJson(TrackBO bo){
        TrackJson trackJson = new TrackJson();
        trackJson.setVehicle(bo.getVehicle());
        trackJson.setTerminal(bo.getTerminal());
        trackJson.setLeftFuelLine(bo.getLeftFuelLine());
        trackJson.setRightFuelLine(bo.getRightFuelLine());
        trackJson.setTrackEntities(bo.getTrackEntities());
        trackJson.setTrackInfo(bo.getTrackInfo());
        trackJson.setStops(toStops(bo.getStopList(), bo.getVehicle()));
        return trackJson;
    }

    private List<StopJson> toStops(List<List<TrackEntity>> stopList, Vehicle vehicle) {
        List<StopJson> ret = new ArrayList<>();
        for(List<TrackEntity> stop: stopList)
            ret.add(toStop(stop, vehicle));
        return ret;
    }

    private StopJson toStop(List<TrackEntity> stop, Vehicle vehicle) {
        StopJson ret = new StopJson();
        ret.setDate(new Date(stop.get(0).getMessageDate()));
        ret.setLongitude(stop.get(0).getLongitude());
        ret.setLatitude(stop.get(0).getLatitude());
        ret.setDuration((stop.get(stop.size()-1).getMessageDate()-stop.get(0).getMessageDate())/1000);
        ret.setVehicle(vehicle);
        return ret;
    }

    public List<TrackJson> toJsons(List<TrackBO> bos) {
        List<TrackJson> ret = new ArrayList<>();
        for(TrackBO bo: bos)
            ret.add(toJson(bo));
        return ret;
    }
}
