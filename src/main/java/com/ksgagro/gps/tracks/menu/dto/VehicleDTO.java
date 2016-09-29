package com.ksgagro.gps.tracks.menu.dto;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by kma on 29.09.2016.
 */
@Component
public class VehicleDTO {
    private Date lastSignalDate = new Date();
    private String vehicleName = "Name";
    private String vehicleLocation = "Location";

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

    //    @Autowired DateUtils dateUtils;
//
//    public VehicleStatus initCurrentStatus() {
//        Date currentDate = new Date();
//        if(currentDate.getTime()-lastSignalDate.getTime()>dateUtils.getLongPeriod(5, DateUtils.TimeMeasure.MINUTES)){
//            return VehicleStatus.OFFLINE;
//        }
//        else{
//            return VehicleStatus.ONLINE;
//        }
//    }
//    public Date getLastSignalDate() {
//        return lastSignalDate;
//    }
//
//    public void setLastSignalDate(Date lastSignalDate) {
//        this.lastSignalDate = lastSignalDate;
//    }
//
//    public String getVehicleName() {
//        return vehicleName;
//    }
//
//    public void setVehicleName(String vehicleName) {
//        this.vehicleName = vehicleName;
//    }
//
//    public String getVehicleLocation() {
//        return vehicleLocation;
//    }
//
//    public void setVehicleLocation(String vehicleLocation) {
//        this.vehicleLocation = vehicleLocation;
//    }
}
