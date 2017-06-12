package com.ksgagro.gps.dto;

import com.ksgagro.gps.domain.Vehicle;

public class ReportGroupDTO {
	private Vehicle vehicle;
	private double mileage;
	private double consumption;
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public double getMileage() {
		return mileage;
	}
	public void setMileage(double mileage) {
		this.mileage = mileage;
	}
	public double getConsumption() {
		return consumption;
	}
	public void setConsumption(double consumption) {
		this.consumption = consumption;
	}
	
}
