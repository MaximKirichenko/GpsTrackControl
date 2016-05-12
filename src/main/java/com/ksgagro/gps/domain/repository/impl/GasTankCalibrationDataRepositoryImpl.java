package com.ksgagro.gps.domain.repository.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ksgagro.gps.domain.GasTank;
import com.ksgagro.gps.domain.GasTankCalibrationData;
import com.ksgagro.gps.domain.GasTankPosition;
import com.ksgagro.gps.domain.Vehicle;
import com.ksgagro.gps.domain.repository.GasTankCalibrationDataRepository;
import com.ksgagro.gps.domain.service.VehicleService;

@Repository
public class GasTankCalibrationDataRepositoryImpl implements GasTankCalibrationDataRepository{
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	VehicleService vehicleService;
	
	Logger logger = Logger.getLogger(GasTankCalibrationDataRepositoryImpl.class);
	
	public GasTankCalibrationDataRepositoryImpl(){};

	@Transactional
	public List<GasTankCalibrationData> getValues() {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(GasTankCalibrationData.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		@SuppressWarnings("unchecked")
		List<GasTankCalibrationData> list = (List<GasTankCalibrationData>)criteria.list();

		
		return list;
	}
	
	//TODO refactor this
	@SuppressWarnings("unchecked")
	@Transactional
	public List<GasTankCalibrationData> getCalibrationDataByVehicleId(int vehicleId){
		Session session = sessionFactory.getCurrentSession();
		
		Criteria calibrationDataCriteria= session.createCriteria(GasTankCalibrationData.class);
		Criteria gasTankCriteria = session.createCriteria(GasTank.class);
		
		Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
		
		gasTankCriteria.add(Restrictions.eq("vehicle", vehicle));
		List<GasTank> gasTanks = gasTankCriteria.list();
		
		if(gasTanks.size()>0){
			calibrationDataCriteria.add(Restrictions.in("gasTank", gasTanks));
			return calibrationDataCriteria.list();
		}
				
		return null;
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<GasTankCalibrationData> getLeftValues(int id){
		Session session = sessionFactory.getCurrentSession();
		
		
		Criteria calibrationDataCriteria= session.createCriteria(GasTankCalibrationData.class);
		Criteria gasTankCriteria = session.createCriteria(GasTank.class);
		
		
		
		Vehicle vehicle = vehicleService.getVehicleById(id);
		logger.info(vehicle);
		
		Criteria gasTankPositionCriteria = session.createCriteria(GasTankPosition.class);
		gasTankPositionCriteria.add(Restrictions.eq("position", "LEFT"));
		GasTankPosition gasTankPosition = (GasTankPosition)gasTankPositionCriteria.uniqueResult();
		logger.info(gasTankPosition);
		
		gasTankCriteria.add(Restrictions.eq("vehicle", vehicle));
		List<GasTank> gasTanks = gasTankCriteria.list();
		logger.info(gasTanks);
		
		gasTankCriteria.add(Restrictions.eq("gasTankPosition", gasTankPosition));
		gasTanks = gasTankCriteria.list();
		logger.info(gasTanks);
		if(gasTanks.size()>0){
			calibrationDataCriteria.add(Restrictions.in("gasTank", gasTanks)).addOrder(Order.asc("fuelLevel"));
			
			return calibrationDataCriteria.list();
		}
				
		return null;
		
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public List<GasTankCalibrationData> getRightValues(int id){
		Session session = sessionFactory.getCurrentSession();
		
		
		Criteria calibrationDataCriteria= session.createCriteria(GasTankCalibrationData.class);
		Criteria gasTankCriteria = session.createCriteria(GasTank.class);
		Criteria gasTankPositionCriteria = session.createCriteria(GasTankPosition.class);
		
		Vehicle vehicle = vehicleService.getVehicleById(id);
		
		gasTankPositionCriteria.add(Restrictions.eq("position", "RIGHT"));
		GasTankPosition gasTankPosition = (GasTankPosition)gasTankPositionCriteria.uniqueResult();
		
		gasTankCriteria.add(Restrictions.eq("vehicle", vehicle));
		gasTankCriteria.add(Restrictions.eq("gasTankPosition", gasTankPosition));
		List<GasTank> gasTanks = gasTankCriteria.list();
		if(gasTanks.size()>0){
			calibrationDataCriteria.add(Restrictions.in("gasTank", gasTanks)).addOrder(Order.asc("fuelLevel"));
			return calibrationDataCriteria.list();
		}
				
		return null;
		
	}

}
