package com.ksgagro.gps.service;

import java.util.List;

import com.ksgagro.gps.domain.AgroPayContract;

public interface ContractService {
	List<AgroPayContract> getHotContracts();
	List<AgroPayContract> getSignedContracts();

}
