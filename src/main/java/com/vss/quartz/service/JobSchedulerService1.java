package com.vss.quartz.service;

import com.vss.quartz.dto.JobRequest;
import com.vss.quartz.job.MultiDatabaseJob;
import com.vss.quartz.job.SimpleJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JobSchedulerService1 {
    private static final Logger logger = LoggerFactory.getLogger(JobSchedulerService1.class);

    @Autowired
    private Scheduler scheduler;

    // Lên lịch các công việc khi ứng dụng sẵn sàng
    @EventListener(ApplicationReadyEvent.class)
    public void scheduleJobsOnStartup() {
        try {
            logger.info("Scheduling jobs on application startup...");

            scheduleSimpleJob();

            scheduleMultiDatabaseJob();

            logger.info("All jobs scheduled successfully");

        } catch (Exception e) {
            logger.error("Error scheduling jobs", e);
        }
    }

    // Lên lịch một công việc đơn giản chạy mỗi 30 giây
    public void scheduleSimpleJob() throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("simpleJob", "group1")
                .withDescription("Simple test job")
                .usingJobData("author", "system")
                .storeDurably()
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("simpleTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(30)
                        .repeatForever())
                .build();
        // Kiêm tra nếu công việc đã tồn tại
        if (scheduler.checkExists(jobDetail.getKey())) {
            logger.info("SimpleJob already exists, deleting and rescheduling...");
            scheduler.deleteJob(jobDetail.getKey());
        }

        Date scheduledDate = scheduler.scheduleJob(jobDetail, trigger);
        logger.info("SimpleJob scheduled to run at: {}", scheduledDate);
    }

    // Lên lịch một công việc đa cơ sở dữ liệu chạy mỗi phút
    public void scheduleMultiDatabaseJob() throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(MultiDatabaseJob.class)
                .withIdentity("multiDatabaseJob", "group1")
                .withDescription("Job that writes to multiple databases in XA transaction")
                .storeDurably()
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("multiDatabaseTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(1)
                        .repeatForever())
                .build();
        // Kiêm tra nếu công việc đã tồn tại
        if (scheduler.checkExists(jobDetail.getKey())) {
            logger.info("MultiDatabaseJob already exists, deleting and rescheduling...");
            scheduler.deleteJob(jobDetail.getKey());
        }

        Date scheduledDate = scheduler.scheduleJob(jobDetail, trigger);
        logger.info("MultiDatabaseJob scheduled to run at: {}", scheduledDate);
    }

    // lên lịch một công việc với biểu thức cron
    public void scheduleCronJob(JobRequest request) throws SchedulerException {

        JobKey jobKey = JobKey.jobKey(request.getJobName(), request.getJobGroup());

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("message", request.getMessage());

        if (request.getMetadata() != null) {
            jobDataMap.putAll(request.getMetadata());
        }

        JobDetail jobDetail = JobBuilder.newJob(MultiDatabaseJob.class)
                .withIdentity(jobKey)
                .usingJobData(jobDataMap)
                .build();

        TriggerKey triggerKey =
                TriggerKey.triggerKey(request.getJobName() + "Trigger", request.getJobGroup());

        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .forJob(jobDetail)
                .withSchedule(CronScheduleBuilder.cronSchedule(request.getCron()))
                .build();

        if (scheduler.checkExists(jobKey)) {
            scheduler.rescheduleJob(triggerKey, trigger);
        } else {
            scheduler.scheduleJob(jobDetail, trigger);
        }
    }



    public void triggerJobNow(String jobName, String groupName) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, groupName);
        if (scheduler.checkExists(jobKey)) {
            scheduler.triggerJob(jobKey);
            logger.info("Job triggered: {}.{}", groupName, jobName);
        } else {
            logger.warn("Job not found: {}.{}", groupName, jobName);
        }
    }


    public void pauseJob(String jobName, String groupName) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, groupName);
        if (scheduler.checkExists(jobKey)) {
            scheduler.pauseJob(jobKey);
            logger.info("Job paused: {}.{}", groupName, jobName);
        } else {
            logger.warn("Job not found: {}.{}", groupName, jobName);
        }
    }


    public void resumeJob(String jobName, String groupName) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, groupName);
        if (scheduler.checkExists(jobKey)) {
            scheduler.resumeJob(jobKey);
            logger.info("Job resumed: {}.{}", groupName, jobName);
        } else {
            logger.warn("Job not found: {}.{}", groupName, jobName);
        }
    }


    public void deleteJob(String jobName, String groupName) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, groupName);
        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
            logger.info("Job deleted: {}.{}", groupName, jobName);
        } else {
            logger.warn("Job not found: {}.{}", groupName, jobName);
        }
    }
}
