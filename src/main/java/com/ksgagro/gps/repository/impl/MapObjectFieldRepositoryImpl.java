package com.ksgagro.gps.repository.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ksgagro.gps.domain.MapObjectField;
import com.ksgagro.gps.repository.MapObjectFieldRepository;

@Repository
public class MapObjectFieldRepositoryImpl implements MapObjectFieldRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public void addField(MapObjectField field) {
		Session session = sessionFactory.getCurrentSession();
		session.save(field);
	}

	@Override
	@Transactional
	public List<MapObjectField> getAll() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(MapObjectField.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		@SuppressWarnings("unchecked")
		List<MapObjectField> list = (List<MapObjectField>)criteria.list();
		return list;
	}

	@Override
	@Transactional
	public MapObjectField get(int id) {
		Session session = sessionFactory.getCurrentSession();
		return (MapObjectField)session.createCriteria(MapObjectField.class).add(Restrictions.eq("id", id)).uniqueResult();
	}

}
