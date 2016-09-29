package com.ksgagro.gps.dto;

import java.util.List;

import com.ksgagro.gps.domain.Refueling;

public class FuelChartDTO {
	private List<Double> leftTankDatas;
	private List<Double> rightTankDatas;
	private List<Integer> engineSpeedDatas;
	private List<Long> messageData;
	private double fuelConsumptionFromCan;
	private List<Refueling> refuelings;	
	private List<Integer> speeds;	
	private List<Integer> voltage;
	
	public List<Double> getLeftTankDatas() {
		return leftTankDatas;
	}
	public void setLeftTankDatas(List<Double> leftTankDatas) {
		this.leftTankDatas = leftTankDatas;
	}
	public List<Double> getRightTankDatas() {
		return rightTankDatas;
	}
	public void setRightTankDatas(List<Double> rightTankDatas) {
		this.rightTankDatas = rightTankDatas;
	}
	public List<Integer> getEngineSpeedDatas() {
		return engineSpeedDatas;
	}
	public void setEngineSpeedDatas(List<Integer> engineSpeedDatas) {
		this.engineSpeedDatas = engineSpeedDatas;
	}
	public List<Long> getMessageData() {
		return messageData;
	}
	public void setMessageData(List<Long> messageData) {
		this.messageData = messageData;
	}
	public double getFuelConsumptionFromCan() {
		return fuelConsumptionFromCan;
	}
	public void setFuelConsumptionFromCan(double fuelConsumptionFromCan) {
		this.fuelConsumptionFromCan = fuelConsumptionFromCan;
	}
	public List<Refueling> getRefuelings() {
		return refuelings;
	}
	public void setRefuelings(List<Refueling> refuelings) {
		this.refuelings = refuelings;
	}
	public List<Integer> getSpeeds() {
		return speeds;
	}
	public void setSpeeds(List<Integer> speeds) {
		this.speeds = speeds;
	}
	public List<Integer> getVoltage() {
		return voltage;
	}
	public void setVoltage(List<Integer> voltage) {
		this.voltage = voltage;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((engineSpeedDatas == null) ? 0 : engineSpeedDatas.hashCode());
		long temp;
		temp = Double.doubleToLongBits(fuelConsumptionFromCan);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((leftTankDatas == null) ? 0 : leftTankDatas.hashCode());
		result = prime * result + ((messageData == null) ? 0 : messageData.hashCode());
		result = prime * result + ((rightTankDatas == null) ? 0 : rightTankDatas.hashCode());
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
		FuelChartDTO other = (FuelChartDTO) obj;
		if (engineSpeedDatas == null) {
			if (other.engineSpeedDatas != null)
				return false;
		} else if (!engineSpeedDatas.equals(other.engineSpeedDatas))
			return false;
		if (Double.doubleToLongBits(fuelConsumptionFromCan) != Double.doubleToLongBits(other.fuelConsumptionFromCan))
			return false;
		if (leftTankDatas == null) {
			if (other.leftTankDatas != null)
				return false;
		} else if (!leftTankDatas.equals(other.leftTankDatas))
			return false;
		if (messageData == null) {
			if (other.messageData != null)
				return false;
		} else if (!messageData.equals(other.messageData))
			return false;
		if (rightTankDatas == null) {
			if (other.rightTankDatas != null)
				return false;
		} else if (!rightTankDatas.equals(other.rightTankDatas))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "FuelChartDTO [leftTankDatas=" + leftTankDatas + ", rightTankDatas=" + rightTankDatas
				+ ", engineSpeedDatas=" + engineSpeedDatas + ", messageData=" + messageData
				+ ", fuelConsumptionFromCan=" + fuelConsumptionFromCan + ", refuelings=" + refuelings + ", speeds="
				+ speeds + "]";
	}
	
	
	
	
	
}
