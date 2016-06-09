package com.smartapp.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.smartapp.web.dao.jpa.VTCacheDao;
import com.smartapp.web.domain.Employee;

@Service(value="cacheService")
@SuppressWarnings({"unchecked", "deprecation"})
public class CacheServiceImpl implements VTCacheService{
	
	@Autowired
	@Resource(name="cacheDao")
	private VTCacheDao cacheDao;
	 
	@Autowired
	@Qualifier(value = "cacheManager")
	CacheManager manager;

	@PostConstruct
	public boolean loadVTCache(){
		
		System.out.println("loadVTCache() : IN");
		final Cache cache = manager.getCache("appCache");
	    if (cache != null) {
	    	synchronized (cache){	    		
	    		cache.put("employees", cacheDao.getEmployees());
	    	}
	     } 
	    System.out.println("loadVTCache() : EXIT");
  		return true;
	} 
	
	
	public Element getCacheElement(String cacheKey) {
		Cache cache = manager.getCache("appCache");
		Object nativeCache = cache.getNativeCache();
		Ehcache ehCache = (Ehcache) nativeCache;
		final Element element = (Element) ehCache.get(cacheKey);
		return element;
	}

	public Map<Integer, Employee> getEmployeeMap() {
		Map<Integer, Employee> map = new HashMap<Integer, Employee>();

		List<Employee> list = (List<Employee>) getCacheElement("employees").getValue(); 
		
		for (Employee employee : list) {
			map.put(employee.getId(), employee);
		}
		return map;
	}

	public List<Employee> getEmployeeList() {
		List<Employee> list = (List<Employee>) getCacheElement("employees").getValue(); 
		//Collections.sort(list, Employee.ProcessNameComparator);
		return list;
	}
	
}