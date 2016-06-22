package com.ksgagro.gps.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ksgagro.gps.controller.dto.ReportTrackDto;
import com.ksgagro.gps.controller.dto.TerminalDateDTO;
import com.ksgagro.gps.domain.Location;

import com.ksgagro.gps.domain.TerminalDate;
import com.ksgagro.gps.domain.Vehicle;
import com.ksgagro.gps.domain.VehicleDetailsTable;
import com.ksgagro.gps.domain.VehicleGroup;
import com.ksgagro.gps.domain.repository.LocationRepository;
import com.ksgagro.gps.domain.repository.VehicleGroupRepository;
import com.ksgagro.gps.domain.service.AgroFieldsService;
import com.ksgagro.gps.domain.service.GasTankCalibrationDataService;
import com.ksgagro.gps.domain.service.TerminalDateService;
import com.ksgagro.gps.domain.service.VehicleDetailsTableService;
import com.ksgagro.gps.domain.service.VehicleService;

@Controller
public class HomeController {
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private VehicleGroupRepository vehicleGroupRepository;
	
	@Autowired
	private VehicleService vehicleService;
	
	@Autowired
	private VehicleDetailsTableService vehicleDetailsTableService;
	
	@Autowired
	private TerminalDateService terminalDateService;
	
	@Autowired
	private GasTankCalibrationDataService calibrationDataService;
	
	
	@Autowired
	private AgroFieldsService agroFieldsService;
	
	
	Logger logger = Logger.getLogger(HomeController.class);
	
	@RequestMapping("/home")
	public ModelAndView helloPage(){
		ModelAndView model = new ModelAndView("home");
		//calibrationDataService.getFuelLevel(57, 1, 300);
		
		return model;
	}
	
	@RequestMapping("/")
	public ModelAndView monitoring(){
		ModelAndView model = new ModelAndView("home");

		List<Location> listLocation = locationRepository.getList();	
		List<VehicleGroup> listGroup = vehicleGroupRepository.getList();
		List<Vehicle> listVehicle = vehicleService.getList();
		List<Vehicle> vehicleInEnterprise = vehicleService.getListFromLocation(1);

		model.addObject("listLocation", listLocation);
		model.addObject("listGroup", listGroup);
		model.addObject("listVehicle", listVehicle);
		model.addObject("vehicleInEnterprise", vehicleInEnterprise);
		model.addObject("fields", agroFieldsService.getJsonFields());
		
		return model;
	}
	@RequestMapping(method = RequestMethod.POST, value = "/buildTrack")
	public @ResponseBody ReportTrackDto buildTrack(@RequestBody TerminalDateDTO periodDtoJson, Model model){
		ReportTrackDto report = new ReportTrackDto();
		List<TerminalDate> list = terminalDateService.getTerminalDateAboutVehicleFromPeriod(periodDtoJson.getDataFrom(), periodDtoJson.getDataTo(), periodDtoJson.getTerminalNumber());

//		System.out.println("NOT FILTERED: ");
//		for(TerminalDate data: list){
//			System.out.println(data);
//		}
		list = terminalDateService.filterData(list);
//		System.out.println("FILTERED: ");
//		for(TerminalDate data: list){
//			System.out.println(data);
//		}
		Collections.reverse(list);
		report.setTerminalDateList(list);
		report.setPathLength(terminalDateService.getPathLength(list));
		report.setCanConsumption(terminalDateService.getCanConsumption(list)*(-1));
		report.setStartMovement(terminalDateService.getStartMovementTime(list));
		report.setFinishMovement(terminalDateService.getFinishMovementTime(list));
		return report;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/carInfo")
	public @ResponseBody Vehicle getCarInfo(@RequestBody int terminalNumber){
		logger.info("Selected vehicle id: " + terminalNumber);
		Vehicle vehicle = vehicleService.getVehicleById(terminalNumber);
		return vehicle;
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value="/getVehicleDeteilTable")
	public @ResponseBody VehicleDetailsTable getLastPoint(@RequestBody int vehicleId){
		logger.info("Номер терминала: " + vehicleId);
		VehicleDetailsTable table = vehicleDetailsTableService.createVehicleDetailsTableById(vehicleId);
		return table;
	}

	

	
	@RequestMapping("/carDetails")
	public String getCarByTerminalNumber(@RequestParam("terminalNumber") int terminalNumber, Model model){
		TerminalDate terminalDate = terminalDateService.getLastSignal(terminalNumber);
		Vehicle car = vehicleService.getVehicleById(terminalNumber);
		String status;
		Date date = new Date(terminalDate.getMessageDate());
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		
		status = df.format(date);
		
		double leftTanks = calibrationDataService.getFuelLevel(terminalNumber, 1, terminalDate.getRightGasTank());
		
		double rightTanks = calibrationDataService.getFuelLevel(terminalNumber, 2, terminalDate.getLeftGasTank());
		
		double totalFuel = leftTanks + rightTanks;
		//double fuelFromCan = new BigDecimal(terminalDate.getCANFLS()*0.0017236857).setScale(3, RoundingMode.UP).doubleValue();
		double fuelFromCan = new BigDecimal(terminalDate.getCANFLS()).setScale(3, RoundingMode.UP).doubleValue();
		
		int speed = terminalDate.getSpeed();
		int engineSpeed = (int) (terminalDate.getEngineSpeed() / 4);
		model.addAttribute("car", car);
		model.addAttribute("statusTerminal", status);
		model.addAttribute("terminalDate", terminalDate);
		model.addAttribute("inputVoltage", terminalDate.getPsv()/1000);
		model.addAttribute("voltageOnDevice", terminalDate.getvBat()/1000);
		model.addAttribute("leftTanks", leftTanks);
		model.addAttribute("rightTanks", rightTanks);
		model.addAttribute("totalFuel", totalFuel);
		model.addAttribute("canFuelLevel", fuelFromCan);
		model.addAttribute("consumptionCan", new BigDecimal(terminalDate.getCANFuelConsumption()*0.4).setScale(2, RoundingMode.UP).doubleValue() );
		model.addAttribute("engineSpeed", engineSpeed);
		model.addAttribute("speed", speed);
		System.out.println(car);
		return "carDetails";
	}
	@RequestMapping("/home_new")
	public String getHomeNew(){
		
		return "home_new";
	}
}
