package com.ksgagro.gps.repository.impl;

import java.util.List;

import com.ksgagro.gps.domain.LastDeviceDateEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ksgagro.gps.domain.Terminal;
import com.ksgagro.gps.domain.TrackEntity;
import com.ksgagro.gps.repository.TerminalDateRepository;

@Repository
public class TerminalDateRepositoryImpl implements TerminalDateRepository {
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<TrackEntity> list(long from, long to, String imei) {
		return (List<TrackEntity>) sessionFactory.getCurrentSession().
				createCriteria(TrackEntity.class).
				add(Restrictions.between("messageDate", from, to)).
				add(Restrictions.eq("imei", imei)).addOrder(Order.asc("messageDate")).list();
	}
	
	@Transactional
	public TrackEntity getLastSignal(String imei) {
		DetachedCriteria lastDate = DetachedCriteria.forClass(TrackEntity.class)
				.setProjection(Projections.max("messageDate"))
				.add(Restrictions.eq("imei", imei));
		return (TrackEntity) sessionFactory.getCurrentSession().createCriteria(TrackEntity.class)
				.add(Property.forName("messageDate").eq(lastDate))
				.add(Restrictions.eq("imei", imei))
				.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<LastDeviceDateEntity> getLastSignals() {
		
		return sessionFactory.getCurrentSession().createCriteria(LastDeviceDateEntity.class).list();

	}
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<TrackEntity> tracksByTerminalNumbers(long millisFrom, long millisTo, List<Integer> terminalNumbers) {

		DetachedCriteria getImeisByVehicleIdCrt = DetachedCriteria.forClass(Terminal.class).
		add(Restrictions.in("vehicle", terminalNumbers)).
		setProjection(Projections.property("imei"));
		
		List<TrackEntity> dates = sessionFactory.getCurrentSession().
				createCriteria(TrackEntity.class).
				add(Restrictions.between("messageDate", millisFrom, millisTo)).
				add(Property.forName("imei").in(getImeisByVehicleIdCrt)).addOrder(Order.asc("messageDate")).list();

		return dates;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<TrackEntity> tracksByImeis(long millisFrom, long millisTo, List<String> imeis) {

		if (imeis.size()==1)
			return (List<TrackEntity>) sessionFactory.getCurrentSession().
					createCriteria(TrackEntity.class).
					add(Restrictions.between("messageDate", millisFrom, millisTo)).
					add(Restrictions.eq("imei", imeis.get(0))).addOrder(Order.asc("messageDate")).list();

		return (List<TrackEntity>) sessionFactory.getCurrentSession().
				createCriteria(TrackEntity.class).
				add(Restrictions.between("messageDate", millisFrom, millisTo)).
				add(Restrictions.in("imei", imeis)).addOrder(Order.asc("messageDate")).list();
	}

		
	

}
