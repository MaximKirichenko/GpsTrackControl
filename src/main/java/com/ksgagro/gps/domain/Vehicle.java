package com.ksgagro.gps.domain;

import java.io.Serializable;

public class Vehicle implements Serializable{
	
	private static final long serialVersionUID = 4604304450106985899L;
	
	private int id;
	private String name;
	private String regNumber;
	private VehicleGroup group;
	private Location enterprise;
	
	public Vehicle(){}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegNumber() {
		return regNumber;
	}
	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}
	public VehicleGroup getGroup() {
		return group;
	}
	public void setGroup(VehicleGroup group) {
		this.group = group;
	}
	public Location getEnterprise() {
		return enterprise;
	}
	public void setEnterprise(Location enterprise) {
		this.enterprise = enterprise;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((enterprise == null) ? 0 : enterprise.hashCode());
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((regNumber == null) ? 0 : regNumber.hashCode());
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
		Vehicle other = (Vehicle) obj;
		if (enterprise == null) {
			if (other.enterprise != null)
				return false;
		} else if (!enterprise.equals(other.enterprise))
			return false;
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (regNumber == null) {
			if (other.regNumber != null)
				return false;
		} else if (!regNumber.equals(other.regNumber))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", name=" + name + ", regNumber=" + regNumber + ", group=" + group
				+ ", enterprise=" + enterprise + "]";
	}

	
	


	
}
