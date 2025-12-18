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

        String message = jobExecutionContext.getMergedJobDataMap().getString("message");
        System.out.println("Hello Quartz " + message + LocalDateTime.now().format(time));
    }
}
/*JobExecutionContext là một đối tượng được truyền vào phương thức execute của một Quartz Job.
Nó cung cấp thông tin về ngữ cảnh thực thi của công việc hiện tại, bao gồm các chi tiết như JobDetail,
Trigger, và các dữ liệu liên quan đến công việc đó.*/