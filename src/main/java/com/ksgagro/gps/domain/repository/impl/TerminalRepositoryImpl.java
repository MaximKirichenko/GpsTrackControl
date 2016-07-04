package com.ksgagro.gps.domain.repository.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
		Criteria criteria = session.createCriteria(Terminal.class)
				.add(Restrictions.eq("vehicle", id))
				.add(Restrictions.eq("uninstal_date",new Long(0)));
		
		Terminal terminal = (Terminal)criteria.uniqueResult();
		return terminal;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Terminal> getTerminals(List<Integer> terminalNumbers) {
		return sessionFactory.getCurrentSession().createCriteria(Terminal.class).add(Restrictions.in("vehicle", terminalNumbers)).list();
	}

}
