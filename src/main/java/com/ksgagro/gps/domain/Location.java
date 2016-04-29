package com.ksgagro.gps.domain;

import java.io.Serializable;

public class Location implements Serializable{

	private static final long serialVersionUID = 576385398677790962L;
	
	private int id;
	
	private String enterprise;
	
	public Location() {super();}

	public Location(int id, String enterprise) {
		super();
		this.id = id;
		this.enterprise = enterprise;
	}

	public Location(Location enterprise) {
		this.id = enterprise.id;
		this.enterprise = enterprise.enterprise;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(String enterprise) {
		this.enterprise = enterprise;
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
		Location other = (Location) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Location [id=" + id + ", enterprise=" + enterprise + "]";
	}
	
	
	
}
