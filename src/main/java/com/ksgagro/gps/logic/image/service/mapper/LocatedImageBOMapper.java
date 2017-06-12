package com.ksgagro.gps.logic.image.service.mapper;

import com.ksgagro.gps.logic.common.model.Coordinate;
import com.ksgagro.gps.logic.image.persistence.model.LocatedImageEntity;
import com.ksgagro.gps.logic.image.service.model.LocatedImageBO;
import com.ksgagro.gps.utils.RandomUtils;
import javaxt.io.Image;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Maxim Kirichenko on 02.05.17.
 */
@Component
public class LocatedImageBOMapper {

    public LocatedImageBO toBO(Image image){
        return new LocatedImageBO(){{
            setCoordinate(toCoordinate(image.getGPSCoordinate()));
            setData(image.getByteArray());
            setName(generateName());
            setOwnerId(1L);
            setCreatorId(1L);
            setCreationTime(creationDate(image));
            setUpdatedTime(new Date());
        }};
    }

    private Date creationDate(Image image) {
        String dateStr = image.getExifTags().get(36868).toString().trim();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
        LocalDateTime localDate = LocalDateTime.parse(dateStr, dateTimeFormatter);

        return Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
    }

    private String generateName() {
        return RandomUtils.fileKey();
    }

    private Coordinate toCoordinate(double[] gpsCoordinate) {
        return new Coordinate() {{
            setLatitude(gpsCoordinate[0]);
            setLongitude(gpsCoordinate[1]);
        }};
    }

    public List<LocatedImageBO> toBOs(List<LocatedImageEntity> list) {
        List<LocatedImageBO> ret = new ArrayList<>();
        for(LocatedImageEntity entity: list)
            ret.add(toBO(entity));
        return ret;
    }

    public LocatedImageBO toBO(LocatedImageEntity entity){
        return new LocatedImageBO(){{
            setName(entity.getName());
            setData(entity.getData());
            setCoordinate(new Coordinate(){{
                setLatitude(entity.getLatitude());
                setLongitude(entity.getLongitude());
            }});
        }};

    }
}
