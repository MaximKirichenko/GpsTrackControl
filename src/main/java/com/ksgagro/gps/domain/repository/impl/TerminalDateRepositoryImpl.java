package com.ksgagro.gps.domain.repository.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ksgagro.gps.domain.TerminalDate;
import com.ksgagro.gps.domain.repository.TerminalDateRepository;

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

		//System.out.println(result);
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

}
