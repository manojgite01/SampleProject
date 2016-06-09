package com.smartapp.web.service;

import java.util.List;
import java.util.Map;

import net.sf.ehcache.Element;

import com.smartapp.web.domain.Employee;

	
public interface VTCacheService {
		
	public boolean loadVTCache();
	public Element getCacheElement(String cacheKey);
	public Map<Integer, Employee> getEmployeeMap();
	public List<Employee> getEmployeeList();
}
