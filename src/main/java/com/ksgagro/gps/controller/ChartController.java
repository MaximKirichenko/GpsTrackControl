package com.ksgagro.gps.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ksgagro.gps.domain.*;
import com.ksgagro.gps.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ksgagro.gps.dto.FuelChartDTO;
import com.ksgagro.gps.dto.TrackRequestDTO;
import com.ksgagro.gps.repository.LocationRepository;
import com.ksgagro.gps.repository.VehicleGroupRepository;
import com.ksgagro.gps.repository.VehicleRepository;


@Controller
public class ChartController {
	
	@Autowired private ChartService chartService;
	@Autowired private TrackService trackService;
	@Autowired private VehicleService vehicleService;
	@Autowired private TerminalService terminalService;
	@Autowired private VehicleRepository vehicleRepository;
	@Autowired private LocationRepository locationRepository;
	@Autowired private VehicleGroupRepository vehicleGroupRepository;
	@Autowired private GasTankCalibrationDataService calibrationDataService;

	@RequestMapping(value = "/chart")
	public ModelAndView buildChartPage(){
		ModelAndView model = new ModelAndView("fuelLineChart");
		List<Location> listLocation = locationRepository.getList();	
		List<VehicleGroup> listGroup = vehicleGroupRepository.getList();
		List<Vehicle> listVehicle = vehicleService.getFilteredList();
		List<Vehicle> vehicleInEnterprise = vehicleRepository.getListFromLocation(1);	
//		String carName = car.getName() + " (" + car.getRegNumber() + ")";
//		model.addObject("numberTerminal", car.getNumberTerminal());
//		model.addObject("carName", carName);
		model.addObject("listLocation", listLocation);
		model.addObject("listGroup", listGroup);
		model.addObject("listVehicle", listVehicle);
		model.addObject("vehicleInEnterprise", vehicleInEnterprise);
		return model;
	}
	@RequestMapping(method = RequestMethod.POST, value = "/chart/fuel/build")
	public @ResponseBody FuelChartDTO buildFuelChart(@RequestBody TrackRequestDTO periodDtoJson){
		return chartService.getFuelChartData(
				periodDtoJson.getTerminalNumber(), 
				periodDtoJson.getDataFrom(), 
				periodDtoJson.getDataTo()
				);

	}
	@RequestMapping(value = "/chart/fuel")
	public String buildFuelChartPage(@RequestParam("terminalNumber") int vehicleId, Model model){
		Terminal terminal = terminalService.getTerminalByVehicle(vehicleId);
		TrackEntity terminalDate = trackService.last(terminal.getImei());
		List<Location> listLocation = locationRepository.getList();	
		List<VehicleGroup> listGroup = vehicleGroupRepository.getList();
		List<Vehicle> listVehicle = vehicleRepository.getList();
		List<Vehicle> vehicleInEnterprise = vehicleRepository.getListFromLocation(1);	

		model.addAttribute("listLocation", listLocation);
		model.addAttribute("listGroup", listGroup);
		model.addAttribute("listVehicle", listVehicle);
		model.addAttribute("vehicleInEnterprise", vehicleInEnterprise);
		Vehicle car = vehicleRepository.getVehicleById(vehicleId);
		String carName = car.getName() + " " + car.getRegNumber();
		model.addAttribute("carName", carName);
//		model.addAttribute("numberTerminal", car.getNumberTerminal());
		
		/*Detail table filing*/
		String status;
		Date date = new Date(terminalDate.getMessageDate());
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		
		status = df.format(date);
		double leftTanks = calibrationDataService.getFuelLevel(vehicleId, 1, terminalDate.getRightGasTank());
		
		double rightTanks = calibrationDataService.getFuelLevel(vehicleId, 2, terminalDate.getLeftGasTank());
		
		double totalFuel = leftTanks + rightTanks;
		double fuelFromCan = new BigDecimal(terminalDate.getCANFLS()).setScale(3, RoundingMode.UP).doubleValue();
		int speed = terminalDate.getSpeed();
		int engineSpeed = (int) (terminalDate.getEngineSpeed() / 4);
		
		model.addAttribute("inputVoltage", terminalDate.getPsv()/1000);
		model.addAttribute("voltageOnDevice", terminalDate.getvBat()/1000);
		model.addAttribute("leftTanks", leftTanks);
		model.addAttribute("rightTanks", rightTanks);
		model.addAttribute("totalFuel", totalFuel);
		model.addAttribute("canFuelLevel", fuelFromCan);
		model.addAttribute("consumptionCan", new BigDecimal(terminalDate.getCANFuelConsumption()*0.4).setScale(2, RoundingMode.UP).doubleValue() );
		model.addAttribute("engineSpeed", engineSpeed);
		model.addAttribute("speed", speed);
		model.addAttribute("car", car);
		model.addAttribute("statusTerminal", status);
		return "fuelLineChart";
	}
	
	
}
