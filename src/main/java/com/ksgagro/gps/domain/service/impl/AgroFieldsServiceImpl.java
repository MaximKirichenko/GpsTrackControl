package com.ksgagro.gps.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ksgagro.gps.controller.dto.FirmsArea;
import com.ksgagro.gps.controller.dto.NotNullContractDTO;
import com.ksgagro.gps.domain.AgroFields;
import com.ksgagro.gps.domain.repository.AgroFieldsRepository;
import com.ksgagro.gps.domain.service.AgroFieldsService;

@Service
public class AgroFieldsServiceImpl implements AgroFieldsService{
	@Autowired
	AgroFieldsRepository agroFieldsRepository;
	public void setFields(AgroFields agroFields) {
		agroFieldsRepository.setFields(agroFields);
		
	}

	public List<AgroFields> getAll() {
		return agroFieldsRepository.getAll();
	}

	public String getJsonFields() {
		Gson gson = new Gson();
		String str = gson.toJson(getAll());
		return str;
	}

	public int getFieldsSum() {
		return agroFieldsRepository.getFieldsSum();
	}

	public double getTotalArea() {
		return agroFieldsRepository.getTotalArea();
	}
	public List<FirmsArea> getFirmsAreaList(){
		return agroFieldsRepository.getFirmsAreaList();
	}
	
	public  List<NotNullContractDTO> getNotNullContracts(){
		return agroFieldsRepository.getNotNullContracts();
	}

}
