package com.ksgagro.gps.repository.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ksgagro.gps.domain.GasTank;
import com.ksgagro.gps.domain.GasTankCalibrationData;
import com.ksgagro.gps.domain.GasTankPosition;
import com.ksgagro.gps.domain.Vehicle;
import com.ksgagro.gps.repository.GasTankCalibrationDataRepository;
import com.ksgagro.gps.service.VehicleService;

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
	public List<GasTankCalibrationData> getLeftTankCalibrationDataValues(int vehicleId, long date){
		logger.info("getLeftTankCalibrationDataValues INVOKED");
		Session session = sessionFactory.getCurrentSession();
		Criteria calibrationDataCriteria= session.createCriteria(GasTankCalibrationData.class);
		Criteria gasTankCriteria = session.createCriteria(GasTank.class);
		
		logger.info(vehicleId);
		Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
		logger.info(vehicle);
		
		Criteria gasTankPositionCriteria = session.createCriteria(GasTankPosition.class);
		gasTankPositionCriteria.add(Restrictions.eq("position", "LEFT"));
		GasTankPosition gasTankPosition = (GasTankPosition)gasTankPositionCriteria.uniqueResult();
		logger.info(gasTankPosition);
		
		gasTankCriteria.add(Restrictions.eq("vehicle", vehicle));
		gasTankCriteria.add(Restrictions.le("calibrationDate", date));
		gasTankCriteria.add(Restrictions.eq("gasTankPosition", gasTankPosition));
		gasTankCriteria.addOrder(Order.desc("calibrationDate")).setMaxResults(1);
		
		GasTank gasTanks = (GasTank)gasTankCriteria.uniqueResult();
		logger.info(gasTanks);
		
		
		
		
		if(gasTanks!=null){
			calibrationDataCriteria.add(Restrictions.eq("gasTank", gasTanks)).addOrder(Order.asc("fuelLevel"));
			List<GasTankCalibrationData> result = calibrationDataCriteria.list();
			return result;
		}
				
		return null;
		
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public List<GasTankCalibrationData> getRighTankCalibrationDatatValues(int vehicleId, long date){
		Session session = sessionFactory.getCurrentSession();
		
		
		Criteria calibrationDataCriteria= session.createCriteria(GasTankCalibrationData.class);
		
		Criteria gasTankPositionCriteria = session.createCriteria(GasTankPosition.class);
		
		Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
		
		gasTankPositionCriteria.add(Restrictions.eq("position", "RIGHT"));
		GasTankPosition gasTankPosition = (GasTankPosition)gasTankPositionCriteria.uniqueResult();
		
		Criteria gasTankCriteria = session.createCriteria(GasTank.class);
		
		gasTankCriteria.add(Restrictions.eq("vehicle", vehicle));
		gasTankCriteria.add(Restrictions.le("calibrationDate", date));
		gasTankCriteria.add(Restrictions.eq("gasTankPosition", gasTankPosition));
		gasTankCriteria.addOrder(Order.desc("calibrationDate")).setMaxResults(1);
		
		GasTank gasTanks = (GasTank)gasTankCriteria.uniqueResult();
		logger.info(gasTanks);
		
		if(gasTanks!=null){
			calibrationDataCriteria.add(Restrictions.eq("gasTank", gasTanks)).addOrder(Order.asc("fuelLevel"));
			List<GasTankCalibrationData> result = calibrationDataCriteria.list();
			return result;
		}
				
		return null;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<GasTankCalibrationData> getTables(List<Integer> terminalNumbers, long millisFrom) {
		
		List<Vehicle> vehicles = sessionFactory.getCurrentSession().createCriteria(Vehicle.class).
				add(Restrictions.in("id", terminalNumbers)).list();
		List<Vehicle> idNumbers = sessionFactory.getCurrentSession().createCriteria(GasTank.class).
				setProjection(Projections.max("calibrationDate")).setProjection(Projections.groupProperty("vehicle")).list();
		
		List<GasTank> gasTanks = sessionFactory.getCurrentSession().createCriteria(GasTank.class).
				add(Restrictions.in("vehicle", vehicles)).list();
				
		
		for(GasTank gt: gasTanks){
			System.out.println(gt);
		}
		
		return null;
	}

}
