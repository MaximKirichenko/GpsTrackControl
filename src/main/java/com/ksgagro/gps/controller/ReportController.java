package com.ksgagro.gps.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ksgagro.gps.dto.ReportGroupDTO;
import com.ksgagro.gps.dto.TrackRequestDTO;
import com.ksgagro.gps.domain.AgroPayContract;
import com.ksgagro.gps.domain.TerminalDate;
import com.ksgagro.gps.domain.Vehicle;
import com.ksgagro.gps.service.AgroFieldsService;
import com.ksgagro.gps.service.CarSignalService;
import com.ksgagro.gps.service.ContractService;
import com.ksgagro.gps.service.TerminalDateService;
import com.ksgagro.gps.service.VehicleService;

@Controller
public class ReportController {
	@Autowired
	ContractService contractService;
	
	@Autowired
	AgroFieldsService agroFieldsService;
	
	@Autowired
	CarSignalService carSignal;
	
	@Autowired
	VehicleService vehicleService;
	
	@Autowired
	TerminalDateService terminalDateService;
	
	@RequestMapping(value = "/report")
	public ModelAndView buildChartPage(){
		ModelAndView model = new ModelAndView("report");
		
		return model;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/groupReport")
	public @ResponseBody List<ReportGroupDTO> getGroupReport(@RequestBody TrackRequestDTO periodDtoJson){
		List<Vehicle> vehicleList = vehicleService.getFilteredList();
		List<ReportGroupDTO> report = new ArrayList<ReportGroupDTO>();
		for(Vehicle vehicle: vehicleList){
			ReportGroupDTO item = new ReportGroupDTO();
			List<TerminalDate> terminalDates = terminalDateService.getTerminalDateAboutVehicleFromPeriod(periodDtoJson.getDataFrom(), periodDtoJson.getDataTo(), vehicle.getId());
			terminalDates = terminalDateService.filterData(terminalDates);
			double consumption = terminalDateService.getCanConsumption(terminalDates);
			double mileage = terminalDateService.getPathLength(terminalDates);
			item.setVehicle(vehicle);
			item.setConsumption(consumption);
			item.setMileage(mileage);
			report.add(item);
			
		}
		return report;
	}
	
	@RequestMapping("/closeContract")
	public String getContract(Model model){
		List<AgroPayContract> list = contractService.getHotContracts();
		model.addAttribute("contracts", list);
		return "closeContractReport";
	}
	@RequestMapping("/openContract")
	public String getOpenedContract(Model model){
		List<AgroPayContract> list = contractService.getSignedContracts();
		model.addAttribute("contracts", list);
		return "openContractReport";
	}
	@RequestMapping("/kadastrReport")
	public String getKadastrReport(Model model){
		model.addAttribute("totalArea", agroFieldsService.getTotalArea());
		model.addAttribute("totalAmount", agroFieldsService.getFieldsSum());
		model.addAttribute("firmsArea", agroFieldsService.getFirmsAreaList());
		model.addAttribute("notNullContract", agroFieldsService.getNotNullContracts());
		
		return "kadastrReport";
	}
	@RequestMapping("/carActiveReport")
	public String getCarActivitiReport(Model model){
		model.addAttribute("cars", carSignal.gelLastSignal());
		return "lastSignalReport";
	}

}
