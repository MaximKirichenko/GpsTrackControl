package com.ksgagro.gps.service.impl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksgagro.gps.domain.GasTankCalibrationData;
import com.ksgagro.gps.domain.Refueling;
import com.ksgagro.gps.domain.TrackEntity;
import com.ksgagro.gps.service.GasTankCalibrationDataService;
import com.ksgagro.gps.service.RefuelingService;

@Service
public class RefuelingServiceImpl implements RefuelingService {
	private static final int BIG_FUEL_DIFFERENS = 50;
	private static final int MIN_TIME_FOR_START_COMPARATION = 180000;
	private static final int MAX_PERIOD_FOR_COMPARE = 300000;

	@Autowired
	private GasTankCalibrationDataService gasTankService;

	private Refueling refueling = null;

	public List<Refueling> getRefuelings(List<TrackEntity> datasFromTerminal,
			List<GasTankCalibrationData> calibrationDatas) {
		long timeIntervalBetweenMessages = 0;
		boolean refuling = false;
		boolean inserting = false;
		ArrayList<TrackEntity> refulingItem = null;
		ArrayList<ArrayList<TrackEntity>> refulings = new ArrayList<ArrayList<TrackEntity>>();

		Deque<TrackEntity> dateDeque = new ArrayDeque<TrackEntity>(10);

		for (int i = 0; i < datasFromTerminal.size(); i++) {

			if (!dateDeque.isEmpty()) {
				timeIntervalBetweenMessages = dateDeque.getFirst().getMessageDate() - dateDeque.getLast().getMessageDate();
			}
			if (timeIntervalBetweenMessages >= MAX_PERIOD_FOR_COMPARE) {
				dateDeque.pollLast();
				continue;
			}
			if (timeIntervalBetweenMessages <= MIN_TIME_FOR_START_COMPARATION) {
				dateDeque.offerFirst(datasFromTerminal.get(i));
				continue;
			}
			dateDeque.offerFirst(datasFromTerminal.get(i));
			double engineDifferent = Math
					.abs(dateDeque.getFirst().getEngineSpeed() - dateDeque.getLast().getEngineSpeed());
			
			boolean isBigFuelDifferent = isBigFuelDifferent(dateDeque, calibrationDatas);

			if (isBigFuelDifferent && engineDifferent < 100 && dateDeque.getFirst().getEngineSpeed() < 2000) {
				refuling = true;
			} else if (refuling && engineDifferent > 1000 && !isBigFuelDifferent) {
				refuling = false;
				inserting = false;
			} 
			if (refuling) {
				if (!inserting) {
					refulingItem = new ArrayList<TrackEntity>();
					refulings.add(refulingItem);
					refulingItem.add(dateDeque.getLast());
					inserting = true;
				}
				refulingItem.add(datasFromTerminal.get(i));
			}

		}

		return buildRefuelingsList(refulings, datasFromTerminal, calibrationDatas);
	}
	private boolean isBigFuelDifferent(Deque<TrackEntity> fuelDeque, List<GasTankCalibrationData> calibrationDatas){
		double rightTankNext = gasTankService.getFuelLevel(fuelDeque.getLast().getRightGasTank(), calibrationDatas);
		double rightTankPrevious = gasTankService.getFuelLevel(fuelDeque.getFirst().getRightGasTank(), calibrationDatas);
		double rightFuelDifferent = rightTankPrevious - rightTankNext;
		double leftTankNext = gasTankService.getFuelLevel(fuelDeque.getLast().getLeftGasTank(), calibrationDatas);
		double leftTankPrevious = gasTankService.getFuelLevel(fuelDeque.getFirst().getLeftGasTank(), calibrationDatas);
		double leftFuelDifferent = leftTankPrevious - leftTankNext;
		
		return rightFuelDifferent>BIG_FUEL_DIFFERENS||leftFuelDifferent>BIG_FUEL_DIFFERENS;
	}
	private List<Refueling> buildRefuelingsList(ArrayList<ArrayList<TrackEntity>> refulings,
												List<TrackEntity> datasFromTerminal, List<GasTankCalibrationData> calibrationDatas)
	{
		refulings = combineRefulings(refulings);
		List<Refueling> list = new ArrayList<Refueling>();
		for (ArrayList<TrackEntity> ref : refulings) {
			refueling = new Refueling();

			long finishedDate = ref.get(ref.size() - 1).getMessageDate() + 300000;
			TrackEntity last = null;
			for (TrackEntity item : datasFromTerminal) {
				if (item.getMessageDate() >= finishedDate) {
					last = item;
					break;
				}
			}
			if (last == null) {
				last = datasFromTerminal.get(datasFromTerminal.size() - 1);
			}
			refueling.setFuelForStart(gasTankService.getFuelLevel(ref.get(0).getRightGasTank(), calibrationDatas));
			refueling.setFuelForEnd(gasTankService.getFuelLevel(last.getRightGasTank(), calibrationDatas));
			refueling.setScope(refueling.getFuelForEnd() - refueling.getFuelForStart());
			refueling.setStartTime(ref.get(0).getMessageDate());
			refueling.setEndTime(last.getMessageDate());
			refueling.setFuelCountAtEnd(ref.get(0).getCANFLS());
			refueling.setFuelCountAtStart(last.getCANFLS());
			list.add(refueling);
		}
		return list;
	}

	// Combine refuelings if time between one less then 1 hour
	private ArrayList<ArrayList<TrackEntity>> combineRefulings(ArrayList<ArrayList<TrackEntity>> refulings) {
		long startedTime = 0;
		long finishedTime = 0;
		for (int i = 0; i < refulings.size(); i++) {
			startedTime = refulings.get(i).get(refulings.get(i).size() - 1).getMessageDate();
			if (i > 0) {
				if (startedTime - finishedTime < 3600000) {
					refulings.get(i - 1).addAll(refulings.get(i));
					refulings.remove(i);
				}
			}
			if (i < refulings.size())
				finishedTime = refulings.get(i).get(refulings.get(i).size() - 1).getMessageDate();
		}
		return refulings;
	}

}
