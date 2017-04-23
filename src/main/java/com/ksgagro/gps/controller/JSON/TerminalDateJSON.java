package com.ksgagro.gps.controller.JSON;

import java.util.Date;

/**
 * Created by Maxim Kirichenko on 24.02.17.
 */
public class TerminalDateJSON {

    Date date = new Date();
    String imei = "651513135135531";

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
