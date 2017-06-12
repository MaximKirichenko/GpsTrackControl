package com.ksgagro.gps.logic.image.service.model;

import com.ksgagro.gps.logic.common.model.Coordinate;
import com.ksgagro.gps.logic.common.model.OwnedBO;

/**
 * Created by Maxim Kirichenko on 02.05.17.
 */
public class LocatedImageBO extends OwnedBO {

    private String name;
    private byte[] data;
    private Coordinate coordinate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
