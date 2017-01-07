package com.ksgagro.gps.service.impl;

import com.ksgagro.gps.domain.MapObjectField;
import com.ksgagro.gps.domain.TestPay;
import com.ksgagro.gps.repository.MapObjectFieldRepository;
import com.ksgagro.gps.service.MapObjectFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

	@Override
	public List<TestPay> neibor() {
		return mapObjectFieldRepository.getNeibor();
	}

}
