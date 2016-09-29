package com.ksgagro.gps.service;

import java.util.List;

import com.ksgagro.gps.dto.FirmsArea;
import com.ksgagro.gps.dto.NotNullContractDTO;
import com.ksgagro.gps.domain.AgroFields;

public interface AgroFieldsService{
	void setFields(AgroFields agroFields);
	int getFieldsSum();
	List<AgroFields> getAll();
	String getJsonFields();
	double getTotalArea();
	List<FirmsArea> getFirmsAreaList();
	List<NotNullContractDTO> getNotNullContracts();
}
