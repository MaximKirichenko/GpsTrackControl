package com.ksgagro.gps.domain.repository.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ksgagro.gps.domain.Pay;
import com.ksgagro.gps.domain.repository.PayRepository;

@Repository
public class PayRepositoryImpl implements PayRepository{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Pay> getPays() {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Pay.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Pay> getPay(String kadastr) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Pay pay WHERE pay.nameR = :kadastr";
		Query query = session.createQuery(hql);
		query.setParameter("kadastr", kadastr);
		return query.list();
	}
	

	
}
