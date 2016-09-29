package com.ksgagro.gps.repository.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ksgagro.gps.domain.Location;
import com.ksgagro.gps.repository.LocationRepository;

@Repository
public class LocationRepositoryImpl implements LocationRepository{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public LocationRepositoryImpl() {}

	@Transactional
	public List<Location> getList() {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(Location.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		@SuppressWarnings("unchecked")
		List<Location> list = (List<Location>)criteria.list();
		
		
		return list;
	}

}
