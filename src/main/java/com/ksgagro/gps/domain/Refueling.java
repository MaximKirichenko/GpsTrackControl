package com.ksgagro.gps.domain;

import java.util.Date;

public class Refueling {
	private double scope;
	private double fuelForStart;
	private double fuelForEnd;
	private Date startTime;
	private Date endTime;
	private double fuelUsedFromSensor;
	private double fuelUsedFromCan;
	private int fuelCountAtEnd;
	private int fuelCountAtStart;
	private double fuelCanDifferent;
	public double getScope() {
		return scope;
	}
	public void setScope(double scope) {
		this.scope = scope;
	}
	public double getFuelForStart() {
		return fuelForStart;
	}
	public void setFuelForStart(double fuelForStart) {
		this.fuelForStart = fuelForStart;
	}
	public double getFuelForEnd() {
		return fuelForEnd;
	}
	public void setFuelForEnd(double fuelForEnd) {
		this.fuelForEnd = fuelForEnd;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = new Date(startTime);
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = new Date(endTime);
	}
	
	public double getFuelUsedFromSensor() {
		return fuelUsedFromSensor;
	}
	public void setFuelUsedFromSensor(double fuelUsedFromSensor) {
		this.fuelUsedFromSensor = fuelUsedFromSensor;
	}
	public double getFuelUsedFromCan() {
		return fuelUsedFromCan;
	}
	public void setFuelUsedFromCan(double fuelUsedFromCan) {
		this.fuelUsedFromCan = fuelUsedFromCan;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public int getFuelCountAtEnd() {
		return fuelCountAtEnd;
	}
	public void setFuelCountAtEnd(int fuelCountAtEnd) {
		this.fuelCountAtEnd = fuelCountAtEnd;
	}
	public int getFuelCountAtStart() {
		return fuelCountAtStart;
	}
	public void setFuelCountAtStart(int fuelCountAtStart) {
		this.fuelCountAtStart = fuelCountAtStart;
	}
	
	public double getFuelCanDifferent() {
		return fuelCanDifferent;
	}
	public void setFuelCanDifferent(double fuelCanDifferent) {
		this.fuelCanDifferent = fuelCanDifferent;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		long temp;
		temp = Double.doubleToLongBits(fuelForEnd);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(fuelForStart);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(scope);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
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
		Refueling other = (Refueling) obj;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (Double.doubleToLongBits(fuelForEnd) != Double.doubleToLongBits(other.fuelForEnd))
			return false;
		if (Double.doubleToLongBits(fuelForStart) != Double.doubleToLongBits(other.fuelForStart))
			return false;
		if (Double.doubleToLongBits(scope) != Double.doubleToLongBits(other.scope))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Refuling [scope=" + scope + ", fuelForStart=" + fuelForStart + ", fuelForEnd=" + fuelForEnd
				+ ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}
	
	
}
