package com.ksgagro.gps.tracks.menu.utils;

import org.springframework.stereotype.Service;

/**
 * Created by kma on 29.09.2016.
 */
@Service
public class DateUtils {
    public static enum TimeMeasure{SECONDS, MINUTES, HOURS, DAYS, MONTHS, YEARS};
    public Long getLongPeriod(int value, TimeMeasure measure){
       switch (measure){
           case SECONDS: return value*1000L;
           case MINUTES: return value*1000L*60;
           case HOURS: return value*1000L*60*60;
           case DAYS: return value * 1000L * 60 * 60 * 24;
           case MONTHS: return value * 1000L * 60 * 60 * 24 * 31;
           case YEARS: return value * 1000L * 60 * 60 * 24 * 31 * 361;
       }
        throw new IllegalArgumentException("Cannot convert period");
    }

    public static void main(String[] args) {
        DateUtils dateUtils = new DateUtils();
        System.out.println(dateUtils.getLongPeriod(5, TimeMeasure.YEARS));
    }
}
