package com.ksgagro.gps.dto;

import java.util.ArrayList;
import java.util.List;

import com.ksgagro.gps.domain.FuelLineChartPoint;
import com.ksgagro.gps.domain.Terminal;
import com.ksgagro.gps.domain.TrackEntity;
import com.ksgagro.gps.domain.TrackInfo;
import com.ksgagro.gps.domain.Vehicle;

public class TrackBO {
	
	private Terminal terminal;
	private Vehicle vehicle;
	private TrackInfo trackInfo;
	private List<TrackEntity> trackEntities = new ArrayList<>();
	private List<FuelLineChartPoint> leftFuelLine = new ArrayList<>();
	private List<FuelLineChartPoint> rightFuelLine = new ArrayList<>();
	private List<List<TrackEntity>> stopList = new ArrayList<>();
	
	
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
	public List<TrackEntity> getTrackEntities() {
		return trackEntities;
	}
	public void setTrackEntities(List<TrackEntity> trackEntities) {
		this.trackEntities = trackEntities;
	}
	public void addTrackEntity(TrackEntity data){
		this.trackEntities.add(data);
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

	public List<List<TrackEntity>> getStopList() {
		return stopList;
	}

	public void setStopList(List<List<TrackEntity>> stopList) {
		this.stopList = stopList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((trackEntities == null) ? 0 : trackEntities.hashCode());
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
		TrackBO other = (TrackBO) obj;
		if (trackEntities == null) {
			if (other.trackEntities != null)
				return false;
		} else if (!trackEntities.equals(other.trackEntities))
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
		return "MultiTrackResponseDto [terminal=" + terminal + ", vehicle=" + vehicle + ", data=" + trackEntities
				+ ", trackInfo=" + trackInfo + "]";
	}
	
}
