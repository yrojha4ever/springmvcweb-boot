package com.javastud.springmvcweb;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = { MultipartAutoConfiguration.class })
@SpringBootApplication
public class SpringmvcwebBootApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringmvcwebBootApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringmvcwebBootApplication.class, args);
	}

	@Bean
	public SessionFactory sessionFactory(EntityManagerFactory emf) {
		if (emf.unwrap(SessionFactory.class) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		return emf.unwrap(SessionFactory.class);
	}

	@Bean
	public HibernateTransactionManager transactionManager(SessionFactory sf) {
		return new HibernateTransactionManager(sf);
	}

	/**
	 * Due to bug: https://github.com/spring-projects/spring-boot/issues/2958 in
	 * tomcat 8
	 * 
	 * @EnableAutoConfiguration(exclude = { MultipartAutoConfiguration.class })
	 */
	@Bean
	public CommonsMultipartResolver commonsMultipartResolver() {
		CommonsMultipartResolver cmr = new CommonsMultipartResolver();
		cmr.setMaxUploadSize(20000000);
		return cmr;
	}

}
