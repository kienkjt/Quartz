package com.vss.quartz.service.Impl;

import com.vss.quartz.service.JobSchedulerService;
import org.quartz.*;
import org.springframework.stereotype.Service;

@Service
public class JobSchedulerServiceImpl implements JobSchedulerService {
    private final Scheduler scheduler;

    public JobSchedulerServiceImpl(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void scheduleJob(String jobName, String cron, String message) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob()
                .ofType(com.vss.quartz.job.HelloJob.class)
                .withIdentity(jobName)
                .usingJobData("message", message)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName + "Trigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .forJob(jobDetail)
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }
}
