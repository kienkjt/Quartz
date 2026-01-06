package com.vss.quartz.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    // ===============================
    // QUARTZ XA DATASOURCE
    // ===============================
    @Bean(name = "quartzXADataSource")
    @ConfigurationProperties(prefix = "app.datasource.quartz-xa")
    public AtomikosDataSourceBean quartzXADataSource() {
        return new AtomikosDataSourceBean();
    }

    // ===============================
    // QUARTZ NON-XA DATASOURCE
    // ===============================
    @Bean(name = "quartzNonXADataSource")
    @ConfigurationProperties(prefix = "app.datasource.quartz-nonxa")
    public DataSource quartzNonXADataSource() {
        return new HikariDataSource();
    }

    // ===============================
    // BUSINESS DB1 XA
    // ===============================
    @Bean(name = "db1DataSource")
    @ConfigurationProperties(prefix = "app.datasource.db1")
    public AtomikosDataSourceBean db1DataSource() {
        return new AtomikosDataSourceBean();
    }

    // ===============================
    // BUSINESS DB2 XA
    // ===============================
    @Bean(name = "db2DataSource")
    @ConfigurationProperties(prefix = "app.datasource.db2")
    public AtomikosDataSourceBean db2DataSource() {
        return new AtomikosDataSourceBean();
    }

    // ===============================
    // JDBC TEMPLATE
    // ===============================
    @Bean(name = "jdbcTemplateDB1")
    public JdbcTemplate jdbcTemplateDB1(
            @Qualifier("db1DataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "jdbcTemplateDB2")
    public JdbcTemplate jdbcTemplateDB2(
            @Qualifier("db2DataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }
}
