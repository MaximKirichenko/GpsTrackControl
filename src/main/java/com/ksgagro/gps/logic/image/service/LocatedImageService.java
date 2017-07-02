package com.ksgagro.gps.logic.image.service;

import com.ksgagro.gps.logic.image.service.model.LocatedImageBO;
import com.ksgagro.gps.logic.image.service.model.LocatedImageBytesBO;
import com.ksgagro.gps.logic.image.service.model.LocatedImageInfoBO;
import javaxt.io.Image;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Maxim Kirichenko on 02.05.17.
 */
public interface LocatedImageService {
    void save(LocatedImageBO image, InputStream inputStream) throws IOException;

    void saveToDatabase(LocatedImageBO image);

    void saveToDisk(LocatedImageBO image, InputStream inputStream) throws IOException;

    List<LocatedImageInfoBO> list();

    LocatedImageBO image(String fileKey);
}
