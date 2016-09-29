package com.ksgagro.gps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ksgagro.gps.domain.MenuItem;
import com.ksgagro.gps.service.AgroFieldsService;

@Controller
public class AdminController {
	
	@Autowired
	private AgroFieldsService agroFieldsService;
	
	@RequestMapping("/adminPage")
	ModelAndView buildAdminPage(){
		ModelAndView model = new ModelAndView("adminPage");
		MenuItem[] menuItems = {new MenuItem("Добавление полей", "getAddFieldPage"), new MenuItem("Работа с тарировочными таблицами", "getCalibratioTablePage")};
		model.addObject("menuItems", menuItems);
		return model;
	}
	
	@RequestMapping("/getAddFieldPage")
	ModelAndView buildAddFieldsPage(){
		ModelAndView model = new ModelAndView("addFieldsPage");
		MenuItem[] menuItems = {new MenuItem("Добавление полей", "/getAddFieldPage"), new MenuItem("Работа с тарировочными таблицами", "/getCalibratioTablePage")};
		model.addObject("menuItems", menuItems);
		model.addObject("fields", agroFieldsService.getJsonFields());
		return model;
	}

}
