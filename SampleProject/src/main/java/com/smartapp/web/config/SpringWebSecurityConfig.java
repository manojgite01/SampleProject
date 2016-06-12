package com.smartapp.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.cache.EhCacheBasedUserCache;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;


/**
 * This class Configures Beans required for SpringSecurity
 * Important points to consider: This class should extend WebSecurityConfigurerAdapter and annotated by @EnableWebSecurity
 * To enable method level security  EnableGlobalMethodSecurity annotation is required
 *
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
public class SpringWebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	/**
	 * SECURITY_CACHE_NAME should match with cacheName defined in spring-security-ehaceh.xml
	 */
	private static final String SECURITY_CACHE_NAME = "UserCache";
	private static final String SECURITY_CACHE_MANAGER_NAME = "springSecurityEhcacheManager";
	private static final String SECURITY_CACHE_CONFIG_FILE = "config/spring-security-ehcache.xml";
	

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/admin/**")
			.access("hasRole('ROLE_ADMIN')").and().formLogin()
			.loginPage("/login").failureUrl("/login?error")
				.usernameParameter("username")
				.passwordParameter("password")
				.and().logout().logoutSuccessUrl("/login?logout")
				.and().csrf()
				.and().exceptionHandling().accessDeniedPage("/403");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

	/**
	 * This bean is factoryBean
	 * This bean read ehcache configuration file and
	 * return ehcachemanager when getObject method is called on this bean
	 */
	@Bean
	public EhCacheManagerFactoryBean securityCacheManagerFactorBean(){
		EhCacheManagerFactoryBean ehcacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		ehcacheManagerFactoryBean.setCacheManagerName(SECURITY_CACHE_MANAGER_NAME);
		ehcacheManagerFactoryBean.setConfigLocation(new ClassPathResource(SECURITY_CACHE_CONFIG_FILE));
		return ehcacheManagerFactoryBean;
	}

	/**
	 * This is factoryBean which creates named Ehcahced instance when getObject method is called
	 */
	@Bean
	public EhCacheFactoryBean securityCacheFactoryBean(){
		EhCacheFactoryBean ehcacheFactoryBean = new EhCacheFactoryBean();
		ehcacheFactoryBean.setCacheManager(securityCacheManagerFactorBean().getObject());
		ehcacheFactoryBean.setCacheName(SECURITY_CACHE_NAME);
		return ehcacheFactoryBean;
	}

	/**
	 * This bean is wrapper over ehcache cache
	 *
	 */
	@Bean
	public UserCache securityUserCache(){
		EhCacheBasedUserCache userCache = new EhCacheBasedUserCache();
		userCache.setCache(securityCacheFactoryBean().getObject());
		return userCache;
	}


    /**
     * registers authentication manager as spring bean which is required for method level security
     */
    @Bean(name="authenticationManager")
    public AuthenticationManager authenticationManagerBean() throws Exception{
    	return super.authenticationManagerBean();
    }


    /**
     * This preauthentication filter uses authentication manager to get user details with authorities
     * from rbac component, creates PrauthenticationToken with userdetail and stores it in  securityContext
     */
    @Bean
	public RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter() throws Exception {
    	RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter =  new RequestHeaderAuthenticationFilter();
    	requestHeaderAuthenticationFilter.setPrincipalRequestHeader("SM_USER");
    	requestHeaderAuthenticationFilter.setAuthenticationManager(authenticationManager());
    	return requestHeaderAuthenticationFilter;
    }


}