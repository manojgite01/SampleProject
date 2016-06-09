package com.smartapp.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import com.smartapp.aspect.IAFPerformanceLogForServiceDAOAspect;

/**
 * Include all the configuration needed for application in this file
 * Also properties file of Java project should be included in this file
 */
@Configuration
@PropertySource(value={"classpath:config/environment-${IafConfigSuffix}.properties",
					   "classpath:config/sample-${IafConfigSuffix}.properties"
					  })
@ComponentScan(basePackages={"com.smartapp.web.service",
							  "com.smartapp.web.jms",
							  "com.smartapp.web.async",
							  "com.smartapp.web.dao.file",
							  "com.smartapp.web.dao.memory",
							  "com.smartapp.web.dao.helper"
							})
@Import(value={PersistenceJpaConfig.class,
		       EhCacheConfig.class
		       })
@EnableAspectJAutoProxy
public class CommonBeansConfig{

	/**
	 * Aspect for performance logging
	 *
	 */
	@Bean
	public IAFPerformanceLogForServiceDAOAspect iafPerformanceLogAspect(){
		IAFPerformanceLogForServiceDAOAspect iafPerformanceLogForServiceAspect = new IAFPerformanceLogForServiceDAOAspect();
		return iafPerformanceLogForServiceAspect;

	}

	/**
	 * Aspect for logging parameter values, if RuntimeException is raised from service or dao layer
	 */
	/*@Bean
	public ParamInfoExceptionAspect paramInfoExceptionAspect(){
		ParamInfoExceptionAspect paramInfoExceptionAspect = new ParamInfoExceptionAspect();
		return paramInfoExceptionAspect;

	}*/
	
	/**
	 * Aspect for logging parameter values & exception stack trace, if RuntimeException is raised from service or dao layer
	 */
	@Bean
	public ExceptionLoggerAspect exceptionLoggerAspect(){
		ExceptionLoggerAspect exceptionLoggerAspect = new ExceptionLoggerAspect();
		return exceptionLoggerAspect;

	}
	
	
}