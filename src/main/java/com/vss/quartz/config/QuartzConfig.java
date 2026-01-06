package com.vss.quartz.config;

import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class QuartzConfig {

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory =
                new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(
            JobFactory jobFactory,
            @Qualifier("quartzXADataSource") DataSource quartzXA,
            @Qualifier("quartzNonXADataSource") DataSource quartzNonXA
    ) throws IOException {

        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(jobFactory);

        // XA + NON-XA
        factory.setDataSource(quartzXA);
        factory.setNonTransactionalDataSource(quartzNonXA);

        // Quartz properties
        PropertiesFactoryBean props = new PropertiesFactoryBean();
        props.setLocation(new ClassPathResource("application.properties"));
        props.afterPropertiesSet();

        factory.setQuartzProperties(props.getObject());

        factory.setOverwriteExistingJobs(true);
        factory.setAutoStartup(true);

        return factory;
    }
}
