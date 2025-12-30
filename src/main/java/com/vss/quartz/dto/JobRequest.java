package com.vss.quartz.dto;

import lombok.Data;

import java.util.Map;

@Data
public class JobRequest {
    private String jobName;
    private String jobGroup;
    private String cron;
    private String message;
    private Map<String, Object> metadata;
}

