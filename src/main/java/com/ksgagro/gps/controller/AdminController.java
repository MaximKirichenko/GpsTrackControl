package com.ksgagro.gps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ksgagro.gps.domain.MenuItem;

@Controller
public class AdminController {
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
		return model;
	}

}
