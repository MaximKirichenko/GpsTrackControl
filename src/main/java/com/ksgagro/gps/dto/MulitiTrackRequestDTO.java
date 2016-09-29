package com.ksgagro.gps.dto;

import java.io.Serializable;
import java.util.List;

public class MulitiTrackRequestDTO implements Serializable{
	
	private static final long serialVersionUID = 74398488174553047L;
	
	private List<Integer> terminalNumbers;
	private long dataFrom;
	private long dataTo;
	
	
	
	public List<Integer> getTerminalNumbers() {
		return terminalNumbers;
	}
	public void setTerminalNumbers(List<Integer> terminalNumbers) {
		this.terminalNumbers = terminalNumbers;
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
		result = prime * result + ((terminalNumbers == null) ? 0 : terminalNumbers.hashCode());
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
		MulitiTrackRequestDTO other = (MulitiTrackRequestDTO) obj;
		if (dataFrom != other.dataFrom)
			return false;
		if (dataTo != other.dataTo)
			return false;
		if (terminalNumbers == null) {
			if (other.terminalNumbers != null)
				return false;
		} else if (!terminalNumbers.equals(other.terminalNumbers))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MulitiTrackRequestDTO [terminalNumbers=" + terminalNumbers + ", dataFrom=" + dataFrom + ", dataTo="
				+ dataTo + "]";
	}
	
	
	
	
	
	
	
	}
