package com.ksgagro.gps.controller.locatedimage.json;

/**
 * Created by Maxim Kirichenko on 14.05.17.
 */
public class LocatedImagePointJSON {
    private Double longitude;
    private Double latitude;
    private String fileKey;

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

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }
}
