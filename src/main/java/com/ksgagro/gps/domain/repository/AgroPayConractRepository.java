package com.ksgagro.gps.domain.repository;

import com.ksgagro.gps.domain.AgroPayContract;
import com.ksgagro.gps.domain.Pay;

public interface AgroPayConractRepository {
	AgroPayContract getAgroPayContract(Pay pay);
	AgroPayContract getAgroPayContract(int id);
}
