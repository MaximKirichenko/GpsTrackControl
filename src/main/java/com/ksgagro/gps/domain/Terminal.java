package com.ksgagro.gps.domain;

import java.io.Serializable;

public class Terminal implements Serializable{

	private static final long serialVersionUID = 7429911736972025032L;
	
	private int id;
	private String imei;
	private String name;
	private String soft;
	private String telephone;
	private int vehicle;
	private long instal_date;
	private long uninstal_date;
	
	public Terminal() {}
	public Terminal(int id, String imei, String name, String soft, String telephone) {
		this.id = id;
		this.imei = imei;
		this.name = name;
		this.soft = soft;
		this.telephone = telephone;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSoft() {
		return soft;
	}
	public void setSoft(String soft) {
		this.soft = soft;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	
	public int getVehicle() {
		return vehicle;
	}
	public void setVehicle(int vehicle) {
		this.vehicle = vehicle;
	}
	
	
	public long getInstal_date() {
		return instal_date;
	}
	public void setInstal_date(long instal_date) {
		this.instal_date = instal_date;
	}
	public long getUninstal_date() {
		return uninstal_date;
	}
	public void setUninstal_date(long uninstal_date) {
		this.uninstal_date = uninstal_date;
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
		Terminal other = (Terminal) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Terminal [id=" + id + ", imei=" + imei + ", name=" + name + ", soft=" + soft + ", telephone="
				+ telephone + ", vehicle=" + vehicle + "]";
	}
	
	
	

}
