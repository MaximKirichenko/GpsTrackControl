package com.ksgagro.gps.domain;

import java.io.Serializable;

public class GasTankCalibrationData implements Serializable{

	private static final long serialVersionUID = -152474859637109600L;
	private int id;
	private GasTank gasTank;
	private int fuelLevel;
	private int data;
	public GasTankCalibrationData() {
		super();
	}
	
	
	public GasTankCalibrationData(int id, GasTank gasTank, int fuelLevel, int data) {
		super();
		this.id = id;
		this.gasTank = gasTank;
		this.fuelLevel = fuelLevel;
		this.data = data;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public GasTank getGasTank() {
		return gasTank;
	}
	public void setGasTank(GasTank gasTank) {
		this.gasTank = gasTank;
	}
	public int getFuelLevel() {
		return fuelLevel;
	}
	public void setFuelLevel(int fuelLevel) {
		this.fuelLevel = fuelLevel;
	}
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
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
		GasTankCalibrationData other = (GasTankCalibrationData) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "GasTankCalibrationData [id=" + id + ", gasTank=" + gasTank + ", fuelLevel=" + fuelLevel + ", data="
				+ data + "]";
	}

}
