package com.ksgagro.gps.controller;

import com.ksgagro.gps.dto.MulitiTrackRequestDTO;
import com.ksgagro.gps.dto.MultiTrackResponseDto;
import com.ksgagro.gps.dto.ReportTrackDto;
import com.ksgagro.gps.dto.TrackRequestDTO;
import com.ksgagro.gps.domain.*;
import com.ksgagro.gps.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
@Scope("session")
public class HomeController {
	
	@Autowired
	private VehicleGroupService vehicleGroupService;
	
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
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private MapObjectFieldService objectFieldService;
	
	@Autowired
	private MulitiTrackRequestDTO multiTrackRequestDTO;
	
	@Autowired 
	private ChartService chartService;
	
	Logger logger = Logger.getLogger(HomeController.class);
	
	@RequestMapping("/home")
	public ModelAndView helloPage(){
		ModelAndView model = new ModelAndView("home");
		//calibrationDataService.getFuelLevel(57, 1, 300);
		
		return model;
	}
	
//	@RequestMapping("/")
//	public ModelAndView monitoring(){
//		ModelAndView model = new ModelAndView("home");
//
//		List<Location> listLocation = locationRepository.getList();	
//		List<VehicleGroup> listGroup = vehicleGroupService.getList();
//		List<Vehicle> listVehicle = vehicleService.getList();
//		List<Vehicle> vehicleInEnterprise = vehicleService.getListFromLocation(1);
//
//		model.addObject("listLocation", listLocation);
//		model.addObject("listGroup", listGroup);
//		model.addObject("listVehicle", listVehicle);
//		model.addObject("vehicleInEnterprise", vehicleInEnterprise);
//		model.addObject("fields", agroFieldsService.getJsonFields());
//		
//		return model;
//	}
	@RequestMapping(method = RequestMethod.POST, value = "/buildTrack")
	public @ResponseBody ReportTrackDto buildTrack(@RequestBody TrackRequestDTO periodDtoJson, Model model){
		ReportTrackDto report = new ReportTrackDto();
		List<TerminalDate> list = terminalDateService.getTerminalDateAboutVehicleFromPeriod(periodDtoJson.getDataFrom(), periodDtoJson.getDataTo(), periodDtoJson.getTerminalNumber());

		list = terminalDateService.filterData(list);

		Collections.reverse(list);
		for(TerminalDate item: list){
			System.out.println(item);
		}
		report.setTerminalDateList(list);
		report.setPathLength(terminalDateService.getPathLength(list));
		report.setCanConsumption(terminalDateService.getCanConsumption(list)*(-1));
		report.setStartMovement(terminalDateService.getStartMovementTime(list));
		report.setFinishMovement(terminalDateService.getFinishMovementTime(list));
		return report;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/buildTracks")
	public @ResponseBody List<MultiTrackResponseDto> buildTracks(@RequestBody MulitiTrackRequestDTO trackRequest){
		multiTrackRequestDTO.setDataFrom(trackRequest.getDataFrom());
		multiTrackRequestDTO.setDataTo(trackRequest.getDataTo());
		multiTrackRequestDTO.setTerminalNumbers(trackRequest.getTerminalNumbers());
		System.out.println("track request\n" + trackRequest.getTerminalNumbers() + " " + new Date(trackRequest.getDataFrom()) + " " + new Date(trackRequest.getDataTo()));
		List<MultiTrackResponseDto> date = terminalDateService.getTerminalDateAboutVehiclesFromPeriod(multiTrackRequestDTO.getDataFrom(), multiTrackRequestDTO.getDataTo(), multiTrackRequestDTO.getTerminalNumbers());
		return date;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/buildChart")
	public @ResponseBody List<MultiTrackResponseDto> buildChart(@RequestBody Integer vehicleId){
		chartService.buildMultiTrackResponseDto(vehicleId, multiTrackRequestDTO.getDataFrom(), multiTrackRequestDTO.getDataTo());
		return null;
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
	
	@RequestMapping(method = RequestMethod.POST, value="/getLastTerminalData")
	public @ResponseBody TerminalDate getLastTerminalData(@RequestBody String imei){
		TerminalDate data = terminalDateService.getLastSignal(imei);
		
		return data;
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
	@RequestMapping("/")
	public String getHomeNew(Model model){
		model.addAttribute("vehicleMenuItems", vehicleService.getVehicleMenuItems());
		model.addAttribute("vehicleGroups", vehicleGroupService.getList());
		return "home_new";
	}
	
	@RequestMapping("/getFields")
	public @ResponseBody List<AgroFields> getFields(){
		@SuppressWarnings("unchecked")
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		boolean hasRole;
		for (GrantedAuthority authority : authorities) {
		     hasRole = authority.getAuthority().equals("ROLE_RENT");
		     if (hasRole) {
			  return null;
		     }
		  }
		return agroFieldsService.getAll();
	}
	
	@RequestMapping("/getEnterprises")
	public @ResponseBody List<Location> getEnterprises(){
		return locationService.getList();
	}
	@RequestMapping(method = RequestMethod.POST, value="/addMapObjectField")
	public @ResponseBody MapObjectField addMapObjectField(@RequestBody MapObjectField field){
		objectFieldService.addField(field);
		return field;
	}
	@RequestMapping("/getMapObjectField")
	public @ResponseBody List<MapObjectField> getMapObjectField(){
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		@SuppressWarnings("unchecked")
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		boolean hasRole;
		for (GrantedAuthority authority : authorities) {
		     hasRole = authority.getAuthority().equals("ROLE_RENT");
		     if (hasRole) {
			  return null;
		     }
		  }
		return objectFieldService.getAll();
	}
	@RequestMapping("/getFieldInfo")
	public @ResponseBody MapObjectField getMapObjectField(@RequestBody int poliId){
		System.out.println(poliId);
		return objectFieldService.get(poliId);
	}
}
