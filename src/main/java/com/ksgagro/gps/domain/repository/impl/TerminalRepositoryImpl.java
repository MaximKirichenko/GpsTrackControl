package com.ksgagro.gps.domain.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
	public Terminal getTerminalById(int id) {
		Session session = sessionFactory.getCurrentSession();
		return (Terminal)session.get(Terminal.class, id);
	}

}
