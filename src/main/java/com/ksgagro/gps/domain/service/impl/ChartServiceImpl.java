package com.ksgagro.gps.domain.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksgagro.gps.controller.dto.FuelChartDTO;
import com.ksgagro.gps.domain.GasTankCalibrationData;
import com.ksgagro.gps.domain.Refueling;
import com.ksgagro.gps.domain.TerminalDate;
import com.ksgagro.gps.domain.service.ChartService;
import com.ksgagro.gps.domain.service.GasTankCalibrationDataService;
import com.ksgagro.gps.domain.service.TerminalDateService;

@Service
public class ChartServiceImpl implements ChartService{

	@Autowired
	private GasTankCalibrationDataService gasTankService;
	@Autowired
	private TerminalDateService terminalDataService;

	
	public FuelChartDTO getFuelChartData(int vehicleId, long from, long to) {
		FuelChartDTO fuelChartDTO = new FuelChartDTO();
		
		List<GasTankCalibrationData> leftTankCalibrationData = gasTankService.getLeftTankCalibrationDataValues(vehicleId, from);
		List<GasTankCalibrationData> rightTankCalibrationData = gasTankService.getRighTankCalibrationDatatValues(vehicleId, from);
		
		List<Integer> engineSpeedDatas = new ArrayList<Integer>();
		List<Long> messageDate= new ArrayList<Long>();
		List<Integer> speeds = new ArrayList<Integer>();
		List<Integer> voltage = new ArrayList<Integer>();
		
		List<Refueling> refuelings = terminalDataService.getRefulingDate(
				terminalDataService.getStops(from, to, vehicleId), vehicleId);
		
		List<TerminalDate> terminalDataList = terminalDataService.getTerminalDateAboutVehicleFromPeriod(from, to, vehicleId);
		List<Double> leftTankDatas = new ArrayList<Double>();
		List<Double> rightTankDatas = new ArrayList<Double>();
		for(TerminalDate data: terminalDataList){
			leftTankDatas.add(gasTankService.getFuelLevel(data.getLeftGasTank(), leftTankCalibrationData));
			rightTankDatas.add(gasTankService.getFuelLevel(data.getRightGasTank(), rightTankCalibrationData));
			engineSpeedDatas.add(data.getEngineSpeed()/(4*10));
			messageDate.add(data.getMessageDate());
			speeds.add(data.getSpeed());
			voltage.add(data.getPsv()/1000);
		}
		
		fuelChartDTO.setRefuelings(refuelings);
		fuelChartDTO.setLeftTankDatas(leftTankDatas);
		fuelChartDTO.setRightTankDatas(rightTankDatas);
		fuelChartDTO.setEngineSpeedDatas(engineSpeedDatas);
		fuelChartDTO.setMessageData(messageDate);
		fuelChartDTO.setFuelConsumptionFromCan(terminalDataService.getCanConsumption(terminalDataList));
		fuelChartDTO.setSpeeds(speeds);
		fuelChartDTO.setVoltage(voltage);
		
		return fuelChartDTO;
	}

}
