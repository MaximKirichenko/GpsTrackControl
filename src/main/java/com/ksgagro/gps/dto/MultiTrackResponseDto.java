package com.ksgagro.gps.dto;

import java.util.List;

import com.ksgagro.gps.domain.FuelLineChartPoint;
import com.ksgagro.gps.domain.Terminal;
import com.ksgagro.gps.domain.TerminalDate;
import com.ksgagro.gps.domain.TrackInfo;
import com.ksgagro.gps.domain.Vehicle;

public class MultiTrackResponseDto {
	
	private Terminal terminal;
	private Vehicle vehicle;
	private List<TerminalDate> data;
	private TrackInfo trackInfo;
	
	private List<FuelLineChartPoint> leftFuelLine;
	private List<FuelLineChartPoint> rightFuelLine;
	
	
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
	public List<TerminalDate> getData() {
		return data;
	}
	public void setData(List<TerminalDate> data) {
		this.data = data;
	}
	public void addData(TerminalDate data){
		this.data.add(data);
	}
	
	public TrackInfo getTrackInfo() {
		return trackInfo;
	}
	public void setTrackInfo(TrackInfo trackInfo) {
		this.trackInfo = trackInfo;
	}
	
	
	public List<FuelLineChartPoint> getLeftFuelLine() {
		return leftFuelLine;
	}
	public void setLeftFuelLine(List<FuelLineChartPoint> leftFuelLine) {
		this.leftFuelLine = leftFuelLine;
	}
	public void addLeftTankFuelPoint(long messageDate, double fuelLevel){
		FuelLineChartPoint point = new FuelLineChartPoint();
		point.setX(messageDate);
		point.setY(fuelLevel);
		this.leftFuelLine.add(point);
	}
	
	public List<FuelLineChartPoint> getRightFuelLine() {
		return rightFuelLine;
	}
	public void setRightFuelLine(List<FuelLineChartPoint> rightFuelLine) {
		this.rightFuelLine = rightFuelLine;
	}
	public void addRightTankFuelPoint(long messageDate, double fuelLevel){
		FuelLineChartPoint point = new FuelLineChartPoint();
		point.setX(messageDate);
		point.setY(fuelLevel);
		this.rightFuelLine.add(point);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((terminal == null) ? 0 : terminal.hashCode());
		result = prime * result + ((trackInfo == null) ? 0 : trackInfo.hashCode());
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
		MultiTrackResponseDto other = (MultiTrackResponseDto) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (terminal == null) {
			if (other.terminal != null)
				return false;
		} else if (!terminal.equals(other.terminal))
			return false;
		if (trackInfo == null) {
			if (other.trackInfo != null)
				return false;
		} else if (!trackInfo.equals(other.trackInfo))
			return false;
		if (vehicle == null) {
			if (other.vehicle != null)
				return false;
		} else if (!vehicle.equals(other.vehicle))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MultiTrackResponseDto [terminal=" + terminal + ", vehicle=" + vehicle + ", data=" + data
				+ ", trackInfo=" + trackInfo + "]";
	}
	
}
