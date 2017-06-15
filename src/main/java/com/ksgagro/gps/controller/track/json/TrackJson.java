package com.ksgagro.gps.controller.track.json;

import com.ksgagro.gps.domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxim Kirichenko on 13.06.2017.
 */
public class TrackJson {
    private Terminal terminal;
    private Vehicle vehicle;
    private TrackInfo trackInfo;
    private List<TrackEntity> trackEntities;
    private List<FuelLineChartPoint> leftFuelLine;
    private List<FuelLineChartPoint> rightFuelLine;
    private List<StopJson> stops;

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public TrackInfo getTrackInfo() {
        return trackInfo;
    }

    public void setTrackInfo(TrackInfo trackInfo) {
        this.trackInfo = trackInfo;
    }

    public List<TrackEntity> getTrackEntities() {
        return trackEntities;
    }

    public void setTrackEntities(List<TrackEntity> trackEntities) {
        this.trackEntities = trackEntities;
    }

    public List<FuelLineChartPoint> getLeftFuelLine() {
        return leftFuelLine;
    }

    public void setLeftFuelLine(List<FuelLineChartPoint> leftFuelLine) {
        this.leftFuelLine = leftFuelLine;
    }

    public List<FuelLineChartPoint> getRightFuelLine() {
        return rightFuelLine;
    }

    public void setRightFuelLine(List<FuelLineChartPoint> rightFuelLine) {
        this.rightFuelLine = rightFuelLine;
    }

    public List<StopJson> getStops() {
        return stops;
    }

    public void setStops(List<StopJson> stops) {
        this.stops = stops;
    }
}
