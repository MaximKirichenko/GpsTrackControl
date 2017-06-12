package com.ksgagro.gps.logic.image.persistence.impl;

import com.ksgagro.gps.logic.image.persistence.LocatedImageManager;
import com.ksgagro.gps.logic.image.persistence.model.LocatedImageEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
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
    public void save(LocatedImageEntity entity) {
        Session session = sessionFactory.getCurrentSession();
        session.save(entity);
    }

    @Override
    @Transactional
    public List<LocatedImageEntity> list() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(LocatedImageEntity.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<LocatedImageEntity>)criteria.list();
    }

    @Override
    @Transactional
    public LocatedImageEntity get(String fileKey) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(LocatedImageEntity.class)
                .add(Restrictions.eq("name", fileKey));
        return (LocatedImageEntity)criteria.uniqueResult();
    }

}
