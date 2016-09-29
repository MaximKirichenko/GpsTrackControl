package com.ksgagro.gps.repository.impl;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ksgagro.gps.domain.AgroPayContract;
import com.ksgagro.gps.domain.Pay;
import com.ksgagro.gps.repository.AgroPayConractRepository;



@Repository
public class AgroPayContractRepositoryImpl implements AgroPayConractRepository{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public AgroPayContract getAgroPayContract(Pay pay) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM AgroPayContract contract WHERE contract.pay =:pay";
		Query query = session.createQuery(hql);
		query.setParameter("pay", pay);
		
		return (AgroPayContract) query.list().get(0);
	}
	
	@Transactional
	public AgroPayContract getAgroPayContract(int id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM AgroPayContract contract WHERE contract.id =:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		
		return (AgroPayContract) query.list().get(0);
	}
	
}
