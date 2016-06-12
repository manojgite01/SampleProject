package com.smartapp.web.dao.jpa;

import java.util.List;

import com.smartapp.web.domain.Employee;
import com.smartapp.web.domain.User;


public interface CacheDao {
	
	public List<Employee> getEmployees();
	public List<User> getUsersList();
}