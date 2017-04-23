package com.ksgagro.gps.controller.JSON;

import com.ksgagro.gps.domain.Coordinates;
import com.ksgagro.gps.domain.MapObjectFieldType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kma on 03.12.2016.
 */
public class MapObjectJSON {
    private int id;
    private String color;
    private List<Coordinates> latLngArray;
    private String fieldNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Coordinates> getLatLngArray() {
        return latLngArray;
    }

    public void setLatLngArray(List<Coordinates> latLngArray) {
        this.latLngArray = latLngArray;
    }

    public String getFieldNumber() {
        return fieldNumber;
    }

    public void setFieldNumber(String fieldNumber) {
        this.fieldNumber = fieldNumber;
    }


}
