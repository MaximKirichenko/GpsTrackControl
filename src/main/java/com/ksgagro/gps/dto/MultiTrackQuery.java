package com.ksgagro.gps.dto;

import java.io.Serializable;
import java.util.List;

public class MultiTrackQuery implements Serializable{
	
	private static final long serialVersionUID = 74398488174553047L;
	
	private List<Integer> vehicleIds;
	private long dataFrom;
	private long dataTo;
	
	
	
	public List<Integer> getVehicleIds() {
		return vehicleIds;
	}
	public void setVehicleIds(List<Integer> vehicleIds) {
		this.vehicleIds = vehicleIds;
	}
	public long getDataFrom() {
		return dataFrom;
	}
	public void setDataFrom(long dataFrom) {
		this.dataFrom = dataFrom;
	}
	public long getDataTo() {
		return dataTo;
	}
	public void setDataTo(long dataTo) {
		this.dataTo = dataTo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (dataFrom ^ (dataFrom >>> 32));
		result = prime * result + (int) (dataTo ^ (dataTo >>> 32));
		result = prime * result + ((vehicleIds == null) ? 0 : vehicleIds.hashCode());
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
		MultiTrackQuery other = (MultiTrackQuery) obj;
		if (dataFrom != other.dataFrom)
			return false;
		if (dataTo != other.dataTo)
			return false;
		if (vehicleIds == null) {
			if (other.vehicleIds != null)
				return false;
		} else if (!vehicleIds.equals(other.vehicleIds))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MulitiTrackRequestDTO [terminalNumbers=" + vehicleIds + ", dataFrom=" + dataFrom + ", dataTo="
				+ dataTo + "]";
	}
	
	
	
	
	
	
	
	}
