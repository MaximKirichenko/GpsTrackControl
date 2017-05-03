package com.ksgagro.gps.controller.locatedimage.controllers;

import com.ksgagro.gps.logic.image.service.LocatedImageService;
import javaxt.io.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by Maxim Kirichenko on 23.04.17.
 */
@Controller
public class LocatedImageController {

    @Autowired LocatedImageService imageService;

    @RequestMapping(value = "/located/image")
    public ModelAndView buildChartPage(){
        ModelAndView model = new ModelAndView("located_image");
        return model;
    }

    @RequestMapping(value = "/located/image/upload", method = POST)
    public String upload(@RequestParam MultipartFile image){
        try {
            imageService.save(new Image(image.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
        }
        return "located_image";
    }
}
