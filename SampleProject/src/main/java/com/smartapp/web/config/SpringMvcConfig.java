package com.smartapp.web.config;


import java.util.Locale;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.smartapp.aspect.PerformanceLogForControllerAspect;
import com.smartapp.web.exceptionresolver.SmartAppExceptionResolver;
import com.smartapp.web.interceptor.HttpSessionInterceptor;
import com.smartapp.web.utility.EhCacheAdminController;


/**
 * This class Configures Beans required for SpringMVC
 * Important points to consider: This class should extend WebMvcConfigurerAdapter and annotated by EnableWebMvc
 * Add required  basepackages in ComponentScan as per application need
 * To enable AspectJ in springmvc for performance logging EnableAspectJAutoProxy is required
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages =	{	"com.smartapp.web.controller",
									"com.smartapp.web.interceptor"
								})
@EnableAspectJAutoProxy
@EnableTransactionManagement
@Import({ SpringWebSecurityConfig.class })
public class SpringMvcConfig extends WebMvcConfigurerAdapter {


	/**
	 * Required for JPA, OpenEntityManagerInViewInterceptor, for lazy loading of data from domain object
	 */
	@Autowired
	@Qualifier(value="emf")
	EntityManagerFactory emf;


	/**
	 * For getting User Information from UUC component and storing it in session.
	 */
	@Autowired
	@Qualifier(value="httpSessionInterceptor")
	private HttpSessionInterceptor httpSessionInterceptor;

	/**
	 * ViewResolver for converting view name returned from controller to actual jsp name with path
	 *
	 */
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver(){

		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setViewClass(JstlView.class);
		internalResourceViewResolver.setPrefix("/WEB-INF/view/");
		internalResourceViewResolver.setSuffix(".jsp");
		internalResourceViewResolver.setOrder(0);
		return internalResourceViewResolver;
	}

	/**
	 * Required for FileUpload
	 *
	 */
	@Bean
	public CommonsMultipartResolver multipartResolver(){
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		// size in bytes
		multipartResolver.setMaxUploadSize(5000000);
		return multipartResolver;
	}

	/**
	 * Required for i18N internationalization.
	 * Each jsp should have corresponding properties for internationalization and should be included in this bean
	 */
	@Bean
	public ReloadableResourceBundleMessageSource messageSource(){
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		String[] baseNames = { 	"/WEB-INF/i18n/global",
							 	"/WEB-INF/i18n/sample",
							 	"/WEB-INF/i18n/thresultcontainer",
							 	"/WEB-INF/i18n/ehcacheclient"
							 };
		messageSource.setBasenames(baseNames);
		return messageSource;
	}

	/**
	 * Cookie LocalResolver for determining locale
	 * Default is English.
	 */
	@Bean
	public CookieLocaleResolver localeResolver(){
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setDefaultLocale(Locale.ENGLISH);
		return localeResolver;
	}


	/**
	 * Aspect performance logging of controller
	 *
	 */
	@Bean
	public PerformanceLogForControllerAspect iafPerformanceLogForControllerAspect(){
		PerformanceLogForControllerAspect iafPerformanceLogForControllerAspect = new PerformanceLogForControllerAspect();
		return iafPerformanceLogForControllerAspect;
	}

	// can be removed if you are going to use IAFPerformanceLogForControllerAspect
	/*
	@Bean
	public JamonPerformanceLogForControllerAspect jamonPerformanceLogForControllerAspect(){
		JamonPerformanceLogForControllerAspect jamonPerformanceLogForControllerAspect = new JamonPerformanceLogForControllerAspect();
		return jamonPerformanceLogForControllerAspect;
	}
	*/

	/**
	 * For mapping directly to view name. No need to explicitly write controller and do request mapping.
	 */
	public void addViewControllers(ViewControllerRegistry registry){
		registry.addViewController("/").setViewName("index");

	}

	/**
	 * For serving static resources like css, images directly from resourcelocation using defaultServlet
	 * This resourceHandler is useful if dispatcherServlet is mapped to /
	 * In our case dispatcher servlet is mapping to /web hence this resourcehandler will not play role
	 * for displaying static contents
	 */
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String[] pathPatterns = {"/components/**", "/images/**", "/scripts/**", "/styles/**"};
		String[] resourceLocations = {"/components/", "/images/", "/scripts/, /styles/"};
		registry.addResourceHandler(pathPatterns).addResourceLocations(resourceLocations);
	}


	/**
	 * default servlet for handling static resources
	 */
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/**
	 * Exception resolver for displaying view based on exception and also for logging exception
	 *
	 */
	@Bean
	public SmartAppExceptionResolver dsfjExceptionResolver(){
		SmartAppExceptionResolver smartAppExceptionResolver = new SmartAppExceptionResolver();
		smartAppExceptionResolver.setDefaultErrorView("ErrorPage");
		Properties exceptionMappingProperties = new Properties();
		// change this, if you are going to use spring security method level security
		//exceptionMappingProperties.put("org.springframework.security.access.AccessDeniedException", "AccessDeniedPage");
		exceptionMappingProperties.put("java.lang.RuntimeException", "ErrorPage");
		smartAppExceptionResolver.setExceptionMappings(exceptionMappingProperties);
		return smartAppExceptionResolver;
	}


	/**
	 * Interceptor that allows for changing the current locale on every request. This may not be required for many application
	 *
	 */
	public LocaleChangeInterceptor localeChangeInterceptor(){
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	/**
	 *	Required for JPA, OpenEntityManagerInViewInterceptor, for lazy loading of data from domain object
	 *
	*/
	public OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor(){
    	OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor = new OpenEntityManagerInViewInterceptor();
    	openEntityManagerInViewInterceptor.setEntityManagerFactory(emf);
    	return openEntityManagerInViewInterceptor;

    }

	/**
	 * adds interceptors into InterceptorRegistry
	 */
    public void addInterceptors(InterceptorRegistry registry){
		registry.addWebRequestInterceptor(openEntityManagerInViewInterceptor());
		registry.addInterceptor(localeChangeInterceptor());
		// change this to add uucInterceptor
		registry.addInterceptor(httpSessionInterceptor);
    }

    // for ehacheclient
	@Bean
	public EhCacheAdminController EhCacheAdminController(){
		EhCacheAdminController ehcaheAdminController = new EhCacheAdminController();
		return ehcaheAdminController;
	}

}
