package com.ksgagro.gps.domain.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ksgagro.gps.controller.dto.CarSignalDTO;
import com.ksgagro.gps.domain.repository.CarSignalRepository;

@Repository
public class CarSignalRepositoryImpl implements CarSignalRepository{

	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	public List<CarSignalDTO> gelLastSignal() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT VEHICLE.NAME as name, "
				+ "VEHICLE.REG_NUMBER as regNumber, "
				+ "MES_DATE as message FROM VEHICLE,"
				+ "(SELECT * from TERMINAL, (SELECT IMEI, MAX(MESSAGE_DATE) AS MES_DATE "
				+ "FROM DEVICE_DATE GROUP BY IMEI) DATE_T WHERE TERMINAL.IMEI = DATE_T.IMEI) "
				+ "TERMINAL_D WHERE VEHICLE.NUMBERTERMINAL = TERMINAL_D.ID ORDER BY message";
		@SuppressWarnings("unchecked")
		List<CarSignalDTO> result = session.createSQLQuery(hql)
				.addScalar("name")
				.addScalar("regNumber")
				.addScalar("message")
				.setResultTransformer(Transformers.aliasToBean(CarSignalDTO.class)).list();
		
		return result;
	}

}
