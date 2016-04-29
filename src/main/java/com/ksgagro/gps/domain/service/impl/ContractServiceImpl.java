package com.ksgagro.gps.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksgagro.gps.domain.AgroPayContract;
import com.ksgagro.gps.domain.repository.ContractRepository;
import com.ksgagro.gps.domain.service.ContractService;

@Service
public class ContractServiceImpl implements ContractService{

	@Autowired
	private ContractRepository contractRepository;
	
	public List<AgroPayContract> getHotContracts() {
		
		return contractRepository.getHotContracts();
	}

	public List<AgroPayContract> getSignedContracts() {
		// TODO Auto-generated method stub
		return contractRepository.getSignedContracts();
	}

}
