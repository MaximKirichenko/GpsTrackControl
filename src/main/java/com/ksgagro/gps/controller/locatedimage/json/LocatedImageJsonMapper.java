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
}
