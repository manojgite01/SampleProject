package com.smartapp.web.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.smartapp.web.domain.Employee;
import com.smartapp.web.domain.User;


@Repository("cacheDao")
@SuppressWarnings("unchecked")
public class CacheDaoImpl implements CacheDao{

	@PersistenceContext(unitName="emf")
	private EntityManager entityManager;
	
	public List<Employee> getEmployees() {
		Query query =  entityManager.createQuery("from Employee p");
		return (List<Employee>)query.getResultList(); 
	}
	public List<User> getUsersList() {
		Query query =  entityManager.createQuery("from User p");
		return (List<User>)query.getResultList(); 
	}
}