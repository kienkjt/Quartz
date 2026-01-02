package com.vss.quartz.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "quartzXADataSource")
    @ConfigurationProperties(prefix = "app.datasource.quartz-xa")
    public AtomikosDataSourceBean quartzXADataSource() {
        return new AtomikosDataSourceBean();
    }

    @Bean(name = "quartzNonXADataSource")
    @ConfigurationProperties(prefix = "app.datasource.quartz-nonxa")
    public DataSource quartzNonXADataSource() {
        return new HikariDataSource();
    }

    @Bean(name = "db1DataSource")
    @ConfigurationProperties(prefix = "app.datasource.db1")
    public AtomikosDataSourceBean db1DataSource() {
        return new AtomikosDataSourceBean();
    }

    @Bean(name = "db2DataSource")
    @ConfigurationProperties(prefix = "app.datasource.db2")
    public AtomikosDataSourceBean db2DataSource() {
        return new AtomikosDataSourceBean();
    }
}