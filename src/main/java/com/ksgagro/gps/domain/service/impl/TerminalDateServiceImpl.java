package com.ksgagro.gps.domain.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksgagro.gps.domain.GasTankCalibrationData;
import com.ksgagro.gps.domain.Refueling;
import com.ksgagro.gps.domain.Terminal;
import com.ksgagro.gps.domain.TerminalDate;
import com.ksgagro.gps.domain.repository.TerminalDateRepository;
import com.ksgagro.gps.domain.repository.TerminalRepository;
import com.ksgagro.gps.domain.service.GasTankCalibrationDataService;
import com.ksgagro.gps.domain.service.TerminalDateService;

@Service
public class TerminalDateServiceImpl implements TerminalDateService {

	@Autowired
	TerminalDateRepository terminalDateRepository;
	
	@Autowired
	GasTankCalibrationDataService gasCalibration;

	@Autowired
	TerminalRepository terminalRepository;
	
	Logger logger = Logger.getLogger(TerminalDateServiceImpl.class);
	
	public List<TerminalDate> getTerminalDateAboutVehicleFromPeriod(long millisFrom, long millisTo, int terminalNumber){
		Terminal terminal = terminalRepository.getTerminalByVehicleId(terminalNumber);

		List<TerminalDate> inputList = terminalDateRepository.getListFromPeriod(millisFrom, millisTo,
				terminal.getImei());
		
		////inputList = calibrateTank(inputList, terminalNumber);
		return inputList;
	}
	
	public List<TerminalDate> calibrateTank(List<TerminalDate> list, int terminalNumber ){
		for(TerminalDate point: list){
			int leftGasLevel = (int)gasCalibration.getFuelLevel(terminalNumber, 1, point.getLeftGasTank());
			int rightGasLevel = (int)gasCalibration.getFuelLevel(terminalNumber, 2, point.getRightGasTank());
			point.setLeftGasTank(leftGasLevel);
			point.setRightGasTank(rightGasLevel);
		}
		return list;
		
	}
	
	public List<TerminalDate> filterData(List<TerminalDate> inputList){
		List<TerminalDate> result = new ArrayList<TerminalDate>();

		for (int i = 0; i < inputList.size(); i++) {
			if(inputList.get(i).getLatitude()==0||inputList.get(i).getLongitude()==0) continue;
			if (result.size()==0) {
				result.add(inputList.get(i));
			} else {
				int lastElement = result.size() - 1;
				if ((result.get(lastElement).getLatitude() != inputList.get(i).getLatitude())
						|| (result.get(lastElement).getLongitude() != inputList.get(i).getLongitude())) {
					
					double difLat = Math.abs(result.get(lastElement).getLatitude() - inputList.get(i).getLatitude());
					double difLong = Math.abs(result.get(lastElement).getLongitude() - inputList.get(i).getLongitude());

					if ((difLat < 0.01) && (difLong < 0.01)) {
						result.add(inputList.get(i));
					}
					
				}
			}
		}

		return result;
	}

	public List<TerminalDate> getVehicleFromPeriod(String dateFrom, String timeFrom, String dateTo, String timeTo,
			int terminalNumber) {

		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		LocalDateTime timeFromLDT = LocalDateTime.from(format.parse(dateFrom + " " + timeFrom));
		LocalDateTime timeToLTD = LocalDateTime.from(format.parse(dateTo + " " + timeTo));

		ZonedDateTime fromZdt = timeFromLDT.atZone(ZoneId.systemDefault());
		long millisFrom = fromZdt.toInstant().toEpochMilli();

		ZonedDateTime toZdt = timeToLTD.atZone(ZoneId.systemDefault());
		long millisTo = toZdt.toInstant().toEpochMilli();

		Terminal terminal = terminalRepository.getTerminalByVehicleId(terminalNumber);

		List<TerminalDate> inputList = terminalDateRepository.getListFromPeriod(millisFrom, millisTo,
				terminal.getImei());
		List<TerminalDate> result = new ArrayList<TerminalDate>();

		for (int i = 0; i < inputList.size(); i++) {

			if (i == 0) {
				result.add(inputList.get(i));
			} else {
				int lastElement = result.size() - 1;
				if (result.get(lastElement).getLatitude() != inputList.get(i).getLatitude()
						&& result.get(lastElement).getLongitude() != inputList.get(i).getLongitude()) {
					// if (result.size() > 0) {
					double difLat = Math.abs(result.get(lastElement).getLatitude() - inputList.get(i).getLatitude());
					double difLong = Math.abs(result.get(lastElement).getLongitude() - inputList.get(i).getLongitude());

					if ((difLat < 0.01) && (difLong < 0.01)) {
						result.add(inputList.get(i));
					}
					// }
				}
			}
		}

		return result;
	}
	
