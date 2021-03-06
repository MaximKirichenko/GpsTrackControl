package com.ksgagro.gps.controller.locatedimage.controllers;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.ksgagro.gps.controller.JSON.BasicJSON;
import com.ksgagro.gps.controller.JSON.FailJSON;
import com.ksgagro.gps.controller.JSON.SuccessJSON;
import com.ksgagro.gps.controller.locatedimage.json.LocatedImageJson;
import com.ksgagro.gps.controller.locatedimage.json.LocatedImageJsonMapper;
import com.ksgagro.gps.controller.locatedimage.json.LocatedImagePointJSON;
import com.ksgagro.gps.logic.image.service.LocatedImageService;
import com.ksgagro.gps.logic.image.service.mapper.LocatedImageBOMapper;
import com.ksgagro.gps.logic.image.service.model.LocatedImageBO;
import com.ksgagro.gps.logic.image.service.model.LocatedImageBytesBO;
import com.ksgagro.gps.logic.image.service.model.LocatedImageInfoBO;
import com.ksgagro.gps.utils.exception.BrokenGeoTagException;
import javaxt.io.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by Maxim Kirichenko on 23.04.17.
 */
@Controller
public class LocatedImageController {

    @Autowired LocatedImageService imageService;
    @Autowired LocatedImageJsonMapper mapper;
    @Autowired LocatedImageBOMapper locatedImageBOMapper;

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
    public @ResponseBody BasicJSON upload(@RequestParam MultipartFile image){
        try {
            LocatedImageBO bo = locatedImageBOMapper.toBO(image.getInputStream());
            imageService.save(bo);
            imageService.saveToDisk(bo, image.getInputStream());
        } catch (IOException | ImageProcessingException e) {
            return new FailJSON("Saving error. Please contact administrator");
        } catch (BrokenGeoTagException e) {
            return new FailJSON("No GeoTag in file. Please select image with GeoTag");
        }
        return new SuccessJSON("Saving success");
    }


    @RequestMapping(value = "/located/image/coordinates", method = GET)
    public @ResponseBody List<LocatedImagePointJSON> coordinates(){
        return mapper.toLocatedImagePointJsons(imageService.list());
    }


}
