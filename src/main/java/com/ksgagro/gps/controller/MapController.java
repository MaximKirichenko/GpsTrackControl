package com.ksgagro.gps.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ksgagro.gps.domain.AgroFields;

import com.ksgagro.gps.service.AgroFieldsService;




@Controller
@RequestMapping("/report/mapRedactor")
public class MapController {
	
	@Autowired
	AgroFieldsService agroFieldsService;
	

	
	
	@RequestMapping
	public String mapView(Model model){
		System.out.println(agroFieldsService.getJsonFields());
		model.addAttribute("fields", agroFieldsService.getJsonFields());

		return "mapView";
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody String addMapWithProperty(@RequestBody AgroFields polygon){
		
		agroFieldsService.setFields(polygon);
		System.out.println("************************************");
		System.out.println(polygon.getKadastr());
		
		return polygon.getKadastr();
	}

}
