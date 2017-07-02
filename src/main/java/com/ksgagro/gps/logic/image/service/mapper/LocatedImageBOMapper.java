package com.ksgagro.gps.logic.image.service.mapper;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.ksgagro.gps.logic.common.model.Coordinate;
import com.ksgagro.gps.logic.image.persistence.model.LocatedImageEntity;
import com.ksgagro.gps.logic.image.persistence.model.LocatedImageInfoEntity;
import com.ksgagro.gps.logic.image.service.model.LocatedImageBO;
import com.ksgagro.gps.logic.image.service.model.LocatedImageBytesBO;
import com.ksgagro.gps.logic.image.service.model.LocatedImageInfoBO;
import com.ksgagro.gps.utils.MyImageUtils;
import com.ksgagro.gps.utils.RandomUtils;
import javaxt.io.Image;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
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

    public LocatedImageInfoBO toInfoBO(Image image, Long imageId){
        return new LocatedImageInfoBO(){{
            setCoordinate(toCoordinate(image.getGPSCoordinate()));
            setImageId(imageId);
            setName(generateName());
            setOwnerId(1L);
            setCreatorId(1L);
            setCreationTime(creationDate(image));
            setUpdatedTime(new Date());
        }};
    }

    public LocatedImageBytesBO toImageBO(Image image) {
        return new LocatedImageBytesBO(){{
            setImage(image.getByteArray());
        }};
    }

    public LocatedImageBO toBO(InputStream inputStream) throws IOException, ImageProcessingException {
        LocatedImageBO ret = new LocatedImageBO();
        Metadata metadata = ImageMetadataReader.readMetadata(inputStream);


        LocatedImageBytesBO imageBytes = new LocatedImageBytesBO();
        imageBytes.setImage(IOUtils.toByteArray(inputStream));
        ret.setLocatedImageBytes(imageBytes);

        LocatedImageInfoBO info = new LocatedImageInfoBO();
        info.setCoordinate(MyImageUtils.getCoordinates(metadata));
        info.setName(generateName());
        info.setCreationTime(new Date());
        info.setUpdatedTime(new Date());
        info.setOwnerId(1L);
        info.setCreatorId(1L);
        ret.setLocatedImageInfo(info);
        return ret;
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

    public List<LocatedImageInfoBO> toBOs(List<LocatedImageInfoEntity> list) {
        List<LocatedImageInfoBO> ret = new ArrayList<>();
        for(LocatedImageInfoEntity entity: list)
            ret.add(toInfoBO(entity));
        return ret;
    }

    public LocatedImageInfoBO toInfoBO(LocatedImageInfoEntity entity){
        return new LocatedImageInfoBO(){{
            setName(entity.getName());
            setImageId(entity.getImageId());
            setCoordinate(new Coordinate(){{
                setLatitude(entity.getLatitude());
                setLongitude(entity.getLongitude());
            }});
        }};

    }

    public LocatedImageBytesBO toImageBO(LocatedImageEntity image) {
        LocatedImageBytesBO ret = new LocatedImageBytesBO();
        ret.setLocatedImageId(image.getLocatedImageId());
        ret.setImage(image.getImage());
        return ret;
    }
}
