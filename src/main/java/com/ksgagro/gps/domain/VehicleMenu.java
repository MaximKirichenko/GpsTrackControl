package com.ksgagro.gps.domain;

import java.util.List;

public class VehicleMenu {
	private List<VehicleMenuItem> menuItems;

	public List<VehicleMenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<VehicleMenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((menuItems == null) ? 0 : menuItems.hashCode());
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
		VehicleMenu other = (VehicleMenu) obj;
		if (menuItems == null) {
			if (other.menuItems != null)
				return false;
		} else if (!menuItems.equals(other.menuItems))
			return false;
		return true;
	}
	
	
}
