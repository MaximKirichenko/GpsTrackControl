package com.ksgagro.gps.controller;

import com.ksgagro.gps.controller.JSON.TerminalDateJSON;
import com.ksgagro.gps.controller.JSON.TerminalDateMapper;
import com.ksgagro.gps.domain.TerminalDate;
import com.ksgagro.gps.dto.TerminalDateDTO;
import com.ksgagro.gps.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ksgagro.gps.domain.MenuItem;
import com.ksgagro.gps.service.AgroFieldsService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
	
	@Autowired private AgroFieldsService agroFieldsService;
	@Autowired private TerminalService terminalService;

	@Autowired private TerminalDateMapper terminalDateMapper;
	
	@RequestMapping("/adminPage")
	ModelAndView buildAdminPage(){
		ModelAndView model = new ModelAndView("adminPage");
		MenuItem[] menuItems = {new MenuItem("Add field", "getAddFieldPage"), new MenuItem("Работа с тарировочными таблицами", "getCalibratioTablePage")};
		model.addObject("menuItems", menuItems);
		return model;
	}

//	@RequestMapping("/getAddFieldPage")
//	ModelAndView buildAddFieldsPage(){
//		ModelAndView model = new ModelAndView("addFieldsPage");
//		MenuItem[] menuItems = {new MenuItem("Добавление полей", "/getAddFieldPage"), new MenuItem("Работа с тарировочными таблицами", "/getCalibratioTablePage")};
//		model.addObject("menuItems", menuItems);
//		model.addObject("fields", agroFieldsService.getJsonFields());
//		return model;
//	}

	@RequestMapping("/terminal/list")
	public ModelAndView terminalList(){
		ModelAndView model = new ModelAndView("terminalList");
		List<TerminalDateJSON> list = terminalDateMapper.toJSONs(terminalService.getTerminals());
		model.addObject("list", list);
		return model;
	}

}
