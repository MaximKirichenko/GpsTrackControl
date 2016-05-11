package com.ksgagro.gps.domain.repository.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ksgagro.gps.domain.Vehicle;
import com.ksgagro.gps.domain.repository.VehicleRepository;

@Repository
public class VehicleRepositoryImpl implements VehicleRepository{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Logger logger = Logger.getLogger(VehicleRepositoryImpl.class);
	
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
		Criteria criteria = session.createCriteria(Vehicle.class);
		SimpleExpression getVehicleInGroupExcpression = Restrictions.eq("groupId", groupId);
		criteria.add(getVehicleInGroupExcpression);
		
		@SuppressWarnings("unchecked")
		List<Vehicle> vehicles = criteria.list();
		
		return vehicles;
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

	@Override
	@Transactional
	public Vehicle getVehicleById(int vehicleId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Vehicle.class);
		SimpleExpression getVehicleByIdExpression = Restrictions.eq("id", vehicleId);
		criteria.add(getVehicleByIdExpression);
		Vehicle vehicle = (Vehicle)criteria.uniqueResult();
		logger.info("vehicleById " + vehicle);
		return vehicle;
	}


	
}
