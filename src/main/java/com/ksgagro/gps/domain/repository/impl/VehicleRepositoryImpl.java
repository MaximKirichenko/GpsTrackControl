package com.ksgagro.gps.domain.repository.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ksgagro.gps.domain.Vehicle;
import com.ksgagro.gps.domain.repository.VehicleRepository;

@Repository
public class VehicleRepositoryImpl implements VehicleRepository{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public VehicleRepositoryImpl(){}
	


	@Transactional
	public List<Vehicle> getList() {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(Vehicle.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		@SuppressWarnings("unchecked")
		List<Vehicle> list = (List<Vehicle>)criteria.list();
		return list;
	}


	@Transactional
	public List<Vehicle> getListFromGroup(int groupId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Vehicle vehicle WHERE vehicle.group.id = :group_id";
		Query query = session.createQuery(hql);
		query.setParameter("group_id", groupId);
		@SuppressWarnings("unchecked")
		List<Vehicle> result = query.list();
		
		return result;
	}


	@Transactional
	public List<Vehicle> getListFromLocation(int locationId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Vehicle vehicle WHERE vehicle.enterprise.id = :location_id";
		Query query = session.createQuery(hql);
		query.setParameter("location_id", locationId);
		@SuppressWarnings("unchecked")
		List<Vehicle> result = query.list();
		
		return result;
	}

	@Transactional
	public Vehicle getVehicleByNumberTerminal(int terminalNumber) {
		// TODO Auto-generated method stub
		return (Vehicle) sessionFactory.getCurrentSession().get(Vehicle.class, terminalNumber);
	}
	
	
}
