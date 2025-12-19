package com.vss.quartz.dto;

import lombok.Data;

@Data
public class EmailJobRequest {
    private String jobName;
    private String cron;
    private String to;
    private String subject;
    private String body;
}
