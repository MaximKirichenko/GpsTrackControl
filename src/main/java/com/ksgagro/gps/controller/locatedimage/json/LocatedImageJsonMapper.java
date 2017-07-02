package com.ksgagro.gps.controller.locatedimage.json;

import com.ksgagro.gps.logic.image.service.model.LocatedImageBO;
import com.ksgagro.gps.logic.image.service.model.LocatedImageBytesBO;
import com.ksgagro.gps.logic.image.service.model.LocatedImageInfoBO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxim Kirichenko on 14.05.17.
 */
@Component
public class LocatedImageJsonMapper {

    public List<LocatedImagePointJSON> toLocatedImagePointJsons(List<LocatedImageInfoBO> list) {
        List<LocatedImagePointJSON> ret = new ArrayList<>();
        for(LocatedImageInfoBO bo: list)
            ret.add(toLocatedImagePointJson(bo));
        return ret;
    }

    public LocatedImagePointJSON toLocatedImagePointJson(LocatedImageInfoBO bo){
        return new LocatedImagePointJSON(){{
            setLongitude(bo.getCoordinate().getLongitude());
            setLatitude(bo.getCoordinate().getLatitude());
            setFileKey(bo.getName());
        }};

    }

    public LocatedImageJson toLocatedImageJson(LocatedImageBO bo) {
        LocatedImageJson ret = new LocatedImageJson();
        LocatedImageInfoBO info = bo.getLocatedImageInfo();
        LocatedImageBytesBO bytes = bo.getLocatedImageBytes();
        if (info != null) {
            ret.setOwner("ADMIN");
            ret.setCreationTime(info.getCreationTime());
            ret.setName(info.getName());
        }
        if(bytes != null)
            ret.setData(bytes.getImage());
        return ret;
    }

    public List<LocatedImageMenuItemJson> toJSONs(List<LocatedImageInfoBO> list) {
        List<LocatedImageMenuItemJson> ret = new ArrayList<>();
        for(LocatedImageInfoBO bo: list)
            ret.add(toJSON(bo));
        return ret;
    }

    private LocatedImageMenuItemJson toJSON(LocatedImageInfoBO bo) {
        LocatedImageMenuItemJson ret = new LocatedImageMenuItemJson();
        String name = bo.getCoordinate().getLongitude() + " " + bo.getCoordinate().getLatitude() + " " + bo.getCreationTime();
        ret.setName(name);
        ret.setKey(bo.getName());
        return ret;
    }
}
