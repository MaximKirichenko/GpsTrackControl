package com.ksgagro.gps.repository.impl;

import com.ksgagro.gps.domain.Terminal;
import com.ksgagro.gps.dto.TerminalDateDTO;
import com.ksgagro.gps.repository.TerminalRepository;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TerminalRepositoryImpl implements TerminalRepository{

	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	public Terminal get(int id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Terminal.class)
				.add(Restrictions.eq("id", id))
				.add(Restrictions.eq("uninstal_date", 0L));

		return (Terminal)criteria.uniqueResult();
	}

    @Override
	@Transactional
    public Terminal get(String imei) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Terminal.class)
				.add(Restrictions.eq("imei", imei))
				.add(Restrictions.eq("uninstal_date", 0L));

		return (Terminal)criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Terminal> getTerminals(List<Integer> terminalNumbers) {
		return sessionFactory.getCurrentSession().createCriteria(Terminal.class).add(Restrictions.in("id", terminalNumbers)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Terminal> byVehicles(List<Integer> vehicleIds) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Terminal.class)
				.add(Restrictions.in("vehicle", vehicleIds))
				.add(Restrictions.eq("uninstal_date", 0L));

		return criteria.list();
    }

    @Override
	@Transactional
    public List<TerminalDateDTO> getTerminals() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT imei, MIN(MESSAGE_DATE) AS MESSAGE_DATE FROM DEVICE_DATE GROUP BY IMEI";
		SQLQuery query = session.createSQLQuery(hql).addEntity(TerminalDateDTO.class);
		return (List<TerminalDateDTO>) query.list();
    }

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
    public Terminal byVehicle(int vehicleId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Terminal.class)
				.add(Restrictions.eq("vehicle", vehicleId))
				.add(Restrictions.eq("uninstal_date", 0L));

		return (Terminal)criteria.uniqueResult();
    }

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<Terminal> all() {
		return sessionFactory.getCurrentSession().createCriteria(Terminal.class).add(Restrictions.eq("uninstal_date", 0l)).list();
	}

}
