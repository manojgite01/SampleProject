package com.smartapp.web.config;

import java.util.Properties;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration of JPA
 */

@Configuration
@ComponentScan(basePackages={"com.smartapp.web.dao.jpa"})
@EnableTransactionManagement
public class PersistenceJpaConfig {

	/**
	 * Persistence unit name should match with persistence unit name in persistence.xml file
	 */
	private static final String PERSISTENCE_UNIT_NAME ="jpa";
	private static final String MYSQL_DIALECT = "org.hibernate.dialect.MySQL5Dialect";

	/**
	 * Required for second level caching
	 */

	@Autowired
	private Environment env;

	/**
	 * Transaction Manager
	 */
	@Bean
	public JpaTransactionManager transactionManager(){
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf().getObject());
		return transactionManager;
	}

	/**
	 * DataSource which is used in application
	 */
	@Bean
	public DataSource tomcatDataSource(){
		DataSource vtDataSource = new DataSource();
		String driverClassName = env.getProperty("driverClassName");
		String databaseURL = env.getProperty("databaseURL");
		String databaseUserId = env.getProperty("databaseUserId");

		String encryptionPassword = env.getProperty("databasePassword");
  		if(encryptionPassword != null){
			vtDataSource.setPassword(encryptionPassword);
  		} else {
  			throw new RuntimeException("In PersistenceJpaConfig for dsfjDataSource encryptionPassword or  encryptionPassword is null");
  		}
		vtDataSource.setDriverClassName(driverClassName);
		vtDataSource.setUrl(databaseURL);
		vtDataSource.setUsername(databaseUserId);
		vtDataSource.setTestWhileIdle(false);
		vtDataSource.setTestOnBorrow(true);
		vtDataSource.setValidationQuery("SELECT 1");
		vtDataSource.setTestOnReturn(false);
		vtDataSource.setValidationInterval(30000);
		vtDataSource.setTimeBetweenEvictionRunsMillis(30000);
		vtDataSource.setMaxActive(40);
		vtDataSource.setMaxIdle(40);
		vtDataSource.setInitialSize(10);
		vtDataSource.setMaxWait(10000);
		vtDataSource.setRemoveAbandonedTimeout(300);
		vtDataSource.setMinEvictableIdleTimeMillis(30000);
		vtDataSource.setMinIdle(10);
		vtDataSource.setLogAbandoned(true);
		vtDataSource.setRemoveAbandoned(true);

		return vtDataSource;
	}
	

	/**
	 * Post processor for injecting EntityManager in DAO
	 * Proxy is injected in DAO which gets entityManager from threadLocal if it present or
	 * new entityManager is created and attached to threadlocal
	 */
	@Bean
	static public PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor(){
		PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor = new PersistenceAnnotationBeanPostProcessor();
		persistenceAnnotationBeanPostProcessor.setDefaultPersistenceUnitName("jpa");
		return persistenceAnnotationBeanPostProcessor;
	}

	/**
	 * Bean post processor for attaching exception translator to spring beans annotated with repository
	 * Exception translator converts jpa exceptions to spring exception hierarcy
	 */
	@Bean
	static public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor(){
		PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor = new PersistenceExceptionTranslationPostProcessor();
		return persistenceExceptionTranslationPostProcessor;
	}

	/**
	 * JPA vendor adapter as per database. This is for DB2
	 */
	@Bean
	public HibernateJpaVendorAdapter hibernateJpaVendorAdapter(){
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setDatabasePlatform(MYSQL_DIALECT);
		hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setGenerateDdl(false);
		return hibernateJpaVendorAdapter;
	}

	/**
	 * EntityManager FactoryBean
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean emf(){
		Properties jpaProperties = new Properties();
		jpaProperties.put(org.hibernate.cfg.Environment.DEFAULT_SCHEMA, env.getProperty("schema"));

		LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localContainerEntityManagerFactoryBean.setDataSource(tomcatDataSource());
		localContainerEntityManagerFactoryBean.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter());
		localContainerEntityManagerFactoryBean.setJpaProperties(jpaProperties);
		localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		return localContainerEntityManagerFactoryBean;
	}
}