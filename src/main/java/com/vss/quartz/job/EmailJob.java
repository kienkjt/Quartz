package com.vss.quartz.job;

import com.vss.quartz.service.EmailService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailJob implements Job {


    private final EmailService emailService;

    public EmailJob(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String to = jobDataMap.getString("to");
        String subject = jobDataMap.getString("subject");
        String body = jobDataMap.getString("body");

        try {
            emailService.sendEmail(to, subject, body);
            System.out.println("Email sent successfully to: " + to);
        } catch (Exception e) {
            System.err.println("Failed to send email to " + to + ": " + e.getMessage());
            throw new JobExecutionException(e);
        }
    }
}
