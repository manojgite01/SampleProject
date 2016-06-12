package com.smartapp.web.service;

import java.util.List;
import java.util.Map;

import net.sf.ehcache.Element;

import com.smartapp.web.domain.Employee;
import com.smartapp.web.domain.User;

	
public interface CacheService {
		
	public boolean loadVTCache();
	public Element getCacheElement(String cacheKey);
	public Map<Integer, Employee> getEmployeeMap();
	public List<Employee> getEmployeeList();
	public List<User> getUsersList();
	public boolean refreshCache();
}
