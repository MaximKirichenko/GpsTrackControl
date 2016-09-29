package com.ksgagro.gps.repository.impl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ksgagro.gps.dto.FirmsArea;
import com.ksgagro.gps.dto.NotNullContractDTO;
import com.ksgagro.gps.domain.AgroFields;
import com.ksgagro.gps.repository.AgroFieldsRepository;

@Repository
public class AgroFieldsRepositoryImpl implements AgroFieldsRepository {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public void setFields(AgroFields agroFields) {
		Session session = sessionFactory.getCurrentSession();
		session.save(agroFields);
	}
	
	@Transactional
	public List<AgroFields> getAll() {
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(AgroFields.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		@SuppressWarnings("unchecked")
		List<AgroFields> list = (List<AgroFields>)criteria.list();
		return list;
	}
	
	@Transactional
	public int getFieldsSum() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT COUNT(*) FROM AGRO_FIELD";
		SQLQuery query = session.createSQLQuery(hql);
		BigDecimal ammount = (BigDecimal)query.uniqueResult();
		
		System.out.println("Total ammount: " + ammount.intValue());
		return ammount.intValue();
	}
	
	@Transactional
	public double getTotalArea() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT SUM(AREA) sum  FROM "
				+ "(SELECT PAY.NAME_R, SUM(PAY.PAY_AREA) AS AREA "
				+ "FROM "
					+ "(SELECT DISTINCT AGRO_FIELD_ID.KADASTR, AG_PAY.PAY_AREA, FIRMS.NAME_R "
					+ "FROM AGRO_FIELD_ID, AG_PAY, AG_PAY_CONTRACT, FIRMS "
					+ "WHERE AGRO_FIELD_ID.KADASTR = AG_PAY.NAME_R "
					+ "AND AG_PAY.ID = AG_PAY_CONTRACT.AG_PAY AND AG_PAY_CONTRACT.FIRMS = FIRMS.ID "
					+ "AND AG_PAY_CONTRACT.DATE_CLOSE>SYSDATE"
					+ ") PAY "
					+ "GROUP BY PAY.NAME_R)";
		SQLQuery query = session.createSQLQuery(hql);
		BigDecimal ammount = (BigDecimal)query.uniqueResult();
		
		System.out.println("Total area ammount: " + ammount.doubleValue());
		return ammount.doubleValue();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<FirmsArea> getFirmsAreaList(){
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT PAY.NAME_R AS name, SUM(PAY.PAY_AREA) AS area "
				+ "FROM (SELECT DISTINCT AGRO_FIELD_ID.KADASTR, AG_PAY.PAY_AREA, FIRMS.NAME_R FROM AGRO_FIELD_ID, AG_PAY, AG_PAY_CONTRACT, FIRMS "
				+ "WHERE AGRO_FIELD_ID.KADASTR = AG_PAY.NAME_R AND AG_PAY.ID = AG_PAY_CONTRACT.AG_PAY AND AG_PAY_CONTRACT.FIRMS = FIRMS.ID AND AG_PAY_CONTRACT.DATE_CLOSE>SYSDATE) "
				+ "PAY GROUP BY PAY.NAME_R";
		List<FirmsArea> result = session.createSQLQuery(hql)
				.addScalar("name")
				.addScalar("area")
				.setResultTransformer(Transformers.aliasToBean(FirmsArea.class)).list();
		;
		for(FirmsArea item: result){
			
			System.out.println(item);
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public List<NotNullContractDTO> getNotNullContracts(){
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT DISTINCT "+ 
				  "DOUBLE_PAY.NAME_R AS kadastr, "+ 
				  "FIRMS.NAME_R AS firmsName, "+ 
				  "TYPE_CONTR.NAME_R AS contractType, "+
				  "AG_PAY_CONTRACT_STATUS.name_r AS status, "+
				  "CONCAT(CONCAT(EMP.L_NAME_R, ' '),EMP.F_NAME_R) AS fio, "+
				  "AG_PAY_CONTRACT.DATE_OPEN AS open, "+
				  "AG_PAY_CONTRACT.DATE_CLOSE AS close, "+
				  "DOUBLE_PAY.PAY_AREA AS area "+
				"FROM"+
				  "(SELECT AG_PAY.ID, AG_PAY.NAME_R, AG_PAY.PAY_AREA FROM "+ 
				  "(SELECT * FROM ("+ 
				  "SELECT DISTINCT AG_PAY.NAME_R AS KADASTR, COUNT(*) AS PAY_COUNT "+
				  "FROM AGRO_FIELD_ID, AG_PAY, AG_PAY_CONTRACT, FIRMS "+ 
				  "WHERE AGRO_FIELD_ID.KADASTR = AG_PAY.NAME_R " + 
				       "AND AG_PAY.ID = AG_PAY_CONTRACT.AG_PAY "+ 
				       "AND AG_PAY.ID > 0 "+
				       "AND AG_PAY_CONTRACT.FIRMS = FIRMS.ID "+
				       "AND AG_PAY_CONTRACT.DATE_CLOSE > SYSDATE "+
				       "AND AG_PAY.IS_ANNULMENT = 0 "+
				  "GROUP BY AG_PAY.NAME_R) "+
				"WHERE PAY_COUNT>1) DOUBLE_KADASTR, "+
				"AG_PAY "+
				"WHERE DOUBLE_KADASTR.KADASTR = AG_PAY.NAME_R "+
				"AND AG_PAY.ID > 0) DOUBLE_PAY, "+
				"AG_PAY_CONTRACT, "+
				"FIRMS, "+
				"TYPE_CONTR, "+
				"AG_PAY_CONTRACT_STATUS, EMP "+
				"WHERE AG_PAY_CONTRACT.AG_PAY = DOUBLE_PAY.ID AND AG_PAY_CONTRACT.DATE_CLOSE > SYSDATE "+
				"AND FIRMS.ID = AG_PAY_CONTRACT.FIRMS "+
				"AND TYPE_CONTR.ID = AG_PAY_CONTRACT.TYPE_CONTR "+
				"AND AG_PAY_CONTRACT_STATUS.ID = AG_PAY_CONTRACT.AG_PAY_CONTRACT_STATUS "+
				"AND EMP.ID = AG_PAY_CONTRACT.EMP "+
				"ORDER BY DOUBLE_PAY.NAME_R";
				
				
		List<NotNullContractDTO> result = session.createSQLQuery(hql)
				.addScalar("kadastr")
				.addScalar("firmsName")
				.addScalar("contractType")
				.addScalar("status")
				.addScalar("fio")
				.addScalar("open")
				.addScalar("close")
				.addScalar("area")
				.setResultTransformer(Transformers.aliasToBean(NotNullContractDTO.class)).list();
		;
		for(NotNullContractDTO item: result){
			System.out.println(item);
		}
		return result;
	}

}
