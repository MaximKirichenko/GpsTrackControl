package com.ksgagro.gps.domain;

import java.io.Serializable;
import java.util.List;

public class MapObjectField implements Serializable{
	private static final long serialVersionUID = -5044583500094712978L;
	private int id;
	private List<Coordinates> latLngArray;
	private String fieldNumber;
	private int fieldEnterprice;
	private double fieldArea;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Coordinates> getLatLngArray() {
		return latLngArray;
	}
	public void setLatLngArray(List<Coordinates> latLngArray) {
		this.latLngArray = latLngArray;
	}

	public String getFieldNumber() {
		return fieldNumber;
	}
	public void setFieldNumber(String fieldNumber) {
		this.fieldNumber = fieldNumber;
	}
	public int getFieldEnterprice() {
		return fieldEnterprice;
	}
	public void setFieldEnterprice(int fieldEnterprice) {
		this.fieldEnterprice = fieldEnterprice;
	}
	public double getFieldArea() {
		return fieldArea;
	}
	public void setFieldArea(double fieldArea) {
		this.fieldArea = fieldArea;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(fieldArea);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + fieldEnterprice;
		result = prime * result + ((fieldNumber == null) ? 0 : fieldNumber.hashCode());
		result = prime * result + id;
		result = prime * result + ((latLngArray == null) ? 0 : latLngArray.hashCode());
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
		MapObjectField other = (MapObjectField) obj;
		if (Double.doubleToLongBits(fieldArea) != Double.doubleToLongBits(other.fieldArea))
			return false;
		if (fieldEnterprice != other.fieldEnterprice)
			return false;
		if (fieldNumber == null) {
			if (other.fieldNumber != null)
				return false;
		} else if (!fieldNumber.equals(other.fieldNumber))
			return false;
		if (id != other.id)
			return false;
		if (latLngArray == null) {
			if (other.latLngArray != null)
				return false;
		} else if (!latLngArray.equals(other.latLngArray))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MapObjectField [latLngArray=" + latLngArray + ", fieldNumber=" + fieldNumber + ", fieldEnterprice="
				+ fieldEnterprice + ", fieldArea=" + fieldArea + "]";
	}
}
