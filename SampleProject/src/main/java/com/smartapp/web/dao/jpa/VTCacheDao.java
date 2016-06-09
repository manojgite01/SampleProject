package com.smartapp.web.dao.jpa;

import java.util.List;

import com.smartapp.web.domain.Employee;


public interface VTCacheDao {
	
	public List<Employee> getEmployees();
}