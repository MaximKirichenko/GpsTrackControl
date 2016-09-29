package com.ksgagro.gps.repository;

import java.util.List;

import com.ksgagro.gps.domain.Location;

public interface LocationRepository {
	List<Location> getList();
}
