package com.jwtAuthentication.jwtAuthentication.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.jwtAuthentication.jwtAuthentication.routing.TenantAwareRoutingSource;

@Configuration
public class DataSourceConfig {
	
	
	@Autowired
	Environment env;

	@Bean
	public DataSource customDataSource1() {
		System.out.println("Setting data source from first DB");
		AbstractRoutingDataSource dataSource = new TenantAwareRoutingSource();
		Map<Object, Object> targetDataSources = new HashMap<>();
		
		targetDataSources.put("marvel", marvel());
		targetDataSources.put("prmacro", prmacros());
		
		dataSource.setTargetDataSources(targetDataSources);
		
		dataSource.afterPropertiesSet();
		
	    return dataSource;
	}

private Object marvel() {
	System.out.println("Connected to tenant 1");
	DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty("marvel.datasource.driver-class-name"));
    dataSource.setUrl(env.getProperty("marvel.datasource.url"));
    dataSource.setUsername(env.getProperty("marvel.datasource.username"));
    dataSource.setPassword(env.getProperty("marvel.datasource.password"));
	return dataSource;
}

private Object prmacros() {
	System.out.println("Connected to tenant 2");
	DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty("prmacro.datasource.driver-class-name"));
    dataSource.setUrl(env.getProperty("prmacro.datasource.url"));
    dataSource.setUsername(env.getProperty("prmacro.datasource.username"));
    dataSource.setPassword(env.getProperty("prmacro.datasource.password"));
	return dataSource;
}
}
