package com.ksgagro.gps.service;

import com.ksgagro.gps.domain.MapObjectField;
import com.ksgagro.gps.domain.MapObjectFieldType;
import com.ksgagro.gps.domain.TestPay;

import java.util.List;

public interface MapObjectFieldService {

	void addField(MapObjectField field);

	List<MapObjectField> getAll();

	MapObjectField get(int id);

	List<TestPay> neibors();

    TestPay getNeibor(long id);

    List<MapObjectFieldType> getTypes();
}
