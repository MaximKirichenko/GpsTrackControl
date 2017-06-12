package com.ksgagro.gps.controller.locatedimage.controllers;

import com.ksgagro.gps.controller.locatedimage.json.LocatedImageJson;
import com.ksgagro.gps.controller.locatedimage.json.LocatedImageJsonMapper;
import com.ksgagro.gps.controller.locatedimage.json.LocatedImagePointJSON;
import com.ksgagro.gps.logic.image.service.LocatedImageService;
import javaxt.io.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by Maxim Kirichenko on 23.04.17.
 */
@Controller
public class LocatedImageController {

    @Autowired LocatedImageService imageService;
    @Autowired LocatedImageJsonMapper mapper;

    @RequestMapping(value = "/located/image/{fileKey}")
    public @ResponseBody  LocatedImageJson image(@PathVariable("fileKey") String fileKey){

        return mapper.toLocatedImageJson(imageService.image(fileKey));
    }

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

    @RequestMapping(value = "/located/image/coordinates", method = GET)
    public @ResponseBody List<LocatedImagePointJSON> coordinates(){
        return mapper.toLocatedImagePointJsons(imageService.list());
    }


}
