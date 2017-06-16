package com.ksgagro.gps.service.impl;

import com.ksgagro.gps.domain.*;
import com.ksgagro.gps.dto.MultiTrackQuery;
import com.ksgagro.gps.dto.TrackBO;
import com.ksgagro.gps.repository.TerminalDateRepository;
import com.ksgagro.gps.repository.TerminalRepository;
import com.ksgagro.gps.service.GasTankCalibrationDataService;
import com.ksgagro.gps.service.TrackService;
import com.ksgagro.gps.service.TerminalService;
import com.ksgagro.gps.service.VehicleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class TrackServiceImpl implements TrackService {

	@Autowired private VehicleService vehicleService;
	@Autowired private TerminalService terminalService;
	@Autowired private TerminalRepository terminalRepository;
	@Autowired private GasTankCalibrationDataService gasCalibration;
	@Autowired private TerminalDateRepository trackRepository;

	Logger logger = Logger.getLogger(TrackServiceImpl.class);
	
	@Override
	public List<TrackEntity> tracks(long millisFrom, long millisTo, int terminalNumber){
		Terminal terminal = terminalRepository.get(terminalNumber);
		return trackRepository.list(millisFrom, millisTo, terminal.getImei());
	}
	
	@Override
	public List<TrackEntity> filterData(List<TrackEntity> inputList){
		boolean lastCoordinateIsZero = false;
		List<TrackEntity> result = new ArrayList<TrackEntity>();
		for (int i = 0; i < inputList.size(); i++) {
			
			if(inputList.get(i).getLatitude()==0||inputList.get(i).getLongitude()==0){
				lastCoordinateIsZero = true;
				continue;
			}
			if (result.size()==0) {
				result.add(inputList.get(i));
			} else {
				int lastElement = result.size() - 1;
				if (
						!lastCoordinateIsZero &&
						((result.get(lastElement).getLatitude() != inputList.get(i).getLatitude()) || 
						(result.get(lastElement).getLongitude() != inputList.get(i).getLongitude()))
						) {
					
					double difLat = Math.abs(result.get(lastElement).getLatitude() - inputList.get(i).getLatitude());
					double difLong = Math.abs(result.get(lastElement).getLongitude() - inputList.get(i).getLongitude());

					if ((difLat < 0.01) && (difLong < 0.02)&&inputList.get(i).getNumberSatellite()>3) {
						
						result.add(inputList.get(i));
					}
					
				}
				lastCoordinateIsZero = false;
			}
		}

		return result;
	}

	@Override
	public List<TrackEntity> tracks(String dateFrom, String timeFrom, String dateTo, String timeTo,
									int terminalNumber) {

		long epochTimeFrom = epochTime(dateFrom, timeFrom);
		long epochTimeTo = epochTime(dateTo, timeTo);
		Terminal terminal = terminalRepository.get(terminalNumber);

		List<TrackEntity> tracks = trackRepository.list(epochTimeFrom, epochTimeTo, terminal.getImei());
		List<TrackEntity> ret = new ArrayList<TrackEntity>();

		for (int i = 0; i < tracks.size(); i++) {

			if (i == 0) {
				ret.add(tracks.get(i));
			} else {
				int lastElement = ret.size() - 1;
				if (ret.get(lastElement).getLatitude() != tracks.get(i).getLatitude()
						&& ret.get(lastElement).getLongitude() != tracks.get(i).getLongitude()) {
					// if (result.size() > 0) {
					double difLat = Math.abs(ret.get(lastElement).getLatitude() - tracks.get(i).getLatitude());
					double difLong = Math.abs(ret.get(lastElement).getLongitude() - tracks.get(i).getLongitude());

					if ((difLat < 0.01) && (difLong < 0.01)) {
						ret.add(tracks.get(i));
					}
					// }
				}
			}
		}

		return ret;
	}

	private Long epochTime(String date, String time){
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime localDateTime = LocalDateTime.from(format.parse(date + " " + time));
		ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
		return zonedDateTime.toInstant().toEpochMilli();

	}
	
	public double getPathLength(List<TrackEntity> terminalDate) {
		double distance = 0.0;
		TrackEntity first = null;
		TrackEntity second = null;
		
		for (TrackEntity aList : terminalDate) {
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
	
	@Override
	public List<TrackEntity> last(){
		return trackRepository.getLastSignals();
	}
	
	@Override
	public double getCanConsumption(List<TrackEntity> terminalDates){
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
		
        double cl1 = Math.cos(firstLat);
        double cl2 = Math.cos(secondLat);
        double sl1 = Math.sin(firstLat);
        double sl2 = Math.sin(secondLat);
        
        double delta = secondLong - firstLong;
        
        double cdelta = Math.cos(delta);
        double sdelta = Math.sin(delta);
		
        double y = Math.sqrt(Math.pow(cl2 * sdelta, 2) + Math.pow(cl1 * sl2 - sl1 * cl2 * cdelta, 2));
        double x = sl1 * sl2 + cl1 * cl2 * cdelta;
        double ad = Math.atan2(y, x);
        distance = ad * 6372795;
        return distance;
	}
	
	private static double round(double d) {	   
	      return new BigDecimal(d).setScale(2, RoundingMode.UP).doubleValue();
	   }

	public Date getStartMovementTime(List<TrackEntity> terminalDate) {
		for(TrackEntity item: terminalDate){
			double longitude = 0;
			double latitude = 0;
			if(item.getSpeed()>0){
				return new Date(item.getMessageDate());
			}
			if(longitude-item.getLongitude()!=0||latitude-item.getLatitude()!=0){
				return new Date(item.getMessageDate());
			}
			longitude = item.getLongitude();
			latitude = item.getLatitude();
		}
		return null;
	}

	public Date getFinishMovementTime(List<TrackEntity> terminalDate) {
		Collections.reverse(terminalDate);
		return getStartMovementTime(terminalDate);
	}

	@Override
	public List<List<TrackEntity>> stopList(long from, long to, int terminalNumber){
		return stopList(tracks(from, to, terminalNumber));
	}

	@Override
	public List<List<TrackEntity>> stopList(List<TrackEntity> trackEntities) {
		boolean isStop = false;

		List<List<TrackEntity>> stops = new ArrayList<>();
		List<TrackEntity> stopList = new ArrayList<>();
		for(TrackEntity track: trackEntities){
			if(track.getSpeed()==0){
				isStop = true;
				stopList.add(track);
			}else if(isStop && track.getSpeed() > 0){
				stops.add(stopList);
				stopList = new ArrayList<>();
				isStop = false;
			}
		}

		return filterStop(stops);
	}

	private List<List<TrackEntity>> filterStop(List<List<TrackEntity>> stops){
		List<List<TrackEntity>> result = new ArrayList<>();
		for(List<TrackEntity> currentData: stops){
			if(currentData.size()>1) result.add(currentData);
		}
		return result;
	}
	
	public List<Refueling> getRefulingDate(List<List<TrackEntity>> stops, int terminalNumber){

		List<Refueling> refulings = new ArrayList<>();
		
		
		for(List<TrackEntity> stopDateList: stops){
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
//				System.out.println("Left fuel data level: " + leftFuelDataAtStart + " " + new Date(refulingLeftEndTime));
			}
			if(refData.rightTanks.size()>0){
				refulingRightStartTime = refData.getRefulingRightStartDate();
				
				
				List<GasTankCalibrationData> rightTankCalibrationData = gasCalibration.getRighTankCalibrationDatatValues(terminalNumber, refulingRightStartTime);
				
				rightFuelDataAtStart = gasCalibration.getFuelLevel(refData.getStartFuelLevelRightTank(), rightTankCalibrationData);
				rightFuelDataAtEnd = gasCalibration.getFuelLevel(refData.getRightMaxFuelLevel(), rightTankCalibrationData);
				refulingRightEndTime = refData.getRefulingRightFinishDate();
				
//				System.out.println("Right fuel data at start: " + rightFuelDataAtStart + " " + new Date(refulingRightStartTime));
//				System.out.println("Right fuel data at end: " + rightFuelDataAtEnd + " " + new Date(refulingRightEndTime));

			}
			
			Date startDate = new Date(refulingLeftStartTime<=refulingRightStartTime?refulingLeftStartTime:refulingRightStartTime);	
			
			Date endDate = new Date(refulingLeftEndTime>=refulingRightEndTime?refulingLeftEndTime:refulingRightEndTime);
			
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
	private DataAboutRefuling getRefulingFromStopList(List<TrackEntity> stopList){

		for(TrackEntity terminalDate: stopList){
			System.out.println(terminalDate);
		}

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
	private DataAboutRefuling analizeAndAddLeftTankDataIfRefulingActive(DataAboutRefuling refuling, TrackEntity previousDataInArray, TrackEntity curentDataInArray, boolean isLastData){
		
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
	private DataAboutRefuling analizeAndAddRightTankDataIfRefulingActive(DataAboutRefuling refuling, TrackEntity previous, TrackEntity curent, boolean isLastData){
		
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
		
		List<TrackEntity> leftTanks;
		List<TrackEntity> rightTanks;
		
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
		
		public void setStartFuelLevelLeftTank(TrackEntity terminalDate){
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
		public void setStartFuelLevelRightTank(TrackEntity terminalDate){
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
			for(TrackEntity curent: leftTanks){
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
			for(TrackEntity curent: rightTanks){
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

		public void addLeftTanksData(TrackEntity data){
			leftTanks.add(data);
		}
		public void addRightTanksData(TrackEntity data){
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

	@Override
	public List<TrackBO> tracks(MultiTrackQuery query) {
		Map<String, TrackBO> map = new HashMap<>();
		for(TrackEntity trackEntity : trackRepository.tracksByImeis(
				query.getDataFrom(),
				query.getDataTo(),
				imeis(terminalService.getTerminalsByVehicles(query.getVehicleIds())))
				){
			if(map.get(trackEntity.getImei())!=null)
				toBO(map.get(trackEntity.getImei()), trackEntity);
			else
				map.put(trackEntity.getImei(), toBO(null, trackEntity));
		}
		List<TrackBO> ret = new ArrayList<>(map.values());
		for (TrackBO bo : ret) {
			bo.setStopList(stopList(bo.getTrackEntities()));
		}
		return ret;
	}

	private TrackBO toBO(TrackBO trackBO, TrackEntity trackEntity) {
		if(trackBO==null){
			trackBO = new TrackBO();
			Terminal terminal = terminalService.getTerminalByImei(trackEntity.getImei());
			trackBO.setVehicle(vehicleService.getVehicleByTerminalNumber(terminal.getId()));
			trackBO.setTerminal(terminal);
			trackBO.addTrackEntity(trackEntity);
			trackBO.addLeftTankFuelPoint(trackEntity.getMessageDate(), trackEntity.getLeftGasTank());
			trackBO.addRightTankFuelPoint(trackEntity.getMessageDate(), trackEntity.getRightGasTank());
			return trackBO;
		}
		trackBO.addTrackEntity(trackEntity);
		trackBO.addLeftTankFuelPoint(trackEntity.getMessageDate(), trackEntity.getLeftGasTank());
		trackBO.addRightTankFuelPoint(trackEntity.getMessageDate(), trackEntity.getRightGasTank());
		trackBO.setTrackInfo(new TrackInfo(getPathLength(trackBO.getTrackEntities())));

		return trackBO;
	}

	private List<String> imeis(List<Terminal> terminals){
		List<String> ret = new ArrayList<>();
		for(Terminal terminal: terminals)
			ret.add(terminal.getImei());
		return ret;
	}

	@Override
	public TrackEntity last(String imei) {
		return trackRepository.getLastSignal(imei);
	}


	

}
