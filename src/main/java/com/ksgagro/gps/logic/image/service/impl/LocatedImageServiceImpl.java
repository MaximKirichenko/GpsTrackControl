package com.ksgagro.gps.logic.image.service.impl;

import com.ksgagro.gps.logic.image.persistence.LocatedImageManager;
import com.ksgagro.gps.logic.image.persistence.mapper.LocatedImageEntityMapper;
import com.ksgagro.gps.logic.image.persistence.model.LocatedImageEntity;
import com.ksgagro.gps.logic.image.persistence.model.LocatedImageInfoEntity;
import com.ksgagro.gps.logic.image.service.LocatedImageService;
import com.ksgagro.gps.logic.image.service.mapper.LocatedImageBOMapper;
import com.ksgagro.gps.logic.image.service.model.LocatedImageBO;
import com.ksgagro.gps.logic.image.service.model.LocatedImageBytesBO;
import com.ksgagro.gps.logic.image.service.model.LocatedImageInfoBO;
import javaxt.io.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Maxim Kirichenko on 02.05.17.
 */
@Service
public class LocatedImageServiceImpl implements LocatedImageService {

    @Autowired
    LocatedImageBOMapper boMapper;
    @Autowired
    LocatedImageManager manager;
    @Autowired
    LocatedImageEntityMapper entityMapper;

    @Override
    public void save(Image image) {
        LocatedImageEntity imageEntity = entityMapper.toImage(boMapper.toImageBO(image));
        Long imageId = manager.save(imageEntity);

        LocatedImageInfoEntity infoEntity = entityMapper.toEntity(boMapper.toInfoBO(image, imageId));
        manager.save(infoEntity);
    }

    @Override
    public void save(LocatedImageBO image) {
        LocatedImageEntity imageEntity = entityMapper.toImage(image.getLocatedImageBytes());
        Long imageId = manager.save(imageEntity);

        LocatedImageInfoEntity infoEntity = entityMapper.toEntity(image.getLocatedImageInfo());
        infoEntity.setImageId(imageId);
        manager.save(infoEntity);

    }

    @Override
    public void saveToDisk(LocatedImageBO image, InputStream inputStream) throws IOException {
        LocatedImageInfoBO info = image.getLocatedImageInfo();
        String sRootPath = new File("").getAbsolutePath();
        String fileName = "c:/located_image/" + info.getName() + ".jpg";
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        ImageIO.write(bufferedImage, "jpg", new File(fileName));
    }

    @Override
    public List<LocatedImageInfoBO> list() {
        return boMapper.toBOs(manager.list());
    }

    @Override
    public LocatedImageBO image(String fileKey) {
        LocatedImageBO ret = new LocatedImageBO();
        ret.setLocatedImageInfo(boMapper.toInfoBO(manager.get(fileKey)));
        ret.setLocatedImageBytes(boMapper.toImageBO(manager.getImage(ret.getLocatedImageInfo().getImageId())));
        return ret;
    }


}
