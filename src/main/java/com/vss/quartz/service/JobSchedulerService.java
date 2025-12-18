package com.vss.quartz.service;

import com.vss.quartz.dto.JobRequest;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

public interface JobSchedulerService {
    void scheduleJob(JobDetail jobdetail , Trigger trigger) throws SchedulerException;

    void pauseJob(String jobname) throws SchedulerException;

    void resumeJob(String jobname) throws SchedulerException;

    void deleteJob(String jobname) throws SchedulerException;

    JobDetail buildJobDetail(JobRequest jobRequest) ;

    Trigger buildJobTrigger(JobRequest jobRequest) ;
}
