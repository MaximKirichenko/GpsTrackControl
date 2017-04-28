package com.ksgagro.gps.controller.locatedimage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Maxim Kirichenko on 23.04.17.
 */
@Controller
public class LocatedImageController {

    @RequestMapping(value = "/located/image")
    public ModelAndView buildChartPage(){
        ModelAndView model = new ModelAndView("located_image");
        return model;
    }

    @RequestMapping(value = "/located/image/upload")
    public Object upload(@RequestParam MultipartFile image){
        System.out.println(image);
        return true;
    }
}
