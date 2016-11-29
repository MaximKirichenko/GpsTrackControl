package com.ksgagro.gps.repository;

import java.util.List;

import com.ksgagro.gps.domain.MapObjectField;

public interface MapObjectFieldRepository {
  void addField(MapObjectField field);
  List<MapObjectField> getAll();
  MapObjectField get(int id);
}
