package com.ksgagro.gps.repository.impl;

import java.math.BigDecimal;
import java.util.List;

import com.ksgagro.gps.controller.JSON.TerminalDateJSON;
import com.ksgagro.gps.dto.TerminalDateDTO;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ksgagro.gps.domain.Terminal;
import com.ksgagro.gps.repository.TerminalRepository;

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

    @Override
	@javax.transaction.Transactional
    public List<TerminalDateDTO> getTerminals() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT imei, MIN(MESSAGE_DATE) AS MESSAGE_DATE FROM DEVICE_DATE GROUP BY IMEI";
		SQLQuery query = session.createSQLQuery(hql).addEntity(TerminalDateDTO.class);
		return (List<TerminalDateDTO>) query.list();
    }
}
