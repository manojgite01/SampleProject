package com.smartapp.web.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * If you have two cachemanager in your application then extend from CachingConfigurerSupport.
 * This will ensure that cachemanager method defined in that class will be used by callback. Also give different bean name using @Bean.
 * @EnableCaching does not use bean name for wiring, instead by default it autowired by type. 
 * For spring security cachemanager is not required, as it directly using cache. 
 * As most of the application will be using single cachemanager hence not extending from CachingConfigurerSupport
 *
 */
@Configuration
@EnableCaching
public class EhCacheConfig {

	/**
	 * This bean is factoryBean
	 * This bean read ehcache configuration file and 
	 * return ehcachemanager when getObject method is called on this bean
	 *  
	 */
	@Bean
	public EhCacheManagerFactoryBean ehcacheManagerFactoryBean(){
		EhCacheManagerFactoryBean ehcacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		ehcacheManagerFactoryBean.setCacheManagerName("ehcacheManager");
		ehcacheManagerFactoryBean.setConfigLocation(new ClassPathResource("config/ehcache.xml"));
		return ehcacheManagerFactoryBean;
	}

	/**
	 * wrapper over ehcacheManager for spring cache aspect
	 * This cachemanager will be used by spring aspect, so that spring aspect are independent of cache implementation 
	 */
	@Bean
	public CacheManager cacheManager(){
		EhCacheCacheManager ehcacheManager = new EhCacheCacheManager();
		ehcacheManager.setCacheManager(ehcacheManagerFactoryBean().getObject());
		return ehcacheManager;
	}
}
