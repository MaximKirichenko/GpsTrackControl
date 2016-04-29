package com.ksgagro.gps.domain.repository;

import java.util.List;

import com.ksgagro.gps.domain.AgroPayContract;

public interface ContractRepository {
	List<AgroPayContract> getHotContracts();
	List<AgroPayContract> getSignedContracts();
}
