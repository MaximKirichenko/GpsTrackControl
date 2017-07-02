package com.ksgagro.gps.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.ksgagro.gps.logic.common.model.Coordinate;
import com.ksgagro.gps.utils.exception.BrokenGeoTagException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by Maxim Kirichenko on 19.06.2017.
 */
public class MyImageUtils {

    private static final String LONGITUDE = "GPS Longitude";
    private static final String LATITUDE = "GPS Latitude";

    public static Coordinate getCoordinates(Metadata metadata) throws ImageProcessingException, IOException {
        try{
            String longitude = getTagValue(metadata, LONGITUDE);
            String latitude = getTagValue(metadata, LATITUDE);
            return new Coordinate(toCoordinate(latitude), toCoordinate(longitude));
        }catch (IllegalArgumentException e){
            throw new BrokenGeoTagException();
        }
    }

    private static double toCoordinate(String str) {
        String[] strings = str.split(" ");
        if(strings.length != 3) throw new IllegalArgumentException("Bad coordinates");
        int degree = new Integer(strings[0].substring(0, strings[0].length() - 1));
        double minute = replaceComma(strings[1].substring(0, strings[1].length() - 1));
        double seconds =  replaceComma(strings[2].substring(0, strings[2].length()-1));
        double fraction = (minute * 60 + seconds)/3600;
        return degree + fraction;
    }

    private static double replaceComma(String substring) {
        return new Double(substring.replace(",", "."));
    }

    private static String getTagValue(Metadata metadata, String tagName) throws ImageProcessingException, IOException {
        for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    if(tag.getTagName().equals(tagName))
                        return tag.getDescription();
                }
            }
            throw new IllegalArgumentException("No tagname: [" + tagName + "] in inputStream value");
    }

    public static Date getCreationTime(Metadata metadata) {

        return null;
    }
}
