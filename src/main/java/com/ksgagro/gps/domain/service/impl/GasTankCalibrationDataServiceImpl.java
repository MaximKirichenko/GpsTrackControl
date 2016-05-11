package com.ksgagro.gps.domain.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksgagro.gps.domain.GasTankCalibrationData;
import com.ksgagro.gps.domain.repository.GasTankCalibrationDataRepository;
import com.ksgagro.gps.domain.service.GasTankCalibrationDataService;

@Service
public class GasTankCalibrationDataServiceImpl implements GasTankCalibrationDataService{

	@Autowired
	GasTankCalibrationDataRepository calibrationDataRepository;
	
	public Double getFuelLevel(int data, List<GasTankCalibrationData> calibrationData){
//		if(data>0)
//		System.out.print("\nInput " + data);
		GasTankCalibrationData previous = getPrevious(data, calibrationData);
		GasTankCalibrationData next = getNext(data, calibrationData);
		
		if(previous==null ||data==0) {
			return 0.0;
		}
		//if(data<620) System.out.println("Input data: " + data);
		if(next==null){
			return (double)previous.getFuelLevel();
		}
//		System.out.print(" previous " + previous.getData());
//		System.out.print(" next " + next.getData() + "\n");
		if(previous.getData()==0) return 0.0;
		
//		double fuelLevel = ((data*previous.getFuelLevel()/previous.getData())+(data*next.getFuelLevel()/next.getData()))/2;
		
		
		double dataDifferent = next.getData() - previous.getData();
		double fuelDifferent = next.getFuelLevel() - previous.getFuelLevel();
		double c = dataDifferent/fuelDifferent;
		double dataFromPrevious = data - previous.getData();
		double fuelFromPrevious = dataFromPrevious/c;
		double fuelLevel = previous.getFuelLevel() + fuelFromPrevious;
		
		return new BigDecimal(fuelLevel).setScale(3, RoundingMode.UP).doubleValue();
		
		
		
	}
	
	public double getFuelLevel(int terminalNumber, int gasTankPosition, int data) {	
		List<GasTankCalibrationData> list = filteringData(terminalNumber, gasTankPosition);
		if(list == null||list.size()==0){
			return 0.0;
		}
		
		return getFuelLevel(data, list);
	}
	
	private List<GasTankCalibrationData> filteringData(int terminalNumber, int gasTankPosition) {
		List<GasTankCalibrationData> list = calibrationDataRepository.getCalibrationDataByVehicleId(terminalNumber);
		if(list == null || list.size()==0){
			return null;
		}
		List<GasTankCalibrationData> resultList  = new ArrayList<GasTankCalibrationData>();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getGasTank().getGasTankPosition().getId()==gasTankPosition){
				resultList.add(list.get(i));
			}
		}
		return resultList;
	}
	
	private List<GasTankCalibrationData> getLargeItems(int number, List<GasTankCalibrationData> list){
		if(list == null) return null;
		List<GasTankCalibrationData> result = new ArrayList<GasTankCalibrationData>();
		//if(result.size() == 0) return null;
		for(GasTankCalibrationData data: list){
			if(data.getData()>number){
				result.add(data);
			}
		}
		return result;
	}

	private GasTankCalibrationData getNext(int number, List<GasTankCalibrationData> list){
		if(list==null) return null;
		List<GasTankCalibrationData> itemsLargeThenNumber = getLargeItems(number, list);
		if(itemsLargeThenNumber==null||itemsLargeThenNumber.size()==0) return null;
		
		GasTankCalibrationData nextLargestData = itemsLargeThenNumber.get(0);
		for(GasTankCalibrationData item: itemsLargeThenNumber){
			if(nextLargestData.getData()>item.getData()){
				nextLargestData = item;
			}
		}
		return nextLargestData;
	}
	private List<GasTankCalibrationData> getSmallerItems(int number, List<GasTankCalibrationData> list){
		if(list==null) return null;
		List<GasTankCalibrationData> result = new ArrayList<GasTankCalibrationData>();
		for(GasTankCalibrationData data: list){
			if(data.getData()<number){
				result.add(data);
			}
		}
		
		if(result.isEmpty()){
			GasTankCalibrationData first = list.get(0);
			result.add(first);
		}
		
		return result;
	}
	private GasTankCalibrationData getPrevious(int number, List<GasTankCalibrationData> list) {
		if(list == null) return null;
		List<GasTankCalibrationData> itemsSmallerThenNumber = getSmallerItems(number, list);
		GasTankCalibrationData nextSmallerData = itemsSmallerThenNumber.get(0);
		for(GasTankCalibrationData item: itemsSmallerThenNumber){
			if(nextSmallerData.getData()<item.getData()){
				nextSmallerData = item;
			}
		}
		return nextSmallerData;
	}
	public List<GasTankCalibrationData> getValues(int terminal) {
		
		return calibrationDataRepository.getCalibrationDataByVehicleId(terminal);
	}
	public List<GasTankCalibrationData> getLeftValues(int terminal) {
		
		return calibrationDataRepository.getLeftValues(terminal);
	}
	public List<GasTankCalibrationData> getRightValues(int terminal) {
		
		return calibrationDataRepository.getRightValues(terminal);
	}

}
