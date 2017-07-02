package com.ksgagro.gps.logic.image.persistence.mapper;

import com.ksgagro.gps.logic.image.persistence.model.LocatedImageEntity;
import com.ksgagro.gps.logic.image.persistence.model.LocatedImageInfoEntity;
import com.ksgagro.gps.logic.image.service.model.LocatedImageBytesBO;
import com.ksgagro.gps.logic.image.service.model.LocatedImageInfoBO;
import org.springframework.stereotype.Component;

/**
 * Created by Maxim Kirichenko on 02.05.17.
 */
@Component
public class LocatedImageEntityMapper {

    public LocatedImageInfoEntity toEntity(LocatedImageInfoBO bo) {
        LocatedImageInfoEntity ret = new LocatedImageInfoEntity();
        ret.setImageId(bo.getImageId());
        ret.setName(bo.getName());
        ret.setLongitude(bo.getCoordinate().getLongitude());
        ret.setLatitude(bo.getCoordinate().getLatitude());
        ret.setCreationTime(bo.getCreationTime());
        ret.setUpdatedTime(bo.getUpdatedTime());
        ret.setOwnerId(bo.getOwnerId());
        ret.setCreatorId(bo.getCreatorId());
        return ret;
    }

    public LocatedImageEntity toImage(LocatedImageBytesBO locatedImageBytesBO) {
        LocatedImageEntity ret = new LocatedImageEntity();
        ret.setImage(locatedImageBytesBO.getImage());
        return ret;
    }
}
