package com.ksgagro.gps.logic.common.model;

/**
 * Created by Maxim Kirichenko on 02.05.17.
 */
public class Coordinate {

    private double longitude;
    private double latitude;

    public Coordinate() {
        super();
    }

    public Coordinate(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
