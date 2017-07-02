package com.ksgagro.gps.logic.image.service.model;

import com.ksgagro.gps.logic.common.model.Coordinate;
import com.ksgagro.gps.logic.common.model.OwnedBO;

import java.util.Date;

/**
 * Created by Maxim Kirichenko on 02.05.17.
 */
public class LocatedImageInfoBO extends OwnedBO {

    private String name;
    private Long imageId;
    private Coordinate coordinate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

}
