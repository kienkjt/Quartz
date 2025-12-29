//package com.vss.quartz.job;
//
//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Map;
//
//public class HelloJob implements Job {
//
//    private static final DateTimeFormatter time = DateTimeFormatter.ofPattern(" HH:mm:ss dd-MM-yyyy");
//
//    @Override
//    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//
//        String message = jobExecutionContext.getMergedJobDataMap().getString("message"); // Lấy dữ liệu message từ JobDataMap. Là chuỗi truyền vào khi tạo job
//        int retry = jobExecutionContext.getMergedJobDataMap().getInt("retry"); // Lấy dữ liệu retry từ JobDataMap. Là số nguyên truyền vào khi tạo job
//        Map<String,Object> metadata = (Map<String, Object>) jobExecutionContext
//                .getMergedJobDataMap()
//                .get("metadata"); // Lấy dữ liệu metadata từ JobDataMap. Là một Map truyền vào khi tạo job, có thể chứa nhiều cặp key-value khác nhau
//        System.out.println("Hello Job message: " + message + LocalDateTime.now().format(time));
//        System.out.println("Hello Job Retry count: " + retry);
//        System.out.println("Hello Job  Metadata: " + metadata);
//    }
//}
///*JobExecutionContext là một đối tượng được truyền vào phương thức execute của một Quartz Job.
//Nó cung cấp thông tin về ngữ cảnh thực thi của công việc hiện tại, bao gồm các chi tiết như JobDetail,
//Trigger, và các dữ liệu liên quan đến công việc đó.*/