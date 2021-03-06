package com.ksgagro.gps.repository;

import com.ksgagro.gps.domain.MapObjectField;
import com.ksgagro.gps.domain.MapObjectFieldType;
import com.ksgagro.gps.domain.TestPay;

import java.util.List;

public interface MapObjectFieldRepository {
    void addField(MapObjectField field);

    List<MapObjectField> getAll();

    MapObjectField get(int id);

    List<TestPay> getNeibors();

    TestPay getNeibor(long id);

    List<MapObjectFieldType> getTypes();
}
