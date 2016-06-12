package com.smartapp.web.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.smartapp.web.domain.User;

@Repository
public class UserDaoImpl implements UserDao {

	@PersistenceContext(unitName="emf")
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public User findByUserName(String username) {

		List<User> users = new ArrayList<User>();

		users = entityManager.createQuery("from User where username=?").setParameter(0, username).getResultList();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}

	}

}