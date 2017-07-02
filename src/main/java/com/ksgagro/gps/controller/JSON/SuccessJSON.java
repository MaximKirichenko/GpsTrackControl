package com.ksgagro.gps.controller.JSON;

/**
 * Created by Maxim Kirichenko on 19.06.2017.
 */
public class SuccessJSON extends BasicJSON{
    public SuccessJSON(String message) {
        setStatus("SUCCESS");
        setMessage(message);
    }
}
