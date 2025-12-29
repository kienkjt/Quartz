//package com.vss.quartz.service;
//
//import com.vss.quartz.dto.EmailJobRequest;
//import com.vss.quartz.dto.JobRequest;
//import org.quartz.JobDetail;
//import org.quartz.SchedulerException;
//import org.quartz.Trigger;
//
//public interface JobSchedulerService {
//    void scheduleJob(JobDetail jobdetail , Trigger trigger) throws SchedulerException;
//
//    void pauseJob(String jobname, String jobGroup) throws SchedulerException;
//
//    void resumeJob(String jobname, String jobGroup) throws SchedulerException;
//
//    void deleteJob(String jobname, String jobGroup) throws SchedulerException;
//
//    JobDetail buildJobDetail(JobRequest jobRequest) ;
//
//    void scheduleEmailJob(EmailJobRequest request) throws SchedulerException;
//
//    Trigger buildJobTrigger(JobRequest jobRequest) ;
//}
