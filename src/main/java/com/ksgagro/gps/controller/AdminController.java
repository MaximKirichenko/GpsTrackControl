package com.ksgagro.gps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {
	@RequestMapping("/adminPage")
	ModelAndView buildAdminPage(){
		ModelAndView model = new ModelAndView("adminPage");
		return model;
	}

}
