package com.ksgagro.gps.logic.image.persistence;

import com.ksgagro.gps.logic.image.persistence.model.LocatedImageEntity;
import com.ksgagro.gps.logic.image.persistence.model.LocatedImageInfoEntity;
import javaxt.io.Image;

import java.util.List;

/**
 * Created by Maxim Kirichenko on 02.05.17.
 */
public interface LocatedImageManager {
    void save(LocatedImageInfoEntity imageBO);

    List<LocatedImageInfoEntity> list();

    LocatedImageInfoEntity get(String fileKey);

    Long save(LocatedImageEntity imageEntity);

    LocatedImageEntity getImage(Long imageId);
}
