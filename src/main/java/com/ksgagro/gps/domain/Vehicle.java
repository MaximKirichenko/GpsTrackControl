package com.ksgagro.gps.domain;

import java.io.Serializable;

public class Vehicle implements Serializable{
	
	private static final long serialVersionUID = 4604304450106985899L;
	
	private int numberTerminal;
	private String name;
	private String regNumber;
	private VehicleGroup group;
	private Location enterprise;
	
	public Vehicle(){}
	
	public Vehicle(int numberTerminal, String name, String regNumber, VehicleGroup group, Location enterprise) {
		super();
		this.numberTerminal = numberTerminal;
		this.name = name;
		this.regNumber = regNumber;
		this.group = group;
		this.enterprise = enterprise;
	}

	public Vehicle(Vehicle vehicle) {
		this.numberTerminal = vehicle.numberTerminal;
		this.name = vehicle.name;
		this.regNumber = vehicle.regNumber;
		this.group = new VehicleGroup(vehicle.getGroup());
		this.enterprise = new Location(vehicle.getEnterprise());
	}

	public int getNumberTerminal() {
		return numberTerminal;
	}
	public void setNumberTerminal(int numberTerminal) {
		this.numberTerminal = numberTerminal;
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
		result = prime * result + numberTerminal;
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
		if (numberTerminal != other.numberTerminal)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vehicle [numberTerminal=" + numberTerminal + ", name=" + name + ", regNumber=" + regNumber + ", group="
				+ group + ", enterprise=" + enterprise + "]";
	}
	
}
