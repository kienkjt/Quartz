//package com.vss.quartz.job;
//
//import org.quartz.Job;
//import org.quartz.JobDataMap;
//import org.quartz.JobExecutionException;
//
//public class EmailJob implements Job {
//    @Override
//    public void execute(org.quartz.JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
//        String email = jobDataMap.getString("email");
//        String subject = jobDataMap.getString("subject");
//
//        System.out.println("Executing Email Job:");
//        System.out.println("Sending email to: " + email);
//        System.out.println("Email Subject: " + subject);
//    }
//}
