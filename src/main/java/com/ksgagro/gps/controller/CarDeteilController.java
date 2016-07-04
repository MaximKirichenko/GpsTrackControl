package com.ksgagro.gps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.ksgagro.gps.controller.dto.TrackRequestDTO;
import com.ksgagro.gps.domain.AgroPayContract;
import com.ksgagro.gps.domain.Location;
import com.ksgagro.gps.domain.Pay;
import com.ksgagro.gps.domain.TerminalDate;
import com.ksgagro.gps.domain.Vehicle;
import com.ksgagro.gps.domain.VehicleGroup;
import com.ksgagro.gps.domain.repository.AgroPayConractRepository;
import com.ksgagro.gps.domain.repository.LocationRepository;
import com.ksgagro.gps.domain.repository.PayRepository;
import com.ksgagro.gps.domain.repository.VehicleGroupRepository;
import com.ksgagro.gps.domain.repository.VehicleRepository;
import com.ksgagro.gps.domain.service.AgroFieldsService;
import com.ksgagro.gps.domain.service.TerminalDateService;

@Controller
@RequestMapping("/report")
public class CarDeteilController {

//	@Autowired
//	private TerminalDateService terminalDateService;
//	
//	@Autowired
//	private LocationRepository locationRepository;
//	
//	@Autowired
//	private VehicleGroupRepository vehicleGroupRepository;
//	
//	@Autowired
//	private VehicleRepository vehicleRepository;
//	
//	@Autowired
//	AgroFieldsService agroFieldsService;
//	
//	@Autowired
//	PayRepository payRepository;	
//	
//	@Autowired
//	AgroPayConractRepository agroPayConractRepository;
//	
//	List<TerminalDate> list = null;
//	
//	@RequestMapping("/trackPage")
//	public String returnTrackPage(@RequestParam("terminalNumber") int numberTerminal, Model model){
//		TerminalDate terminalDate = terminalDateService.getLastSignal(numberTerminal);
//		Gson gson = new Gson();
//		String point = gson.toJson(terminalDate);
//		System.out.println(terminalDate);
//		List<Location> listLocation = locationRepository.getList();	
//		List<VehicleGroup> listGroup = vehicleGroupRepository.getList();
//		List<Vehicle> listVehicle = vehicleRepository.getList();
//		List<Vehicle> vehicleInEnterprise = vehicleRepository.getListFromLocation(1);	
//		
//		model.addAttribute("fields", agroFieldsService.getJsonFields());
//		model.addAttribute("numberTerminal", numberTerminal);
//		model.addAttribute("listLocation", listLocation);
//		model.addAttribute("listGroup", listGroup);
//		model.addAttribute("listVehicle", listVehicle);
//		model.addAttribute("vehicleInEnterprise", vehicleInEnterprise);
//		model.addAttribute("terminalDate", point);
//		
//		return "trackView";
//	}
//	@RequestMapping(method = RequestMethod.POST, value = "/report/buildTrackFAst")
//	public @ResponseBody List<TerminalDate> buildFuelChart(@RequestBody TerminalDateDTO periodDtoJson, Model model){
//		List<TerminalDate> list = terminalDateService.getVehicleFromPeriod(periodDtoJson.getDataFrom(), periodDtoJson.getDataTo(), periodDtoJson.getTerminalNumber());
//		System.out.println(periodDtoJson);
//		list = terminalDateService.filterData(list);
//		return list;
//	}
//	
//	@RequestMapping(method = RequestMethod.POST, value = "/report/getKadastrInfo")
//	public @ResponseBody String getKadastrInfo(@RequestBody String kadastrNumber){
//		
//		kadastrNumber = kadastrNumber.substring(1,  kadastrNumber.length()-1);
//		System.out.println(kadastrNumber);
//		List<Pay> pays = payRepository.getPay(kadastrNumber);
//		StringBuilder strBuilder = new StringBuilder("\"" + kadastrNumber.toString() + "</br>");
//		for(Pay pay: pays){
//			if(pay.getId()>0){
//				AgroPayContract contract = agroPayConractRepository.getAgroPayContract(pay);
//				strBuilder.append(pay.getEmp().getName() + " "+pay.getEmp().getSurname() + "</br>Договор от: " + contract.getOpen() +
//						"</br>Действителен до: " + contract.getClose() + "</br>Площадь: " + pay.getArea() + "</br>");
//			}
//			
//		}
//		strBuilder.append("\"");
//		System.out.println(strBuilder.toString());
//		return strBuilder.toString();
//	}
//
//	@RequestMapping
//	public @ResponseBody List<TerminalDate> buildReport(@RequestParam("dateFrom") String dateFrom,
//			@RequestParam("dateTo") String dateTo, @RequestParam("timeFrom") String timeFrom,
//			@RequestParam("timeTo") String timeTo, @RequestParam("terminalNumber") int terminalNumber, Model model) {
//		
//		return list;
//	}
//
//	@RequestMapping("/build")
//	public String getReport(@RequestParam("dateFrom") String dateFrom, @RequestParam("dateTo") String dateTo,
//			@RequestParam("timeFrom") String timeFrom, @RequestParam("timeTo") String timeTo,
//			@RequestParam("terminalNumber") int terminalNumber, Model model) {
//		model.addAttribute("dateFrom", dateFrom);
//		model.addAttribute("dateTo", dateTo);
//		model.addAttribute("timeFrom", timeFrom);
//		model.addAttribute("timeTo", timeTo);
//		model.addAttribute("terminalNumber", terminalNumber);
//		System.out.println("Build page with next data: " + dateFrom + " " + dateTo + " " + timeFrom + " " + timeTo + " "
//				+ terminalNumber);
//		
//		list = terminalDateService.getVehicleFromPeriod(dateFrom, timeFrom, dateTo, timeTo,
//				terminalNumber);
//		
//		if(list.size() == 0) {
//			model.addAttribute("date", terminalDateService.getLastSignalDate(terminalNumber));
//			return "emptyTrack";
//		}
//		model.addAttribute("pathLength", terminalDateService.getPathLength(list));
//		
//		return "track";
//	}
}
