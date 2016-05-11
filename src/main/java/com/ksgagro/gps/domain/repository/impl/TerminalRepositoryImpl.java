package com.ksgagro.gps.domain.repository.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ksgagro.gps.domain.Terminal;
import com.ksgagro.gps.domain.repository.TerminalRepository;

@Repository
public class TerminalRepositoryImpl implements TerminalRepository{

	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	public Terminal getTerminalByVehicleId(int id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Terminal.class);
		SimpleExpression getTerminalByVehicleIdExcpression = Restrictions.eq("vehicle", id);
		criteria.add(getTerminalByVehicleIdExcpression);
		Terminal terminal = (Terminal)criteria.uniqueResult();
		return terminal;
	}

}
