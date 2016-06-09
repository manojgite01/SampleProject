package com.smartapp.web.utility;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class EhCacheAdminController {
	
	private static final Logger logger = LoggerFactory.getLogger(EhCacheAdminController.class);
	
	private List<String> cacheManagerNames = null;
	
	@RequestMapping(value = {"/EhCacheClient"}, method=RequestMethod.GET)
	public String displayEhCacheClient(Model model){
		logger.debug("Inside displayEhCacheClient method");
		EhCacheForm ehCacheForm = new EhCacheForm();
		if(cacheManagerNames == null) {
			List<CacheManager> cacheManagerList = CacheManager.ALL_CACHE_MANAGERS;
			cacheManagerNames = new ArrayList<String>();
			if(cacheManagerList != null){
				cacheManagerNames.add(" -- Select Cache Manager -- ");
		        for(CacheManager manager : cacheManagerList){
		        	String cacheManagerName = manager.getName();
		        	cacheManagerNames.add(cacheManagerName);
		        }
		        
			}
		}
		
		List<String> ehCacheList = new ArrayList<String>();
		ehCacheList.add("-- Select Cache --");
        ehCacheForm.setEhcacheManagerList(cacheManagerNames);
        ehCacheForm.setEhcacheList(ehCacheList);
		model.addAttribute("ehCacheForm", ehCacheForm);
		return "EhCacheClient";
		
	}
	
	@RequestMapping(value = {"/GetCachesForCacheManager"},  params="cacheManagerName", method=RequestMethod.GET)
	public @ResponseBody String getAllCacheForCacheManager(@RequestParam(value="cacheManagerName", required=true) String cacheManagerName, HttpServletResponse response){
		logger.debug("Inside getAllCacheForCacheManager method");
		List<CacheManager> cacheManagerList = CacheManager.ALL_CACHE_MANAGERS;
		List<String> cacheList	= new ArrayList<String>();
		cacheList.add(" -- Select Cache --");
        for(CacheManager manager : cacheManagerList){
        	String managerName = manager.getName();
        	if(managerName.equals(cacheManagerName)){
        		String[] caches = manager.getCacheNames();
        		cacheList = Arrays.asList(caches);
            	break;
        	}
        }
        String cacheString = convertCacheListToJsonFormat(cacheList);
        return cacheString;
	}

	
	@RequestMapping(value = {"/EhCacheClient"},params="userAction=search" , method=RequestMethod.POST)
    public String displayCacheElements(@ModelAttribute("ehCacheForm") EhCacheForm ehCacheForm,BindingResult bindingResult, Model model){
		logger.debug("Inside displayCacheElements method");
		String cacheManagerName = ehCacheForm.getCacheManagerName();
		String cacheName = ehCacheForm.getCacheName();
		List<CacheManager> cacheManagerList = CacheManager.ALL_CACHE_MANAGERS;
		List<String> cacheList	= new ArrayList<String>();
		List<EhCacheForm.CacheElement> ehcacheElementList = new ArrayList<EhCacheForm.CacheElement>();
		if(cacheManagerNames == null) {
			cacheManagerNames = new ArrayList<String>();
	        for(CacheManager manager : cacheManagerList){
	        	String managerName = manager.getName();
	        	cacheManagerNames.add(managerName);
	        }
		}
		if(cacheManagerList != null){
			for(CacheManager manager : cacheManagerList){
	        	String managerName = manager.getName();
	        	if(managerName.equals(cacheManagerName)){
	        		String[] caches = manager.getCacheNames();
	        		cacheList = Arrays.asList(caches);
	        		Cache cache = manager.getCache(cacheName);
	        		for (Object key : cache.getKeys()) {
	    				Element element = cache.get(key);
	    				ehcacheElementList.add(new EhCacheForm.CacheElement(key, element.getObjectValue()));
	    			}
	        		break;
	        	}
			}
		}
        if (ehcacheElementList == null || ehcacheElementList.isEmpty()){  
        	bindingResult.reject("EhCache.EmptyCache");
        }

		  ehCacheForm.setEhcacheManagerList(cacheManagerNames);
		  ehCacheForm.setEhcacheList(cacheList);
		  ehCacheForm.setEhcacheElementList(ehcacheElementList);
		  model.addAttribute("ehCacheForm", ehCacheForm);
		return "EhCacheClient";
	}

	@RequestMapping(value = {"/EhCacheClient"},params="userAction=clearCache" , method=RequestMethod.POST)
    public String clearCache(@ModelAttribute("ehCacheForm") EhCacheForm ehCacheForm,BindingResult bindingResult, Model model){
		logger.debug("Inside clearCache method");
		String cacheManagerName = ehCacheForm.getCacheManagerName();
		String cacheName = ehCacheForm.getCacheName();
		List<CacheManager> cacheManagerList = CacheManager.ALL_CACHE_MANAGERS;
		List<String> cacheList	= new ArrayList<String>();
		List<EhCacheForm.CacheElement> ehcacheElementList = new ArrayList<EhCacheForm.CacheElement>();
		if(cacheManagerNames == null) {
	        for(CacheManager manager : cacheManagerList){
	        	String managerName = manager.getName();
	        	cacheManagerNames.add(managerName);
	        }
		}
		if(cacheManagerList != null){
	        for(CacheManager manager : cacheManagerList){
		        	String managerName = manager.getName();
		        	if(managerName.equals(cacheManagerName)){
		        		String[] caches = manager.getCacheNames();
		        		cacheList = Arrays.asList(caches);
		        		Cache cache = manager.getCache(cacheName);
		        		cache.removeAll();	
		        		break;
		        	}
			  }
		}
		  ehCacheForm.setEhcacheManagerList(cacheManagerNames);
		  ehCacheForm.setEhcacheList(cacheList);
		  ehCacheForm.setEhcacheElementList(ehcacheElementList);
		  model.addAttribute("ehCacheForm", ehCacheForm);
		return "EhCacheClient";
	}

	
    private String convertCacheListToJsonFormat(List<String> cacheList){
    	logger.debug("Inside convertCacheListToJsonFormat method");
    	String jsonformatCacheList = "";
    	ObjectMapper mapper = new ObjectMapper();
    	StringWriter strWriter = new StringWriter();
  	  	try{
  	  		if(cacheList != null){
  	  			mapper.writeValue(strWriter, cacheList);
  	  		}
  	  	}catch(Exception ex){
  		  logger.warn("Error in getting response body from jackson", ex);
  	  	}
  	  	jsonformatCacheList = strWriter.toString();
        return jsonformatCacheList;
      }
}
