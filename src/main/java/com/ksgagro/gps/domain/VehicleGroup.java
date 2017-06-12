package com.ksgagro.gps.domain;

import java.io.Serializable;

public class VehicleGroup implements Serializable{
	private static final long serialVersionUID = 4118141748311142148L;
	
	private int id;
	private String groupName;
	public VehicleGroup() {
		super();
		// TODO Auto-generated constructor stub
	}
	public VehicleGroup(int id, String groupName) {
		super();
		this.id = id;
		this.groupName = groupName;
	}
	public VehicleGroup(VehicleGroup group) {
		this.id = group.id;
		this.groupName = group.groupName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
		VehicleGroup other = (VehicleGroup) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "VehicleGroup [id=" + id + ", groupName=" + groupName + "]";
	}
	
	
	
}