	public double getPathLength(List<TerminalDate> terminalDate) {
		double distance = 0.0;
		TerminalDate first = null;
		TerminalDate second = null;
		
		for (TerminalDate aList : terminalDate) {
            if (first == null) {
                first = aList;
            } else {
                second = aList;
                distance += getDistance(first.getLatitude(), first.getLongitude(), second.getLatitude(), second.getLongitude());
                first = second;
            }
        }
        return round(distance/1000);
	}
	
	public Date getLastSignalDate(int terminalNumber) {
		Terminal terminal = terminalRepository.getTerminalByVehicleId(terminalNumber);
		TerminalDate point = terminalDateRepository.getLastSignal(terminal.getImei());
		
		return new Date(point.getMessageDate());
	}
	
	public TerminalDate getLastSignal(int vehicleId){
		Terminal terminal = terminalRepository.getTerminalByVehicleId(vehicleId);
		//logger.info(terminal);
		TerminalDate point = terminalDateRepository.getLastSignal(terminal.getImei());
		return point;
	}
	
	public double getCanConsumption(List<TerminalDate> terminalDates){
		double firstSignalCanConsumption = 0;
		double lastSignalCanConsumption = 0;
		
		for(int i=0; i<terminalDates.size(); i++){
			//System.out.println(terminalDates.get(i).getCANFLS());
			if(terminalDates.get(i).getCANFLS()!=0){
				firstSignalCanConsumption = terminalDates.get(i).getCANFLS();
				break;
			}
		}
		System.out.println("firstSignalCanConsumption " + firstSignalCanConsumption);
		
		for(int i=terminalDates.size()-1; i>=0; i--){
			if(terminalDates.get(i).getCANFLS()!=0){
				lastSignalCanConsumption = terminalDates.get(i).getCANFLS();
				break;
			}
		}
		System.out.println("lastSignalCanConsumption " + lastSignalCanConsumption);
		return (lastSignalCanConsumption - firstSignalCanConsumption)*0.5;
	}
	
	
	
	private double getDistance(double firstLat, double firstLong, double secondLat, double secondLong){
		double distance = 0.0;
		
		firstLat = firstLat*Math.PI/180;
		firstLong = firstLong*Math.PI/180;
		secondLat = secondLat*Math.PI/180;
		secondLong = secondLong*Math.PI/180;
		
		//Ќаходим косинусы и синусы широт и разницы долгот
        double cl1 = Math.cos(firstLat);
        double cl2 = Math.cos(secondLat);
        double sl1 = Math.sin(firstLat);
        double sl2 = Math.sin(secondLat);
        
        double delta = secondLong - firstLong;
        
        double cdelta = Math.cos(delta);
        double sdelta = Math.sin(delta);
		
        //¬ычисление длины большего круга
        double y = Math.sqrt(Math.pow(cl2 * sdelta, 2) + Math.pow(cl1 * sl2 - sl1 * cl2 * cdelta, 2));
        double x = sl1 * sl2 + cl1 * cl2 * cdelta;
        double ad = Math.atan2(y, x);
        distance = ad * 6372795;
        return distance;
	}
	
	private static double round(double d) {	   
	      return new BigDecimal(d).setScale(2, RoundingMode.UP).doubleValue();
	   }

	public Date getStartMovementTime(List<TerminalDate> terminalDate) {
		for(TerminalDate item: terminalDate){
			if(item.getSpeed()>0){
				return new Date(item.getMessageDate());
			}
		}
		return null;
	}

	public Date getFinishMovementTime(List<TerminalDate> terminalDate) {
		Collections.reverse(terminalDate);
		return getStartMovementTime(terminalDate);
	}
	
