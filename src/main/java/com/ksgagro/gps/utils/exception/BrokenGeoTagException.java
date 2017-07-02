package com.ksgagro.gps.utils.exception;

/**
 * Created by Maxim Kirichenko on 19.06.2017.
 */
public class BrokenGeoTagException extends RuntimeException {
    public BrokenGeoTagException() {
        super("Broken GeoTag");
    }

}
