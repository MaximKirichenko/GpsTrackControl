package com.ksgagro.gps.domain;

import java.io.Serializable;
import java.util.List;

public class MapObjectField implements Serializable {

    private static final long serialVersionUID = -5044583500094712978L;
    private int id;
    private List<Coordinates> latLngArray;
    private String fieldNumber;
    private int fieldEnterprice;
    private double fieldArea;
    private Long type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getFieldEnterprice() {
        return fieldEnterprice;
    }

    public void setFieldEnterprice(int fieldEnterprice) {
        this.fieldEnterprice = fieldEnterprice;
    }

    public double getFieldArea() {
        return fieldArea;
    }

    public void setFieldArea(double fieldArea) {
        this.fieldArea = fieldArea;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MapObjectField{" +
                "id=" + id +
                ", latLngArray=" + latLngArray +
                ", fieldNumber='" + fieldNumber + '\'' +
                ", fieldEnterprice=" + fieldEnterprice +
                ", fieldArea=" + fieldArea +
                ", type=" + type +
                '}';
    }
}