	public List<List<TerminalDate>> getStops(long millisFrom, long millisTo, int terminalNumber){
		List<TerminalDate> allDateFromTerminal = getTerminalDateAboutVehicleFromPeriod(millisFrom, millisTo, terminalNumber);
//		for(TerminalDate data: allDateFromTerminal){
//			System.out.println(data);
//		}
		boolean stop = false;
		List<List<TerminalDate>> stops = new ArrayList<>();
		List<TerminalDate> stopList = new ArrayList<>();
		for(TerminalDate currentDate: allDateFromTerminal){
			if(currentDate.getSpeed()==0){
				stop = true;
				stopList.add(currentDate);
			}else if(stop == true && currentDate.getSpeed() > 0){
				stops.add(stopList);
				stopList = new ArrayList<>();
				stop = false;
			}
		}
		
		//Leaving only element with size > 1
		List<List<TerminalDate>> result = new ArrayList<>();
		for(List<TerminalDate> currentData: stops){
			if(currentData.size()>1) result.add(currentData);
		}
		return result;
	}
	
	public List<Refueling> getRefulingDate(List<List<TerminalDate>> stops, int terminalNumber){

		List<Refueling> refulings = new ArrayList<>();
		
		
		for(List<TerminalDate> stopDateList: stops){
			DataAboutRefuling refData = getRefulingFromStopList(stopDateList);
			Refueling refuling = new Refueling();
			
			long refulingLeftStartTime = Long.MAX_VALUE;
			long refulingRightStartTime = Long.MAX_VALUE;
			long refulingLeftEndTime = Long.MIN_VALUE;
			long refulingRightEndTime = Long.MIN_VALUE;
			
			double leftFuelDataAtStart = 0;
			double leftFuelDataAtEnd = 0;
			double rightFuelDataAtStart = 0;
			double rightFuelDataAtEnd = 0;
			


			if(refData.leftTanks.size()>0){
				refulingLeftStartTime = refData.getRefulingLeftStartDate();
				
				
				List<GasTankCalibrationData> leftTankCalibrationData = gasCalibration.getLeftTankCalibrationDataValues(terminalNumber, refulingLeftStartTime);
				leftFuelDataAtStart = gasCalibration.getFuelLevel(refData.getStartFuelLevelLeftTank(), leftTankCalibrationData);
				leftFuelDataAtEnd = gasCalibration.getFuelLevel( refData.getLeftMaxFuelLevel(), leftTankCalibrationData);
				refulingLeftEndTime = refData.getRefulingLeftFinishDate();
				System.out.println("Left fuel data level: " + leftFuelDataAtStart + " " + new Date(refulingLeftEndTime));
			}
			if(refData.rightTanks.size()>0){
				refulingRightStartTime = refData.getRefulingRightStartDate();
				
				
				List<GasTankCalibrationData> rightTankCalibrationData = gasCalibration.getRighTankCalibrationDatatValues(terminalNumber, refulingRightStartTime);
				
				rightFuelDataAtStart = gasCalibration.getFuelLevel(refData.getStartFuelLevelRightTank(), rightTankCalibrationData);
				rightFuelDataAtEnd = gasCalibration.getFuelLevel(refData.getRightMaxFuelLevel(), rightTankCalibrationData);
				refulingRightEndTime = refData.getRefulingRightFinishDate();
				
				System.out.println("Right fuel data level: " + rightFuelDataAtStart + " " + new Date(refulingRightEndTime));

			}
			
			Date startDate = new Date(refulingLeftStartTime<=refulingRightStartTime?refulingLeftStartTime:refulingRightStartTime);	
			
			Date endDate = new Date(refulingLeftEndTime>=refulingRightEndTime?refulingRightEndTime:refulingLeftEndTime);
			
			if(startDate.before(endDate)){
				refuling.setStartTime(startDate);
				refuling.setEndTime(endDate);
				refuling.setFuelForStart(Math.floor(leftFuelDataAtStart + rightFuelDataAtStart));
				refuling.setFuelForEnd(Math.floor(leftFuelDataAtEnd + rightFuelDataAtEnd));
			}else{
				refuling.setStartTime(endDate);
				refuling.setEndTime(startDate);
				refuling.setFuelForStart(Math.floor(leftFuelDataAtEnd + rightFuelDataAtEnd));
				refuling.setFuelForEnd(Math.floor(leftFuelDataAtStart + rightFuelDataAtStart));
			}
			
			refuling.setScope(Math.floor(refuling.getFuelForEnd() - refuling.getFuelForStart()));
			refuling.setFuelCountAtEnd(stopDateList.get(stopDateList.size()-1).getCANFLS());
			refuling.setFuelCountAtStart(stopDateList.get(0).getCANFLS());
			if(Math.abs(refuling.getScope())>=10){
				refulings.add(refuling);
			}
		}
		Refueling previous = null;
		for(Refueling curent: refulings){
			if(previous==null){
				previous = curent;
				continue;
			}else{
				curent.setFuelCanDifferent((curent.getFuelCountAtEnd() - previous.getFuelCountAtEnd())*0.5);
				curent.setFuelUsedFromSensor(previous.getFuelForEnd() - curent.getFuelForStart());
				previous = curent;
			}
			
			
		}
		
		return refulings;
	}
	private DataAboutRefuling getRefulingFromStopList(List<TerminalDate> stopList){

		DataAboutRefuling curentRefuling = new DataAboutRefuling();
		
		for(int i = 0; i<stopList.size(); i++){
			if(i>0&&i<stopList.size()-1){
				curentRefuling = analizeAndAddLeftTankDataIfRefulingActive(curentRefuling, stopList.get(i-1), stopList.get(i), false);
				curentRefuling = analizeAndAddRightTankDataIfRefulingActive(curentRefuling, stopList.get(i-1), stopList.get(i), false);
			}else if(i==stopList.size()-1){
				curentRefuling = analizeAndAddLeftTankDataIfRefulingActive(curentRefuling, stopList.get(i-1), stopList.get(i), true);
				curentRefuling = analizeAndAddRightTankDataIfRefulingActive(curentRefuling, stopList.get(i-1), stopList.get(i), true);
			}
			
		}
		System.out.println("Current refuling: " + curentRefuling);
		return curentRefuling;
	}
	private DataAboutRefuling analizeAndAddLeftTankDataIfRefulingActive(DataAboutRefuling refuling, TerminalDate previousDataInArray, TerminalDate curentDataInArray, boolean isLastData){
		
		int previousFuelLevel = previousDataInArray.getLeftGasTank();
		int curentFuelLevel = curentDataInArray.getLeftGasTank();
		refuling.setStartFuelLevelLeftTank(previousDataInArray);
//		logger.info("Input refuling in left gas tank with data: " + previousFuelLevel + " " + 
//				curentFuelLevel + " at " + new Date(previousDataInArray.getMessageDate()) + " isLastData " + isLastData + "\ncurrent refuling object " + refuling);
		if(refulingIsActive(previousFuelLevel, curentFuelLevel, isLastData)){
			logger.info("Start refuling in left gas tank with data: " + previousFuelLevel + " " + 
					curentFuelLevel + " at " + new Date(previousDataInArray.getMessageDate()));
			refuling.startLeftRefuling();
//			refuling.setStartFuelLevelLeftTank(previousFuelLevel);
			refuling.addLeftTanksData(previousDataInArray);
			//logger.info(refuling);
		
		}else if(refulingIsFinished(refuling.isLeftRefulingActive(), previousFuelLevel, curentFuelLevel, isLastData)){
			refuling.finishedLeftRefuling();
//			logger.info("Finish refuling in left gas tank with data: " + previousFuelLevel + " " + 
//					curentFuelLevel + " between " + previousDataInArray.getMessageDate() + " and " + curentDataInArray.getMessageDate());
			refuling.addLeftTanksData(curentDataInArray);
		}
		return refuling;
	}
	private DataAboutRefuling analizeAndAddRightTankDataIfRefulingActive(DataAboutRefuling refuling, TerminalDate previous, TerminalDate curent, boolean isLastData){
		
		int previousFuelLevel = previous.getRightGasTank();
		int curentFuelLevel = curent.getRightGasTank();
		refuling.setStartFuelLevelRightTank(previous);
//		logger.info("Input refuling in right gas tank with data: " + previousFuelLevel + " " + curentFuelLevel
//				+ " at " + new Date(previous.getMessageDate()));
		if(refulingIsActive(previousFuelLevel, curentFuelLevel, isLastData)){
			logger.info("Start refuling in right gas tank with data: " + previousFuelLevel + " " + curentFuelLevel
					+ " at " + new Date(previous.getMessageDate()));
			refuling.startRightRefuling();
//			refuling.setStartFuelLevelRightTank(previousFuelLevel);
			refuling.addRightTanksData(previous);
		
		}else if(refulingIsFinished(refuling.isRightRefulingActive(), previousFuelLevel, curentFuelLevel, isLastData)){
			refuling.finishedRightRefuling();
//			logger.info("Finish refuling in right gas tank with data: " + previousFuelLevel + " " + curentFuelLevel
//					+ " at " + new Date(previous.getMessageDate()));
			refuling.addRightTanksData(curent);
		}
		return refuling;
	}
	
