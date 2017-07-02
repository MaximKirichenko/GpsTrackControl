package com.ksgagro.gps.logic.image.persistence.impl;

import com.ksgagro.gps.logic.image.persistence.LocatedImageManager;
import com.ksgagro.gps.logic.image.persistence.model.LocatedImageEntity;
import com.ksgagro.gps.logic.image.persistence.model.LocatedImageInfoEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Maxim Kirichenko on 02.05.17.
 */
@Repository
public class LocatedImageManagerImpl implements LocatedImageManager{
    @Autowired private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void save(LocatedImageInfoEntity entity) {
        Session session = sessionFactory.getCurrentSession();
        session.save(entity);
    }

    @Override
    @Transactional
    public List<LocatedImageInfoEntity> list() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(LocatedImageInfoEntity.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<LocatedImageInfoEntity>)criteria.list();
    }

    @Override
    @Transactional
    public LocatedImageInfoEntity get(String fileKey) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(LocatedImageInfoEntity.class)
                .add(Restrictions.eq("name", fileKey));
        return (LocatedImageInfoEntity)criteria.uniqueResult();
    }

    @Override
    @Transactional
    public Long save(LocatedImageEntity imageEntity) {
        Session session = sessionFactory.getCurrentSession();
        return (Long)session.save(imageEntity);
    }

    @Override
    @Transactional
    public LocatedImageEntity getImage(Long imageId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(LocatedImageEntity.class)
                .add(Restrictions.eq("locatedImageId", imageId));
        return (LocatedImageEntity)criteria.uniqueResult();
    }

}
