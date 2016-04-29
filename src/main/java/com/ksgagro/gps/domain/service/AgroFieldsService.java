package com.ksgagro.gps.domain.service;

import java.util.List;

import com.ksgagro.gps.controller.dto.FirmsArea;
import com.ksgagro.gps.controller.dto.NotNullContractDTO;
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
