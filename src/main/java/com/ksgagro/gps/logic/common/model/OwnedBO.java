package com.ksgagro.gps.logic.common.model;

import java.util.Date;

/**
 * Created by Maxim Kirichenko on 02.05.17.
 */
public class OwnedBO {
    private Long ownerId;
    private Long creatorId;
    private Date creationTime;
    private Date updatedTime;

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}
