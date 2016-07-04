package com.ksgagro.gps.domain;

import java.util.List;

public class TrackInfo {
	
	private double totalLength;
	private List<DaysTrack> daysTracks;
	
	
	public double getTotalLength() {
		return totalLength;
	}
	public void setTotalLength(double totalLength) {
		this.totalLength = totalLength;
	}
	public List<DaysTrack> getDaysTracks() {
		return daysTracks;
	}
	public void setDaysTracks(List<DaysTrack> daysTracks) {
		this.daysTracks = daysTracks;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((daysTracks == null) ? 0 : daysTracks.hashCode());
		long temp;
		temp = Double.doubleToLongBits(totalLength);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		TrackInfo other = (TrackInfo) obj;
		if (daysTracks == null) {
			if (other.daysTracks != null)
				return false;
		} else if (!daysTracks.equals(other.daysTracks))
			return false;
		if (Double.doubleToLongBits(totalLength) != Double.doubleToLongBits(other.totalLength))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "TrackInfo [totalLength=" + totalLength + ", daysTracks=" + daysTracks + "]";
	}
	
	
}