	private boolean refulingIsFinished(boolean isRefuling, int previous, int curent, boolean isLast){
		return isLast&&isRefuling||isRefuling && previous>=curent;
	}

	private boolean refulingIsActive(int previous, int curent, boolean isLastData){
		return !isLastData||previous<curent;
	}

	
	private class DataAboutRefuling{
		
		List<TerminalDate> leftTanks;
		List<TerminalDate> rightTanks;
		
		Boolean isLeftRefuling;
		Boolean isRightRefuling;
		
		int startFuelLevelLeftTank = 0;
		int startFuelLevelRightTank = 0;
		
		long refulingLeftStartDate;
		long refulingRightStartDate;
		
		long refulingLeftFinishDate;
		long refulingRightFinishDate;
		
		
		public DataAboutRefuling() {
			leftTanks = new ArrayList<>();
			rightTanks = new ArrayList<>();
			isLeftRefuling = false;
			isRightRefuling = false;
		}
		
		public void setStartFuelLevelLeftTank(TerminalDate terminalDate){
			if(startFuelLevelLeftTank == 0) {
				startFuelLevelLeftTank = terminalDate.getLeftGasTank();
				refulingLeftStartDate = terminalDate.getMessageDate();
			}else 
				if(startFuelLevelLeftTank>terminalDate.getLeftGasTank()) {
					startFuelLevelLeftTank = terminalDate.getLeftGasTank();
					refulingLeftStartDate = terminalDate.getMessageDate();
				}
		}
		public int getStartFuelLevelLeftTank(){
			return this.startFuelLevelLeftTank;
		}
		public void setStartFuelLevelRightTank(TerminalDate terminalDate){
			if(startFuelLevelRightTank == 0) {
				startFuelLevelRightTank = terminalDate.getRightGasTank();
				refulingRightStartDate = terminalDate.getMessageDate();
			}
			else if(startFuelLevelRightTank>terminalDate.getRightGasTank()){
				startFuelLevelRightTank = terminalDate.getRightGasTank();
				refulingRightStartDate = terminalDate.getMessageDate();
			}
			
		}
		public int getStartFuelLevelRightTank(){
			return this.startFuelLevelRightTank;
		}
		
		
		public int getLeftMaxFuelLevel(){
			int maxValue = Integer.MIN_VALUE;
			for(TerminalDate curent: leftTanks){
				if(curent.getLeftGasTank()>maxValue){
					maxValue = curent.getLeftGasTank();
					refulingLeftFinishDate = curent.getMessageDate();
					System.out.println(new Date(refulingLeftFinishDate));
				}
			}
			return maxValue;
		}
		
