package com.ksgagro.gps.domain;

import java.io.Serializable;

public class GasTank implements Serializable{

	private static final long serialVersionUID = 2595054792470239853L;
	private int id;
	private Vehicle vehicle;
	private GasTankPosition gasTankPosition;
	private long calibrationDate;
	public GasTank() {
		super();
	}
	public GasTank(int id, Vehicle vehicle, GasTankPosition gasTankPosition) {
		super();
		this.id = id;
		this.vehicle = vehicle;
		this.gasTankPosition = gasTankPosition;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public GasTankPosition getGasTankPosition() {
		return gasTankPosition;
	}
	public void setGasTankPosition(GasTankPosition gasTankPosition) {
		this.gasTankPosition = gasTankPosition;
	}
	
	public long getCalibrationDate() {
		return calibrationDate;
	}
	public void setCalibrationDate(long calibrationDate) {
		this.calibrationDate = calibrationDate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GasTank other = (GasTank) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "GasTank [id=" + id + ", vehicle=" + vehicle + ", gasTankPosition=" + gasTankPosition
				+ ", calibrationDate=" + calibrationDate + "]";
	}
	
	
	

}
