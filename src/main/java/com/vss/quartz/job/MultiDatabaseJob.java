package com.vss.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

@Component
public class MultiDatabaseJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(MultiDatabaseJob.class);

    @Autowired
    @Qualifier("db1DataSource")
    private DataSource db1DataSource;

    @Autowired
    @Qualifier("db2DataSource")
    private DataSource db2DataSource;

    @Override
    @Transactional
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("========================================");
        logger.info("MultiDatabaseJob started at: {}", LocalDateTime.now());
        logger.info("Job Key: {}", context.getJobDetail().getKey());
        logger.info("========================================");

        try {
            // Execute operations on DB1
            executeOnDB1();

            // Execute operations on DB2
            executeOnDB2();

            logger.info("Successfully completed distributed transaction across DB1 and DB2");

        } catch (Exception e) {
            logger.error("Error executing job, transaction will be rolled back", e);
            throw new JobExecutionException(e);
        }

        logger.info("MultiDatabaseJob completed at: {}", LocalDateTime.now());
    }

    private void executeOnDB1() throws Exception {
        logger.info("Executing on business_db1...");

        try (Connection conn = db1DataSource.getConnection()) {
            // Create table if not exists
            String createTable = "CREATE TABLE IF NOT EXISTS job_log_db1 (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "job_name VARCHAR(255), " +
                    "executed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "message TEXT" +
                    ")";

            try (PreparedStatement stmt = conn.prepareStatement(createTable)) {
                stmt.execute();
            }

            // Insert log entry
            String insert = "INSERT INTO job_log_db1 (job_name, message) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insert)) {
                stmt.setString(1, "MultiDatabaseJob");
                stmt.setString(2, "Job executed on DB1 at " + LocalDateTime.now());
                int rows = stmt.executeUpdate();
                logger.info("Inserted {} rows into business_db1", rows);
            }

            // Query to verify
            String query = "SELECT COUNT(*) FROM job_log_db1";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    logger.info("Total records in business_db1: {}", rs.getInt(1));
                }
            }
        }
    }

    private void executeOnDB2() throws Exception {
        logger.info("Executing on business_db2...");

        try (Connection conn = db2DataSource.getConnection()) {
            // Create table if not exists
            String createTable = "CREATE TABLE IF NOT EXISTS job_log_db2 (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "job_name VARCHAR(255), " +
                    "executed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "message TEXT" +
                    ")";

            try (PreparedStatement stmt = conn.prepareStatement(createTable)) {
                stmt.execute();
            }

            // Insert log entry
            String insert = "INSERT INTO job_log_db2 (job_name, message) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insert)) {
                stmt.setString(1, "MultiDatabaseJob");
                stmt.setString(2, "Job executed on DB2 at " + LocalDateTime.now());
                int rows = stmt.executeUpdate();
                logger.info("Inserted {} rows into business_db2", rows);
            }

            // Query to verify
            String query = "SELECT COUNT(*) FROM job_log_db2";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    logger.info("Total records in business_db2: {}", rs.getInt(1));
                }
            }
        }
    }
}
