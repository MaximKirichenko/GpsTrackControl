package com.ksgagro.gps.dto;

import java.math.BigDecimal;
import java.util.Date;

public class NotNullContractDTO {
	private String kadastr;
	private String firmsName;
	private String contractType;
	private String status;
	private String fio;
	private Date open;
	private Date close;
	private double area;
	public String getKadastr() {
		return kadastr;
	}
	public void setKadastr(String kadastr) {
		this.kadastr = kadastr;
	}
	public String getFirmsName() {
		return firmsName;
	}
	public void setFirmsName(String firmsName) {
		this.firmsName = firmsName;
	}
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFio() {
		return fio;
	}
	public void setFio(String fio) {
		this.fio = fio;
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
	public double getArea() {
		return area;
	}
	public void setArea(BigDecimal area) {
		this.area = area.doubleValue();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(area);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((close == null) ? 0 : close.hashCode());
		result = prime * result + ((contractType == null) ? 0 : contractType.hashCode());
		result = prime * result + ((fio == null) ? 0 : fio.hashCode());
		result = prime * result + ((firmsName == null) ? 0 : firmsName.hashCode());
		result = prime * result + ((kadastr == null) ? 0 : kadastr.hashCode());
		result = prime * result + ((open == null) ? 0 : open.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		NotNullContractDTO other = (NotNullContractDTO) obj;
		if (Double.doubleToLongBits(area) != Double.doubleToLongBits(other.area))
			return false;
		if (close == null) {
			if (other.close != null)
				return false;
		} else if (!close.equals(other.close))
			return false;
		if (contractType == null) {
			if (other.contractType != null)
				return false;
		} else if (!contractType.equals(other.contractType))
			return false;
		if (fio == null) {
			if (other.fio != null)
				return false;
		} else if (!fio.equals(other.fio))
			return false;
		if (firmsName == null) {
			if (other.firmsName != null)
				return false;
		} else if (!firmsName.equals(other.firmsName))
			return false;
		if (kadastr == null) {
			if (other.kadastr != null)
				return false;
		} else if (!kadastr.equals(other.kadastr))
			return false;
		if (open == null) {
			if (other.open != null)
				return false;
		} else if (!open.equals(other.open))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "NotNullContractDTO [kadastr=" + kadastr + ", firmsName=" + firmsName + ", contractType=" + contractType
				+ ", status=" + status + ", fio=" + fio + ", open=" + open + ", close=" + close + ", area=" + area
				+ "]";
	}
	
	

}
