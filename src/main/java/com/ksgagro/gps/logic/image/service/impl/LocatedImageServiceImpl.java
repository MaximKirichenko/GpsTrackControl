package com.ksgagro.gps.logic.image.service.impl;

import com.ksgagro.gps.logic.image.persistence.LocatedImageManager;
import com.ksgagro.gps.logic.image.persistence.mapper.LocatedImageEntityMapper;
import com.ksgagro.gps.logic.image.persistence.model.LocatedImageEntity;
import com.ksgagro.gps.logic.image.service.LocatedImageService;
import com.ksgagro.gps.logic.image.service.mapper.LocatedImageBOMapper;
import com.ksgagro.gps.logic.image.service.model.LocatedImageBO;
import javaxt.io.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        LocatedImageEntity entity = entityMapper.toEntity(boMapper.toBO(image));
        manager.save(entity);
    }

    @Override
    public List<LocatedImageBO> list() {
        return boMapper.toBOs(manager.list());
    }

    @Override
    public LocatedImageBO image(String fileKey) {
        return boMapper.toBO(manager.get(fileKey));
    }


}
