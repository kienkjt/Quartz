package com.vss.quartz.dto;

import lombok.Data;

import java.util.Map;

@Data
public class JobRequest {
    private String jobName; // tên của công việc
    private String jobGroup; // nhóm của công việc
    private String cron; // biểu thức cron để lên lịch công việc
    private String message; // thông điệp hoặc dữ liệu liên quan đến công việc
    private int retry; // số lần thử lại nếu công việc thất bại
    private Map<String,Object> metadata; // dữ liệu bổ sung dưới dạng cặp key-value
}
