package com.smartapp.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;


/**
 * This class  will be used by ContextLoaderListener to configure beans in spring webapplication context
 * SpringWebsecurityConfig and CommonBeansConfig be imported in this webapplication context
 *
 *
 */

@Configuration
@PropertySource({"classpath:config/common-placeholder.properties" })
@Import(value={CommonBeansConfig.class})
@EnableAspectJAutoProxy
public class WebApplicationContextConfig {

	// Change this. This is only for reference purpose. This shows how to define session scope bean
	/**
	 *  Defined Session scope bean here
	 *
	 */

	/*
	@Bean
	@Scope(value="session",proxyMode=ScopedProxyMode.TARGET_CLASS)
	public CustomerContainerSession customerContainerSessionBean(){
		CustomerContainerSession customerContainerSessionBean = new CustomerContainerSession();
		return customerContainerSessionBean;
	}
	*/

}

