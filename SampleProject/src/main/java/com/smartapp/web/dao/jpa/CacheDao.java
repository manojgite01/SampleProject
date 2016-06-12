package com.smartapp.web.dao.jpa;

import java.util.List;

import com.smartapp.web.domain.Employee;


public interface CacheDao {
	
	public List<Employee> getEmployees();
}