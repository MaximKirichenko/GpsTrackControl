package com.ksgagro.gps.repository.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ksgagro.gps.domain.User;
import com.ksgagro.gps.domain.UserAction;
import com.ksgagro.gps.repository.UserActionRepository;

@Repository
public class UserActionRepositoryImpl implements UserActionRepository{
	
	@Autowired 
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Transactional
	public void setAction(String userLogin, String action, Date date) {
		Session session = sessionFactory.getCurrentSession();
		String getUserQuery = "FROM User user WHERE user.login = :userLogin";
		Query query = session.createQuery(getUserQuery);
		query.setParameter("userLogin", userLogin);
		List<User> list = query.list();
		User user = list.get(0);
		session.save(new UserAction(user, action, date));
		System.out.println(user);
		
	}

}
