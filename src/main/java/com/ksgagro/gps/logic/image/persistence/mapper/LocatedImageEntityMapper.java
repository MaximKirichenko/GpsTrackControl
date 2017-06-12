package com.ksgagro.gps.logic.image.persistence.mapper;

import com.ksgagro.gps.logic.image.persistence.model.LocatedImageEntity;
import com.ksgagro.gps.logic.image.service.model.LocatedImageBO;
import org.springframework.stereotype.Component;

/**
 * Created by Maxim Kirichenko on 02.05.17.
 */
@Component
public class LocatedImageEntityMapper {

    public LocatedImageEntity toEntity(LocatedImageBO bo) {
        LocatedImageEntity ret = new LocatedImageEntity();
        ret.setData(bo.getData());
        ret.setName(bo.getName());
        ret.setLongitude(bo.getCoordinate().getLongitude());
        ret.setLatitude(bo.getCoordinate().getLatitude());
        ret.setCreationTime(bo.getCreationTime());
        ret.setUpdatedTime(bo.getUpdatedTime());
        ret.setOwnerId(bo.getOwnerId());
        ret.setCreatorId(bo.getCreatorId());
        return ret;
    }
}
