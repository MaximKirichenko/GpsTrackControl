package com.ksgagro.gps.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ksgagro.gps.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksgagro.gps.dto.FuelChartDTO;
import com.ksgagro.gps.dto.TrackBO;
import com.ksgagro.gps.domain.GasTankCalibrationData;
import com.ksgagro.gps.domain.Refueling;
import com.ksgagro.gps.domain.TrackEntity;
import com.ksgagro.gps.service.ChartService;
import com.ksgagro.gps.service.GasTankCalibrationDataService;
import com.ksgagro.gps.service.TrackService;

@Service
public class ChartServiceImpl implements ChartService{

	@Autowired
	private GasTankCalibrationDataService gasTankService;
	@Autowired
	private TrackService terminalDataService;
	@Autowired
	private TerminalService terminalService;
	@Autowired
	private TrackService trackService;

	
	public FuelChartDTO getFuelChartData(int vehicleId, long from, long to) {
		FuelChartDTO fuelChartDTO = new FuelChartDTO();
		
		List<GasTankCalibrationData> leftTankCalibrationData = gasTankService.getLeftTankCalibrationDataValues(vehicleId, from);
		List<GasTankCalibrationData> rightTankCalibrationData = gasTankService.getRighTankCalibrationDatatValues(vehicleId, from);
		
		List<Integer> engineSpeedDatas = new ArrayList<Integer>();
		List<Long> messageDate= new ArrayList<Long>();
		List<Integer> speeds = new ArrayList<Integer>();
		List<Integer> voltage = new ArrayList<Integer>();
		List<TrackEntity> tracks = trackService.tracks(from, to, terminalService.getTerminalByVehicle(vehicleId).getId());
		List<List<TrackEntity>> stops = terminalDataService.stopList(tracks);
		List<Refueling> refuelings = terminalDataService.getRefulingDate(stops, vehicleId);
		
		List<Double> leftTankDatas = new ArrayList<Double>();
		List<Double> rightTankDatas = new ArrayList<Double>();

		double previousLeftTankLevel = 0;
		double previousRightTankLevel = 0;
		double canConsumption = 0;
		int count=0;
		for(TrackEntity data: tracks){
			//System.out.println(data);
			
			double leftTankLevel = gasTankService.getFuelLevel(data.getLeftGasTank(), leftTankCalibrationData);
			double rightTankLevel = gasTankService.getFuelLevel(data.getRightGasTank(), rightTankCalibrationData);
			if(count>0){
				canConsumption = canConsumption + (previousLeftTankLevel-leftTankLevel) + (previousRightTankLevel - rightTankLevel);
			}
			
//			System.out.println("Left: " + data.getLeftGasTank() + " - " + leftTankLevel + 
//					" | Right: " +data.getRightGasTank() + " " + rightTankLevel);
			leftTankDatas.add(leftTankLevel);
			rightTankDatas.add(rightTankLevel);
			engineSpeedDatas.add(data.getEngineSpeed()/(4*10));
			messageDate.add(data.getMessageDate());
			speeds.add(data.getSpeed());
			voltage.add(data.getPsv()/1000);
			previousLeftTankLevel = leftTankLevel;
			previousRightTankLevel = rightTankLevel;
			count++;
		}
		System.out.println("Can consumption: " + canConsumption);
		
		fuelChartDTO.setRefuelings(refuelings);
		fuelChartDTO.setLeftTankDatas(leftTankDatas);
		fuelChartDTO.setRightTankDatas(rightTankDatas);
		fuelChartDTO.setEngineSpeedDatas(engineSpeedDatas);
		fuelChartDTO.setMessageData(messageDate);
		fuelChartDTO.setFuelConsumptionFromCan(terminalDataService.getCanConsumption(tracks));
		fuelChartDTO.setSpeeds(speeds);
		fuelChartDTO.setVoltage(voltage);
		
		return fuelChartDTO;
	}


	@Override
	public TrackBO buildMultiTrackResponseDto(int numberTerminal, long from, long to) {
		List<TrackEntity> terminalDate = terminalDataService.tracks(from, to, numberTerminal);
		
		return null;
	}
	
	

}
