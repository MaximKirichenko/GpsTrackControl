package com.ksgagro.gps.repository.impl;

import java.util.List;

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
import com.ksgagro.gps.domain.TerminalDate;
import com.ksgagro.gps.repository.TerminalDateRepository;

@Repository
public class TerminalDateRepositoryImpl implements TerminalDateRepository {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public List<TerminalDate> getListFromPeriod(long from, long to, String imei) {
		Session session = sessionFactory.getCurrentSession();
		
		String hql = "FROM TerminalDate terminalDate WHERE terminalDate.imei = :imei AND terminalDate.messageDate > :from AND terminalDate.messageDate < :to ORDER BY terminalDate.messageDate";
		Query query = session.createQuery(hql);
		query.setParameter("from", from);
		query.setParameter("to", to);
		query.setParameter("imei", imei);
		
		@SuppressWarnings("unchecked")
		List<TerminalDate> result = query.list();

		return result;
	}
	
	@Transactional
	public TerminalDate getLastSignal(String imei) {
		Session session = sessionFactory.getCurrentSession();
	
		String hql = "FROM TerminalDate terminalDate WHERE terminalDate.imei = :imei AND terminalDate.messageDate = (SELECT max(terminalDate.messageDate) FROM TerminalDate terminalDate WHERE terminalDate.imei = :imei)";
		Query query = session.createQuery(hql);
		query.setParameter("imei", imei);

		@SuppressWarnings("unchecked")
		List<TerminalDate> result = query.list();
		
		return result.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<TerminalDate> getLastSignals() {
		DetachedCriteria  groupingCriteria = DetachedCriteria.forClass(TerminalDate.class);
		groupingCriteria.setProjection(Projections.projectionList().
				add(Projections.groupProperty("imei")).
				add(Projections.max("messageDate")));
		
		return sessionFactory.getCurrentSession().createCriteria(TerminalDate.class)
				.add(Subqueries.propertiesIn(new String[]{"imei", "messageDate"},  groupingCriteria)).list();

	}
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<TerminalDate> getTerminalDateAboutVehiclesFromPeriod(long millisFrom, long millisTo, List<Integer> terminalNumbers) {

		DetachedCriteria getImeisByVehicleIdCrt = DetachedCriteria.forClass(Terminal.class).
		add(Restrictions.in("vehicle", terminalNumbers)).
		setProjection(Projections.property("imei"));
		
		List<TerminalDate> dates = sessionFactory.getCurrentSession().
				createCriteria(TerminalDate.class).
				add(Restrictions.between("messageDate", millisFrom, millisTo)).
				add(Property.forName("imei").in(getImeisByVehicleIdCrt)).addOrder(Order.asc("messageDate")).list();

		return dates;
	}

		
	

}
