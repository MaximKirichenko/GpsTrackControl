package com.ksgagro.gps.tracks.menu.dto;

import com.ksgagro.gps.tracks.menu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by kma on 29.09.2016.
 */
public class VehicleDTO {
    private Date lastSignalDate;
    private String vehicleName;
    private String vehicleLocation;
    @Autowired DateUtils dateUtils;

    static enum VehicleStatus {MOVE("in move img"), ONLINE("online img"), OFFLINE("of line img");
        String status;
        VehicleStatus(String status) {
            this.status = status;
        }

        private String getStatus() {
            return status;
        }
    };

    public VehicleStatus getCurrentStatus() {
        Date currentDate = new Date();
        if(currentDate.getTime()-lastSignalDate.getTime()>dateUtils.getLongPeriod(5, DateUtils.TimeMeasure.MINUTES)){
            System.out.println(VehicleStatus.OFFLINE);
            return VehicleStatus.OFFLINE;
        }
        else{
            System.out.println(VehicleStatus.ONLINE);
            return VehicleStatus.ONLINE;
        }
    }
    public Date getLastSignalDate() {
        return lastSignalDate;
    }

    public void setLastSignalDate(Date lastSignalDate) {
        this.lastSignalDate = lastSignalDate;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleLocation() {
        return vehicleLocation;
    }

    public void setVehicleLocation(String vehicleLocation) {
        this.vehicleLocation = vehicleLocation;
    }
}
