package com.ksgagro.gps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksgagro.gps.domain.MapObjectField;
import com.ksgagro.gps.repository.MapObjectFieldRepository;
import com.ksgagro.gps.service.MapObjectFieldService;

@Service
public class MapObjectFieldServiceImpl implements MapObjectFieldService{

	@Autowired
	MapObjectFieldRepository mapObjectFieldRepository;
	
	@Override
	public void addField(MapObjectField field) {
		mapObjectFieldRepository.addField(field);
		
	}

	@Override
	public List<MapObjectField> getAll() {
		
		return mapObjectFieldRepository.getAll();
	}

	@Override
	public MapObjectField get(int id) {
		return mapObjectFieldRepository.get(id);
	}
	
}
