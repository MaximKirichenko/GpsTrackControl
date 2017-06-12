package com.ksgagro.gps.domain;

import java.io.Serializable;
import java.util.List;

public class AgroFields implements Serializable{

	private static final long serialVersionUID = -8773951988833801033L;
	
	private int id;
	private List<Coordinates> coordinates;
	private String kadastr;
	private String use;
	private double area;
	public List<Coordinates> getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(List<Coordinates> coordinates) {
		this.coordinates = coordinates;
	}
	public String getKadastr() {
		return kadastr;
	}
	public void setKadastr(String kadastr) {
		this.kadastr = kadastr;
	}
	public String getUse() {
		return use;
	}
	public void setUse(String use) {
		this.use = use;
	}
	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
		AgroFields other = (AgroFields) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "AgroFields [id=" + id + ", coordinates=" + coordinates + ", kadastr=" + kadastr + ", use=" + use
				+ ", area=" + area + "]";
	}
	
}
