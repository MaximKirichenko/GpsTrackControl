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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksgagro.gps.domain.Refueling;
import com.ksgagro.gps.domain.Terminal;
import com.ksgagro.gps.domain.TerminalDate;
import com.ksgagro.gps.domain.repository.TerminalDateRepository;
import com.ksgagro.gps.domain.repository.TerminalRepository;
import com.ksgagro.gps.domain.service.TerminalDateService;

@Service
public class TerminalDateServiceImpl implements TerminalDateService {

	@Autowired
	TerminalDateRepository terminalDateRepository;
	
	@Autowired
	GasTankCalibrationDataServiceImpl gasCalibration;

	@Autowired
	TerminalRepository terminalRepository;
	
	public List<TerminalDate> getVehicleFromPeriod(long millisFrom, long millisTo, int terminalNumber){
		Terminal terminal = terminalRepository.getTerminalById(terminalNumber);

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

	public List<TerminalDate> getVehicleFromPeriod(String dateFrom, String timeFrom, String dateTo, String timeTo,
			int terminalNumber) {

		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		LocalDateTime timeFromLDT = LocalDateTime.from(format.parse(dateFrom + " " + timeFrom));
		LocalDateTime timeToLTD = LocalDateTime.from(format.parse(dateTo + " " + timeTo));

		ZonedDateTime fromZdt = timeFromLDT.atZone(ZoneId.systemDefault());
		long millisFrom = fromZdt.toInstant().toEpochMilli();

		ZonedDateTime toZdt = timeToLTD.atZone(ZoneId.systemDefault());
		long millisTo = toZdt.toInstant().toEpochMilli();

		Terminal terminal = terminalRepository.getTerminalById(terminalNumber);

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
		Terminal terminal = terminalRepository.getTerminalById(terminalNumber);
		TerminalDate point = terminalDateRepository.getLastSignal(terminal.getImei());
		
		return new Date(point.getMessageDate());
	}
	
	public TerminalDate getLastSignal(int terminalNumber){
		Terminal terminal = terminalRepository.getTerminalById(terminalNumber);
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
		List<TerminalDate> allDateFromTerminal = getVehicleFromPeriod(millisFrom, millisTo, terminalNumber);
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
		for(List<TerminalDate> currentDate: stops){
			if(currentDate.size()>1) result.add(currentDate);
		}
		return result;
	}
	
	public List<Refueling> getRefulingDate(List<List<TerminalDate>> stops, int terminalNumber){

		List<Refueling> refulings = new ArrayList<>();
		boolean isLeftRefuling = false;
		boolean isRightRefuling = false;
		System.out.println("**************** Stops ***********************");
		for(List<TerminalDate> stopsList: stops){
			System.out.println("start: " + new Date(stopsList.get(0).getMessageDate()) + " end " + new Date(stopsList.get(stopsList.size()-1).getMessageDate()));
		}
		for(List<TerminalDate> stopDateList: stops){
			TerminalDate previous = null;
			List<TerminalDate> refulingLeftDates = new ArrayList<>();
			List<TerminalDate> refulingRightDates = new ArrayList<>();
			for(TerminalDate current: stopDateList){
				
				if(previous == null){
					previous = current;
					continue;
				}
				//left tank
				if(previous.getLeftGasTank()<current.getLeftGasTank()){
					isLeftRefuling = true;
					refulingLeftDates.add(previous);
				}else if(isLeftRefuling && previous.getLeftGasTank()>=current.getLeftGasTank()){
					isLeftRefuling = false;
					refulingLeftDates.add(current);
				}
				//right tank
				if(previous.getRightGasTank()<current.getRightGasTank()){
					isRightRefuling = true;
					refulingRightDates.add(previous);
				}else if(isRightRefuling && previous.getRightGasTank()>=current.getRightGasTank()){
					isRightRefuling = false;
					refulingRightDates.add(current);
				}
				previous = current;
			}
			Refueling refuling = new Refueling();
			
			long refulingLeftStartTime = Long.MAX_VALUE;
			long refulingRightStartTime = Long.MAX_VALUE;
			long refulingLeftEndTime = Long.MIN_VALUE;
			long refulingRightEndTime = Long.MIN_VALUE;
			
			double leftFuelDataAtStart = 0;
			double leftFuelDataAtEnd = 0;
			double rightFuelDataAtStart = 0;
			double rightFuelDataAtEnd = 0;
			


			if(refulingLeftDates.size()>0){
				refulingLeftStartTime = refulingLeftDates.get(0).getMessageDate();
				refulingLeftEndTime = refulingLeftDates.get(refulingLeftDates.size()-1).getMessageDate();
				leftFuelDataAtStart = gasCalibration.getFuelLevel(terminalNumber, 1, refulingLeftDates.get(0).getLeftGasTank());
				leftFuelDataAtEnd = gasCalibration.getFuelLevel(terminalNumber, 1, refulingLeftDates.get(refulingLeftDates.size()-1).getLeftGasTank());
			}
			if(refulingRightDates.size()>0){
				refulingRightStartTime = refulingRightDates.get(0).getMessageDate();
				refulingRightEndTime = refulingRightDates.get(refulingRightDates.size()-1).getMessageDate();
				rightFuelDataAtStart = gasCalibration.getFuelLevel(terminalNumber, 2, refulingRightDates.get(0).getRightGasTank());
				rightFuelDataAtEnd = gasCalibration.getFuelLevel(terminalNumber, 2, refulingRightDates.get(refulingRightDates.size()-1).getRightGasTank());

			}
			
			Date startDate = new Date(refulingLeftStartTime<=refulingRightStartTime?refulingLeftStartTime:refulingRightStartTime);	
			refuling.setStartTime(startDate);
			Date endDate = new Date(refulingLeftEndTime>=refulingRightEndTime?refulingLeftEndTime:refulingRightEndTime);
			refuling.setEndTime(endDate);
			
			refuling.setFuelForStart(Math.floor(leftFuelDataAtStart + rightFuelDataAtStart));
			refuling.setFuelForEnd(Math.floor(leftFuelDataAtEnd + rightFuelDataAtEnd));
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
	

}
