package com.ksgagro.gps.dto;

import java.io.Serializable;

public class TrackRequestDTO implements Serializable{
	
	private static final long serialVersionUID = 74398488174553047L;
	
	private int terminalNumber;
	private long dataFrom;
	private long dataTo;
	
	
	public int getTerminalNumber() {
		return terminalNumber;
	}
	public void setTerminalNumber(int terminalNumber) {
		this.terminalNumber = terminalNumber;
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
		result = prime * result + terminalNumber;
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
		TrackRequestDTO other = (TrackRequestDTO) obj;
		if (dataFrom != other.dataFrom)
			return false;
		if (dataTo != other.dataTo)
			return false;
		if (terminalNumber != other.terminalNumber)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "TrackRequestDTO [terminalNumber=" + terminalNumber + ", dataFrom=" + dataFrom + ", dataTo=" + dataTo
				+ "]";
	}
	
	
	
	}
