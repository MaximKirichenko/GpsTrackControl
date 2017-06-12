package com.ksgagro.gps.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.ksgagro.gps.domain.Location;
import com.ksgagro.gps.domain.TerminalDate;
import com.ksgagro.gps.domain.Vehicle;
import com.ksgagro.gps.domain.VehicleGroup;
import com.ksgagro.gps.repository.LocationRepository;
import com.ksgagro.gps.repository.VehicleGroupRepository;
import com.ksgagro.gps.repository.VehicleRepository;
import com.ksgagro.gps.service.ChartService;
import com.ksgagro.gps.service.GasTankCalibrationDataService;
import com.ksgagro.gps.service.TerminalDateService;
import com.ksgagro.gps.service.VehicleService;



@Controller
public class ChartController {
	
	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private VehicleGroupRepository vehicleGroupRepository;
	@Autowired
	private VehicleRepository vehicleRepository;
	@Autowired
	private ChartService chartService;
	@Autowired
	private TerminalDateService terminalDateService;
	@Autowired
	private GasTankCalibrationDataService calibrationDataService;
	@Autowired
	private VehicleService vehicleService;
	
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
//		List<List<TerminalDate>> stops = terminalDateService.getStops(periodDtoJson.getDataFrom(), periodDtoJson.getDataTo(), periodDtoJson.getTerminalNumber());
//		terminalDateService.getRefulingDate(stops, periodDtoJson.getTerminalNumber());
//		System.out.println("Stops size: " + stops.size());
//		for(List<TerminalDate> stopList: stops){
//			System.out.println("Stop at: " + new Date(stopList.get(0).getMessageDate()) + " run at " + new Date(stopList.get(stopList.size()-1).getMessageDate()));
//		}
		return chartService.getFuelChartData(
				periodDtoJson.getTerminalNumber(), 
				periodDtoJson.getDataFrom(), 
				periodDtoJson.getDataTo()
				);

	}
	@RequestMapping(value = "/chart/fuel")
	public String buildFuelChartPage(@RequestParam("terminalNumber") int terminalNumber, Model model){
		TerminalDate terminalDate = terminalDateService.getLastSignal(terminalNumber);
		List<Location> listLocation = locationRepository.getList();	
		List<VehicleGroup> listGroup = vehicleGroupRepository.getList();
		List<Vehicle> listVehicle = vehicleRepository.getList();
		List<Vehicle> vehicleInEnterprise = vehicleRepository.getListFromLocation(1);	

		model.addAttribute("listLocation", listLocation);
		model.addAttribute("listGroup", listGroup);
		model.addAttribute("listVehicle", listVehicle);
		model.addAttribute("vehicleInEnterprise", vehicleInEnterprise);
		Vehicle car = vehicleRepository.getVehicleById(terminalNumber);
		String carName = car.getName() + " " + car.getRegNumber();
		model.addAttribute("carName", carName);
//		model.addAttribute("numberTerminal", car.getNumberTerminal());
		
		/*Detail table filing*/
		String status;
		Date date = new Date(terminalDate.getMessageDate());
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		
		status = df.format(date);
		double leftTanks = calibrationDataService.getFuelLevel(terminalNumber, 1, terminalDate.getRightGasTank());
		
		double rightTanks = calibrationDataService.getFuelLevel(terminalNumber, 2, terminalDate.getLeftGasTank());
		
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
