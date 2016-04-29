package com.ksgagro.gps.domain;

import java.io.Serializable;
import java.util.List;

public class Polygon implements Serializable{

	private static final long serialVersionUID = -8858329083076697806L;
	List<Coordinates> coordinates;
	String kadastr;
	String use;
	String area;

	
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


	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	@Override
	public String toString() {
		return "Polygon [coordinates=" + coordinates + ", kadastr=" + kadastr + ", use=" + use + ", area=" + area + "]";
	}


	
}
