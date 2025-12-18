package com.vss.quartz.service;

import org.quartz.SchedulerException;

public interface JobSchedulerService {
    void scheduleJob(String jobName, String cron, String message) throws SchedulerException;
}
