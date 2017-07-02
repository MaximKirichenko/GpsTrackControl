package com.ksgagro.gps.logic.image.service.model;

/**
 * Created by Maxim Kirichenko on 17.06.2017.
 */
public class LocatedImageBytesBO {
    private Long locatedImageId;
    private byte[] image;

    public Long getLocatedImageId() {
        return locatedImageId;
    }

    public void setLocatedImageId(Long locatedImageId) {
        this.locatedImageId = locatedImageId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
