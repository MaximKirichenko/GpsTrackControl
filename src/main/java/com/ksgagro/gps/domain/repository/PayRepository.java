package com.ksgagro.gps.domain.repository;

import java.util.List;

import com.ksgagro.gps.domain.Pay;

public interface PayRepository {
	List<Pay> getPays();
	List<Pay> getPay(String kadastr);
}
