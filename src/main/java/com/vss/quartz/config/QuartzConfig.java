package com.vss.quartz.config;

import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class QuartzConfig {

    private final DataSource quartzXADataSource;
    private final DataSource quartzNonXADataSource;

    public QuartzConfig(DataSource quartzXADataSource, DataSource quartzNonXADataSource) {
        this.quartzXADataSource = quartzXADataSource;
        this.quartzNonXADataSource = quartzNonXADataSource;
    }

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(jobFactory);
        factory.setDataSource(quartzXADataSource);
        factory.setNonTransactionalDataSource(quartzNonXADataSource);
        factory.setQuartzProperties(quartzProperties());
        factory.setApplicationContextSchedulerContextKey("applicationContext");
        factory.setWaitForJobsToCompleteOnShutdown(true);
        factory.setAutoStartup(true);
        return factory;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/application.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
}
