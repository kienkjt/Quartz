package com.vss.quartz.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DataSourceConfig {

    // QUARTZ XA DATASOURCE
    @Bean(name = "quartzXADataSource")
    @ConfigurationProperties(prefix = "app.datasource.quartz-xa")
    public AtomikosDataSourceBean quartzXADataSource() {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setXaDataSourceClassName("com.mysql.cj.jdbc.MysqlXADataSource");
        ds.setUniqueResourceName("quartzXA");
        ds.setMinPoolSize(5);
        ds.setMaxPoolSize(20);
        ds.setBorrowConnectionTimeout(30);

        Properties props = new Properties();
        props.setProperty("url", "jdbc:mysql://localhost:3306/quartz?useSSL=false&serverTimezone=UTC");
        props.setProperty("user", "root");
        props.setProperty("password", "123456");
        ds.setXaProperties(props);

        return ds;
    }


    // QUARTZ NON-XA DATASOURCE (for JobStoreCMT)

    @Bean(name = "quartzNonXADataSource")
    @ConfigurationProperties(prefix = "app.datasource.quartz-nonxa.hikari")
    public DataSource quartzNonXADataSource() {
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/quartz?useSSL=false&serverTimezone=UTC")
                .username("root")
                .password("123456")
                .type(HikariDataSource.class)
                .build();
    }


    // BUSINESS DB1 - XA DATASOURCE
    @Bean(name = "db1DataSource")
    @Primary
    @ConfigurationProperties(prefix = "app.datasource.db1")
    public AtomikosDataSourceBean db1DataSource() {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setXaDataSourceClassName("com.mysql.cj.jdbc.MysqlXADataSource");
        ds.setUniqueResourceName("businessDB1");
        ds.setMinPoolSize(5);
        ds.setMaxPoolSize(20);

        Properties props = new Properties();
        props.setProperty("url", "jdbc:mysql://localhost:3306/business_db1?useSSL=false&serverTimezone=UTC");
        props.setProperty("user", "root");
        props.setProperty("password", "123456");
        ds.setXaProperties(props);

        return ds;
    }

    // BUSINESS DB2 - XA DATASOURCE

    @Bean(name = "db2DataSource")
    @ConfigurationProperties(prefix = "app.datasource.db2")
    public AtomikosDataSourceBean db2DataSource() {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setXaDataSourceClassName("com.mysql.cj.jdbc.MysqlXADataSource");
        ds.setUniqueResourceName("businessDB2");
        ds.setMinPoolSize(5);
        ds.setMaxPoolSize(20);

        Properties props = new Properties();
        props.setProperty("url", "jdbc:mysql://localhost:3306/business_db2?useSSL=false&serverTimezone=UTC");
        props.setProperty("user", "root");
        props.setProperty("password", "123456");
        ds.setXaProperties(props);

        return ds;
    }
}