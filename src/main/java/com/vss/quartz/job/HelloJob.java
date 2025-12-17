package com.vss.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HelloJob implements Job {

    private static final DateTimeFormatter time = DateTimeFormatter.ofPattern(" HH:mm:ss dd-MM-yyyy");
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Hello Quartz" + LocalDateTime.now().format(time));
    }
}
