package com.smartapp.web.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.smartapp.web.domain.Employee;


@Repository("cacheDao")
@SuppressWarnings("unchecked")
public class CacheDaoImpl implements VTCacheDao{

	@PersistenceContext(unitName="emf")
	private EntityManager entityManager;
	
	public List<Employee> getEmployees() {
		Query query =  entityManager.createQuery("from Employee p");
		return (List<Employee>)query.getResultList(); 
	}
}