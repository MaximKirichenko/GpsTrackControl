package com.ksgagro.gps.domain.repository;

import java.util.List;

import com.ksgagro.gps.controller.dto.FirmsArea;
import com.ksgagro.gps.controller.dto.NotNullContractDTO;
import com.ksgagro.gps.domain.AgroFields;

public interface AgroFieldsRepository {
	void setFields(AgroFields agroFields);
	List<AgroFields> getAll();
	int getFieldsSum();
	double getTotalArea();
	List<FirmsArea> getFirmsAreaList();
	List<NotNullContractDTO> getNotNullContracts();
}
