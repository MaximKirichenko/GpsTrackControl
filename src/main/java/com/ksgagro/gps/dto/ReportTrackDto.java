package com.ksgagro.gps.dto;

import java.util.Date;
import java.util.List;

import com.ksgagro.gps.domain.TrackEntity;

public class ReportTrackDto {
	private List<TrackEntity> terminalDateList;
	private double pathLength;
	private double canConsumption;
	private Date startMovement;
	private Date finishMovement;
	

	public List<TrackEntity> getTerminalDateList() {
		return terminalDateList;
	}

	public void setTerminalDateList(List<TrackEntity> terminalDateList) {
		this.terminalDateList = terminalDateList;
	}

	public double getPathLength() {
		return pathLength;
	}

	public void setPathLength(double pathLength) {
		this.pathLength = pathLength;
	}

	public double getCanConsumption() {
		return canConsumption;
	}

	public void setCanConsumption(double canConsumption) {
		this.canConsumption = canConsumption;
	}

	public Date getStartMovement() {
		return startMovement;
	}

	public void setStartMovement(Date startMovement) {
		this.startMovement = startMovement;
	}

	public Date getFinishMovement() {
		return finishMovement;
	}

	public void setFinishMovement(Date finishMovement) {
		this.finishMovement = finishMovement;
	}
	
	
	
}
