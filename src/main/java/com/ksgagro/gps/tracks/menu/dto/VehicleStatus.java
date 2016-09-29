package com.ksgagro.gps.tracks.menu.dto;

/**
 * Created by kma on 29.09.2016.
 */
public enum VehicleStatus {
    MOVE("in move img"),
    ONLINE("online img"),
    OFFLINE("of line img");

    String status;
    VehicleStatus(String status) {
        this.status = status;
    }

    private String getStatus() {
        return status;
    }
}
