package com.ksgagro.gps.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ksgagro.gps.domain.AgroPayContract;
import com.ksgagro.gps.repository.AgroPayConractRepository;
import com.ksgagro.gps.repository.ContractRepository;

@Repository
public class ContractRepositoryImpl implements ContractRepository{

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	AgroPayConractRepository agroPayContractRepository;

	@SuppressWarnings("rawtypes")
	@Transactional
	public List<AgroPayContract> getHotContracts() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT DISTINCT C2.ID, C2.DATE_CLOSE  FROM (SELECT EMP, MAX(AG_PAY_CONTRACT.DATE_OPEN) AS last_open FROM AG_PAY_CONTRACT  GROUP BY EMP) C1, "+ 
					 "AG_PAY_CONTRACT C2,  AG_PAY, AGRO_FIELD WHERE c1.emp = c2.emp AND c1.last_open = C2.DATE_OPEN AND C2.DATE_CLOSE BETWEEN "+
					 "to_date('01-01-2016') AND TO_DATE('31-12-2016') AND "+
					 "C2.EMP = AG_PAY.EMP AND AG_PAY.NAME_R = AGRO_FIELD.KADASTR ORDER BY C2.DATE_CLOSE";
		SQLQuery query = session.createSQLQuery(hql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List data = query.list();
		List<AgroPayContract> agroPayContracts = new ArrayList<AgroPayContract>();

		for(Object object : data){
			Map row = (Map)object;
			System.out.println(row);
			BigDecimal big = (BigDecimal)row.get("ID");
			int id = big.intValue();

			agroPayContracts.add(agroPayContractRepository.getAgroPayContract(id));
		}
		
		for(AgroPayContract contract: agroPayContracts){
			System.out.println(contract);
		}
		Date date = new Date();
		long d = date.getTime();
		long day = 1000*60*60*24;
		long month = day*31;
		long year = month*12;		
		date.setTime(d+year);
		System.out.println(date);
		return agroPayContracts;
	}
	
	@SuppressWarnings("rawtypes")
	@Transactional
	public List<AgroPayContract> getSignedContracts() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT DISTINCT C2.ID, C2.DATE_CLOSE  FROM (SELECT EMP, MAX(AG_PAY_CONTRACT.DATE_OPEN) AS last_open FROM AG_PAY_CONTRACT  GROUP BY EMP) C1, "+ 
					 "AG_PAY_CONTRACT C2,  AG_PAY, AGRO_FIELD WHERE c1.emp = c2.emp AND c1.last_open = C2.DATE_OPEN AND C2.DATE_OPEN BETWEEN "+
					 "to_date('01-01-2016') AND TO_DATE('31-12-2016') AND "+
					 "C2.EMP = AG_PAY.EMP AND AG_PAY.NAME_R = AGRO_FIELD.KADASTR ORDER BY C2.DATE_CLOSE";
		SQLQuery query = session.createSQLQuery(hql);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List data = query.list();
		List<AgroPayContract> agroPayContracts = new ArrayList<AgroPayContract>();

		for(Object object : data){
			Map row = (Map)object;
			System.out.println(row);
			BigDecimal big = (BigDecimal)row.get("ID");
			int id = big.intValue();

			agroPayContracts.add(agroPayContractRepository.getAgroPayContract(id));
		}
		
		for(AgroPayContract contract: agroPayContracts){
			System.out.println(contract);
		}
		Date date = new Date();
		long d = date.getTime();
		long day = 1000*60*60*24;
		long month = day*31;
		long year = month*12;		
		date.setTime(d+year);
		System.out.println(date);
		return agroPayContracts;
	}

}
