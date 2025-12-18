package com.vss.quartz.service.Impl;

import com.vss.quartz.dto.JobRequest;
import com.vss.quartz.job.HelloJob;
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
    public void scheduleJob(JobDetail jobdetail, Trigger trigger) throws SchedulerException {
        scheduler.scheduleJob(jobdetail, trigger);
    }

    @Override
    public void pauseJob(String jobname) throws SchedulerException {
        scheduler.pauseJob(new JobKey(jobname));
    }

    @Override
    public void resumeJob(String jobname) throws SchedulerException {
        scheduler.resumeJob(new JobKey(jobname));
    }

    @Override
    public void deleteJob(String jobname) throws SchedulerException {
        scheduler.deleteJob(new JobKey(jobname));
    }

    @Override
    public JobDetail buildJobDetail(JobRequest jobRequest) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("message", jobRequest.getMessage());
        jobDataMap.put(("retry"), jobRequest.getRetry());
        jobDataMap.put("metadata", jobRequest.getMetadata());
        return JobBuilder.newJob()
                .ofType(HelloJob.class)
                .withIdentity(jobRequest.getJobName())
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    @Override
    public Trigger buildJobTrigger(JobRequest jobRequest) {
        return TriggerBuilder.newTrigger()
                .forJob(JobKey.jobKey(jobRequest.getJobName()))
                .withIdentity(jobRequest.getJobName() + "Trigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(jobRequest.getCron()))
                .build();
    }
}
