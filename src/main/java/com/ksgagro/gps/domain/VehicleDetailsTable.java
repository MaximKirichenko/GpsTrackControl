package com.ksgagro.gps.domain;

public class VehicleDetailsTable {
	Terminal terminal;
	Vehicle vehicle;
	TrackEntity terminalDate;
	double fuelLevelLeft;
	double fuelLevelRight;
	
	
	public Terminal getTerminal() {
		return terminal;
	}
	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public TrackEntity getTerminalDate() {
		return terminalDate;
	}
	public void setTerminalDate(TrackEntity terminalDate) {
		this.terminalDate = terminalDate;
	}
	public double getFuelLevelLeft() {
		return fuelLevelLeft;
	}
	public void setFuelLevelLeft(double fuelLevelLeft) {
		this.fuelLevelLeft = fuelLevelLeft;
	}
	public double getFuelLevelRight() {
		return fuelLevelRight;
	}
	public void setFuelLevelRight(double fuelLevelRight) {
		this.fuelLevelRight = fuelLevelRight;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(fuelLevelLeft);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(fuelLevelRight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((terminal == null) ? 0 : terminal.hashCode());
		result = prime * result + ((terminalDate == null) ? 0 : terminalDate.hashCode());
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
		VehicleDetailsTable other = (VehicleDetailsTable) obj;
		if (Double.doubleToLongBits(fuelLevelLeft) != Double.doubleToLongBits(other.fuelLevelLeft))
			return false;
		if (Double.doubleToLongBits(fuelLevelRight) != Double.doubleToLongBits(other.fuelLevelRight))
			return false;
		if (terminal == null) {
			if (other.terminal != null)
				return false;
		} else if (!terminal.equals(other.terminal))
			return false;
		if (terminalDate == null) {
			if (other.terminalDate != null)
				return false;
		} else if (!terminalDate.equals(other.terminalDate))
			return false;
		if (vehicle == null) {
			if (other.vehicle != null)
				return false;
		} else if (!vehicle.equals(other.vehicle))
			return false;
		return true;
	}
	
	
	
}
