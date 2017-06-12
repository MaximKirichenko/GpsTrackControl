package com.ksgagro.gps.logic.image.persistence;

import com.ksgagro.gps.logic.image.persistence.model.LocatedImageEntity;

import java.util.List;

/**
 * Created by Maxim Kirichenko on 02.05.17.
 */
public interface LocatedImageManager {
    void save(LocatedImageEntity imageBO);

    List<LocatedImageEntity> list();

    LocatedImageEntity get(String fileKey);
}
