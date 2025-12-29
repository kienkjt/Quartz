package com.vss.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SimpleJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(SimpleJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("========================================");
        logger.info("SimpleJob executed at: {}", LocalDateTime.now());
        logger.info("Job Key: {}", context.getJobDetail().getKey());
        logger.info("Job Data: {}", context.getJobDetail().getJobDataMap());
        logger.info("========================================");
    }
}
