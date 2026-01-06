package com.vss.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class MultiDatabaseJob implements Job {

    private static final Logger logger =
            LoggerFactory.getLogger(MultiDatabaseJob.class);

    @Autowired
    @Qualifier("jdbcTemplateDB1")
    private JdbcTemplate jdbcTemplateDB1;

    @Autowired
    @Qualifier("jdbcTemplateDB2")
    private JdbcTemplate jdbcTemplateDB2;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        String message = context.getMergedJobDataMap().getString("message");

        TransactionTemplate transactionTemplate =
                new TransactionTemplate(transactionManager);

        try {
            transactionTemplate.execute(status -> {

                jdbcTemplateDB1.update(
                        "INSERT INTO job_log_db1 (job_name, message) VALUES (?, ?)", "MultiDatabaseJob", message
                );

                jdbcTemplateDB2.update(
                        "INSERT INTO job_log_db2 (job_name, message) VALUES (?, ?)", "MultiDatabaseJob", message
                );

                // TEST XA ROLLBACK
//                throw new RuntimeException("Simulated error on DB2");

                return null;
            } );
        } catch (Exception e) {
            logger.error("XA transaction rolled back", e);
            throw new JobExecutionException(e);
        }
    }
}
