package com.vss.quartz.controller;

import com.vss.quartz.dto.JobRequest;
import com.vss.quartz.service.JobSchedulerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class JobController {
    private final JobSchedulerService jobSchedulerService;

    public JobController(JobSchedulerService jobSchedulerService) {
        this.jobSchedulerService = jobSchedulerService;
    }

    @PostMapping("/scheduleJob")
    public String createJob(@RequestBody JobRequest jobRequest){
        try{
           jobSchedulerService.scheduleJob(
                     jobRequest.getJobName(),
                     jobRequest.getCron(),
                     jobRequest.getMessage()
           );
              return "Job scheduled successfully!";
        } catch (Exception e){
            return "Error scheduling job: " + e.getMessage();
        }
    }
}