		public int getRightMaxFuelLevel(){
			int maxValue = Integer.MIN_VALUE;
			for(TerminalDate curent: rightTanks){
				if(curent.getRightGasTank()>maxValue){
					maxValue = curent.getRightGasTank();
					refulingRightFinishDate = curent.getMessageDate();
					System.out.println(new Date(refulingRightFinishDate));
				}
			}
			return maxValue;
		}
		
		
		
		public long getRefulingLeftFinishDate() {
			return refulingLeftFinishDate;
		}

		public long getRefulingRightFinishDate() {
			return refulingRightFinishDate;
		}

		public long getRefulingLeftStartDate() {
			return refulingLeftStartDate;
		}

		public long getRefulingRightStartDate() {
			return refulingRightStartDate;
		}

		public void addLeftTanksData(TerminalDate data){
			leftTanks.add(data);
		}
		public void addRightTanksData(TerminalDate data){
			rightTanks.add(data);
		}
		public void finishedLeftRefuling(){
			isLeftRefuling = false;
		}
		public void startLeftRefuling(){
			isLeftRefuling = true;
		}
		public boolean isLeftRefulingActive(){
			return this.isLeftRefuling;
		}
		public void finishedRightRefuling(){
			isRightRefuling = false;
		}
		public void startRightRefuling(){
			isRightRefuling =true;
		}
		public boolean isRightRefulingActive(){
			return isRightRefuling;
		}

		@Override
		public String toString() {
			return "DataAboutRefuling [isLeftRefuling="
					+ isLeftRefuling + ", isRightRefuling=" + isRightRefuling + ", startFuelLevelLeftTank="
					+ startFuelLevelLeftTank + ", startFuelLevelRightTank=" + startFuelLevelRightTank + "]";
		}
		
		
	}
	

}
