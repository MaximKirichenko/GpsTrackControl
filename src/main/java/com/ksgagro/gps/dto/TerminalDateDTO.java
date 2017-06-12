package com.ksgagro.gps.dto;

/**
 * Created by Maxim Kirichenko on 24.02.17.
 */
public class TerminalDateDTO {

    private Long date;
    private String imei;

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
