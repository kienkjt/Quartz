package com.vss.quartz.config;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob()
                .withIdentity("helloJob","group1")
                .ofType(com.vss.quartz.job.HelloJob.class)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger() {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withIdentity("helloTrigger","group1")
                .startNow()
                .withSchedule(org.quartz.SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10)
                        .repeatForever())
                .build();
    }

    @Bean
    public JobDetail emailJobDetail(){
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("email","kienkj888@gmail.com");
        jobDataMap.put("subject","Test Email from Quartz Job");

        return JobBuilder.newJob()
                .withIdentity("emailJob","group2")
                .ofType(com.vss.quartz.job.EmailJob.class)
                .usingJobData(jobDataMap) // Truyền dữ liệu vào Job
                .storeDurably()
                .build();
    }
    String cronEmail = "0 34 11 * * ?";
    @Bean
    public Trigger emailJobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(emailJobDetail())
                .withIdentity("emailTrigger", "group2")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(cronEmail))
                .build();
    }
}
