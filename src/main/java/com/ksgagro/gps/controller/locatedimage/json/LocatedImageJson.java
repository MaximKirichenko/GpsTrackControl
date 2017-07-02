package com.ksgagro.gps.controller.locatedimage.json;

import java.util.Date;

/**
 * Created by Maxim Kirichenko on 14.05.17.
 */
public class LocatedImageJson {
    private byte[] data;
    private String owner;
    private String name;
    private Date creationTime;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
