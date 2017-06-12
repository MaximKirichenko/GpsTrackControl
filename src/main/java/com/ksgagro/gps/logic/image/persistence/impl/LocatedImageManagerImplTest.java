package com.ksgagro.gps.logic.image.persistence.impl;

import com.ksgagro.gps.logic.image.persistence.model.LocatedImageEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Maxim Kirichenko on 02.05.17.
 */
public class LocatedImageManagerImplTest {
    @Autowired private SessionFactory sessionFactory;


    @Test
    public void save() throws Exception {
        Session session = sessionFactory.getCurrentSession();
        LocatedImageEntity image = new LocatedImageEntity(){{
            setData(new byte[]{0, 1, 2, 8});
            setName("test");
            setLongitude(15.12312);
            setLatitude(65.1651);
            setCreationTime(new Date());
            setUpdatedTime(new Date());
            setOwnerId(1l);
            setCreatorId(1L);
        }};
        session.save(image);
    }

}