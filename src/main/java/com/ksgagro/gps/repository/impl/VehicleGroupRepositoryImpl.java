package com.ksgagro.gps.repository.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ksgagro.gps.domain.VehicleGroup;
import com.ksgagro.gps.repository.VehicleGroupRepository;

@Repository
public class VehicleGroupRepositoryImpl implements VehicleGroupRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	public VehicleGroupRepositoryImpl() {}
	


	@Transactional
	public List<VehicleGroup> getList() {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(VehicleGroup.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		@SuppressWarnings("unchecked")
		List<VehicleGroup> list = (List<VehicleGroup>)criteria.list();
		return list;
	}
	
}
