package com.ksgagro.gps.domain;

import java.util.Date;

public class AgroPayContract {
	private int id;
	private String contract;
	private Pay pay;
	private Date open;
	private Date close;
	private Emp emp;
	
	
	
	public AgroPayContract() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AgroPayContract(int id, String contract, Pay pay, Date open, Date close, Emp emp) {
		super();
		this.id = id;
		this.contract = contract;
		this.pay = pay;
		this.open = open;
		this.close = close;
		this.emp = emp;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContract() {
		return contract;
	}
	public void setContract(String contract) {
		this.contract = contract;
	}
	public Pay getPay() {
		return pay;
	}
	public void setPay(Pay pay) {
		this.pay = pay;
	}
	public Date getOpen() {
		return open;
	}
	public void setOpen(Date open) {
		this.open = open;
	}
	public Date getClose() {
		return close;
	}
	public void setClose(Date close) {
		this.close = close;
	}
	
	public Emp getEmp() {
		return emp;
	}
	public void setEmp(Emp emp) {
		this.emp = emp;
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
		AgroPayContract other = (AgroPayContract) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "AgroPayContract [id=" + id + ", contract=" + contract + ", pay=" + pay + ", open=" + open + ", close="
				+ close + ", emp=" + emp + "]";
	}
}
