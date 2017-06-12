package com.ksgagro.gps.domain;

import java.io.Serializable;

public class GasTankPosition implements Serializable{
	
	private static final long serialVersionUID = -4570417801300197784L;
	
	private int id;
	private String position;
	
	public GasTankPosition() {
		super();
	}
	public GasTankPosition(int id, String position) {
		super();
		this.id = id;
		this.position = position;
	}
	public GasTankPosition(GasTankPosition gasTankPosition) {
		this.id = gasTankPosition.id;
		this.position = gasTankPosition.position;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
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
		GasTankPosition other = (GasTankPosition) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "GasTankPosition [id=" + id + ", position=" + position + "]";
	}
	
	

}
