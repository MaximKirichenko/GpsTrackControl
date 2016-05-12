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

	
	public FuelChartDTO getFuelChartData(int numberTerminal, long from, long to) {
		FuelChartDTO fuelChartDTO = new FuelChartDTO();
		
		List<TerminalDate> terminalDates = terminalDataService.getVehicleFromPeriod(from, to, numberTerminal);

		List<GasTankCalibrationData> fuelDataLeft = gasTankService.getLeftValues(numberTerminal);
		List<GasTankCalibrationData> fuelDataRight = gasTankService.getRightValues(numberTerminal);
		
		List<Double> leftTankDatas = new ArrayList<Double>();
		List<Double> rightTankDatas = new ArrayList<Double>();
		
		List<Integer> engineSpeedDatas = new ArrayList<Integer>();
		List<Long> messageDate= new ArrayList<Long>();
		List<Integer> speeds = new ArrayList<Integer>();
		List<Integer> voltage = new ArrayList<Integer>();
		
		List<Refueling> refuelings = terminalDataService.getRefulingDate(
				terminalDataService.getStops(from, to, numberTerminal), numberTerminal);
		
		for(TerminalDate data: terminalDates){
			leftTankDatas.add(gasTankService.getFuelLevel(data.getLeftGasTank(), fuelDataLeft));
			rightTankDatas.add(gasTankService.getFuelLevel(data.getRightGasTank(), fuelDataRight));
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
		fuelChartDTO.setFuelConsumptionFromCan(terminalDataService.getCanConsumption(terminalDates));
		fuelChartDTO.setSpeeds(speeds);
		fuelChartDTO.setVoltage(voltage);
		
		return fuelChartDTO;
	}

}
