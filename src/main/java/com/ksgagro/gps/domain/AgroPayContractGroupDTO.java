package com.ksgagro.gps.domain;

import java.util.Date;

public class AgroPayContractGroupDTO {
	private Emp emp;
	private Date close;
	public Emp getEmp() {
		return emp;
	}
	public void setEmp(Emp emp) {
		this.emp = emp;
	}
	public Date getClose() {
		return close;
	}
	public void setClose(Date close) {
		this.close = close;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((close == null) ? 0 : close.hashCode());
		result = prime * result + ((emp == null) ? 0 : emp.hashCode());
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
		AgroPayContractGroupDTO other = (AgroPayContractGroupDTO) obj;
		if (close == null) {
			if (other.close != null)
				return false;
		} else if (!close.equals(other.close))
			return false;
		if (emp == null) {
			if (other.emp != null)
				return false;
		} else if (!emp.equals(other.emp))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "AgroPayContractGroupDTO [emp=" + emp + ", close=" + close + "]";
	}
	
	
}
