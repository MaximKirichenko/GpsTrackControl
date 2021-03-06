package com.ksgagro.gps.domain;

public class VehicleMenuItem {
	
	private Vehicle vehicle;
	private TrackEntity lastSignal;
	
	public VehicleMenuItem() {
		super();
	}
	public VehicleMenuItem(Vehicle vehicle, TrackEntity lastSignal) {
		super();
		this.vehicle = vehicle;
		this.lastSignal = lastSignal;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public TrackEntity getLastSignal() {
		return lastSignal;
	}
	public void setLastSignal(TrackEntity lastSignal) {
		this.lastSignal = lastSignal;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vehicle == null) ? 0 : vehicle.hashCode());
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
		VehicleMenuItem other = (VehicleMenuItem) obj;
		if (vehicle == null) {
			if (other.vehicle != null)
				return false;
		} else if (!vehicle.equals(other.vehicle))
			return false;
		return true;
	}
}
