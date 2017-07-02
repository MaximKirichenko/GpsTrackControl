package com.ksgagro.gps.logic.image.service.model;

/**
 * Created by Maxim Kirichenko on 19.06.2017.
 */
public class LocatedImageBO {

    private LocatedImageInfoBO locatedImageInfo;
    private LocatedImageBytesBO locatedImageBytes;

    public LocatedImageInfoBO getLocatedImageInfo() {
        return locatedImageInfo;
    }

    public void setLocatedImageInfo(LocatedImageInfoBO locatedImageInfo) {
        this.locatedImageInfo = locatedImageInfo;
    }

    public LocatedImageBytesBO getLocatedImageBytes() {
        return locatedImageBytes;
    }

    public void setLocatedImageBytes(LocatedImageBytesBO locatedImageBytes) {
        this.locatedImageBytes = locatedImageBytes;
    }
}
