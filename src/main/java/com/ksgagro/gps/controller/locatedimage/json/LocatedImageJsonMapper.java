package com.ksgagro.gps.controller.locatedimage.json;

import com.ksgagro.gps.logic.image.service.model.LocatedImageBO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxim Kirichenko on 14.05.17.
 */
@Component
public class LocatedImageJsonMapper {

    public List<LocatedImagePointJSON> toLocatedImagePointJsons(List<LocatedImageBO> list) {
        List<LocatedImagePointJSON> ret = new ArrayList<>();
        for(LocatedImageBO bo: list)
            ret.add(toLocatedImagePointJson(bo));
        return ret;
    }

    public LocatedImagePointJSON toLocatedImagePointJson(LocatedImageBO bo){
        return new LocatedImagePointJSON(){{
            setLongitude(bo.getCoordinate().getLongitude());
            setLatitude(bo.getCoordinate().getLatitude());
            setFileKey(bo.getName());
        }};

    }

    public LocatedImageJson toLocatedImageJson(LocatedImageBO bo) {

        return new LocatedImageJson(){{
            setCreationTime(bo.getCreationTime());
            setOwner("ADMIN");
            setData(bo.getData());
        }};
    }
}
