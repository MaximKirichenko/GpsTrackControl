package com.ksgagro.gps.controller.track.json;

import com.ksgagro.gps.domain.Vehicle;

import java.util.Date;

/**
 * Created by Maxim Kirichenko on 15.06.2017.
 */

public class StopJson {
    private Vehicle vehicle;
    private Date date;
    private Long duration;
    private Double longitude;
    private Double latitude;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
