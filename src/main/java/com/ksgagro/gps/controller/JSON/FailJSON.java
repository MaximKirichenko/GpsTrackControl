package com.ksgagro.gps.controller.JSON;

/**
 * Created by Maxim Kirichenko on 19.06.2017.
 */
public class FailJSON extends BasicJSON{

    public FailJSON(String message) {
        this.setStatus("FAIL");
        this.setMessage(message);
    }
}
