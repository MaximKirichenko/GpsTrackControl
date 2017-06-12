package com.ksgagro.gps.logic.image.service;

import com.ksgagro.gps.logic.image.service.model.LocatedImageBO;
import javaxt.io.Image;

import java.util.List;

/**
 * Created by Maxim Kirichenko on 02.05.17.
 */
public interface LocatedImageService {
    void save(Image image);

    List<LocatedImageBO> list();

    LocatedImageBO image(String fileKey);
}
