package com.ksgagro.gps.repository;

import java.util.List;

import com.ksgagro.gps.domain.MapObjectFieldType;
import com.ksgagro.gps.dto.FirmsArea;
import com.ksgagro.gps.dto.NotNullContractDTO;
import com.ksgagro.gps.domain.AgroFields;

public interface AgroFieldsRepository {

	void setFields(AgroFields agroFields);

	List<AgroFields> getAll();

	int getFieldsSum();

	double getTotalArea();

	List<FirmsArea> getFirmsAreaList();

	List<NotNullContractDTO> getNotNullContracts();
}
