package com.ksgagro.gps.domain;

import java.io.Serializable;

public class TerminalDate implements Serializable, Cloneable {

	private static final long serialVersionUID = -9142955441904363073L;

	private int id;
	private String imei;
	private int numberMessage;
	private long messageDate;
	private int height;
	private int isCorrect;
	private int numberSatellite;
	private int azimuth;
	private int speed;
	private double latitude;
	private double longitude;

	private int leftGasTank;
	private int rightGasTank;
	private int CANFuelConsumption;
	private int CANFLS;
	private int engineSpeed;
	private int psv;
	private int vBat;
	
	
	
	

	

	public TerminalDate() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public TerminalDate(TerminalDate terminalDate) {
		super();
		this.id = terminalDate.id;
		this.imei = terminalDate.imei;
		this.numberMessage = terminalDate.numberMessage;
		this.messageDate = terminalDate.messageDate;
		this.height = terminalDate.height;
		this.isCorrect = terminalDate.isCorrect;
		this.numberSatellite = terminalDate.numberSatellite;
		this.azimuth = terminalDate.azimuth;
		this.speed = terminalDate.speed;
		this.latitude = terminalDate.latitude;
		this.longitude = terminalDate.longitude;
		this.leftGasTank = terminalDate.leftGasTank;
		this.rightGasTank = terminalDate.rightGasTank;
		this.CANFuelConsumption = terminalDate.CANFuelConsumption;
		this.CANFLS = terminalDate.CANFLS;
		this.engineSpeed = terminalDate.engineSpeed;
		this.psv = terminalDate.psv;
		this.vBat = terminalDate.vBat;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public int getNumberMessage() {
		return numberMessage;
	}

	public void setNumberMessage(int numberMessage) {
		this.numberMessage = numberMessage;
	}

	public long getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(long messageDate) {
		this.messageDate = messageDate;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(int isCorrect) {
		this.isCorrect = isCorrect;
	}

	public int getNumberSatellite() {
		return numberSatellite;
	}

	public void setNumberSatellite(int numberSatellite) {
		this.numberSatellite = numberSatellite;
	}

	public int getAzimuth() {
		return azimuth;
	}

	public void setAzimuth(int azimuth) {
		this.azimuth = azimuth;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public int getLeftGasTank() {
		return leftGasTank;
	}

	public void setLeftGasTank(int leftGasTank) {
		this.leftGasTank = leftGasTank;
	}

	public int getRightGasTank() {
		return rightGasTank;
	}

	public void setRightGasTank(int rightGasTank) {
		this.rightGasTank = rightGasTank;
	}

	public int getCANFuelConsumption() {
		return CANFuelConsumption;
	}

	public void setCANFuelConsumption(int cANFuelConsumption) {
		CANFuelConsumption = cANFuelConsumption;
	}

	public int getCANFLS() {
		return CANFLS;
	}

	public void setCANFLS(int cANFLS) {
		CANFLS = cANFLS;
	}

	public int getEngineSpeed() {
		return engineSpeed;
	}

	public void setEngineSpeed(int engineSpeed) {
		this.engineSpeed = engineSpeed;
	}

	public int getPsv() {
		return psv;
	}

	public void setPsv(int psv) {
		this.psv = psv;
	}

	public int getvBat() {
		return vBat;
	}

	public void setvBat(int vBat) {
		this.vBat = vBat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		TerminalDate other = (TerminalDate) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TerminalDate [id=" + id + ", imei=" + imei + ", numberMessage=" + numberMessage + ", messageDate="
				+ messageDate + ", height=" + height + ", isCorrect=" + isCorrect + ", numberSatellite="
				+ numberSatellite + ", azimuth=" + azimuth + ", speed=" + speed + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", leftGasTank=" + leftGasTank + ", rightGasTank=" + rightGasTank
				+ ", CANFuelConsumption=" + CANFuelConsumption + ", CANFLS=" + CANFLS + ", engineSpeed=" + engineSpeed
				+ ", psv=" + psv + ", vBat=" + vBat + "]";
	}

	

}
