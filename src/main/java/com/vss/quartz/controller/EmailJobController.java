//package com.vss.quartz.controller;
//
//import com.vss.quartz.dto.EmailJobRequest;
//import com.vss.quartz.service.JobSchedulerService;
//import org.quartz.SchedulerException;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/jobs/email")
//public class EmailJobController {
//
//    private final JobSchedulerService jobSchedulerService;
//
//    public EmailJobController(JobSchedulerService jobSchedulerService) {
//        this.jobSchedulerService = jobSchedulerService;
//    }
//
//    @PostMapping
//    public String scheduleEmailJob(@RequestBody EmailJobRequest request) throws SchedulerException {
//        jobSchedulerService.scheduleEmailJob(request);
//        return "Email job scheduled successfully";
//    }
//}
