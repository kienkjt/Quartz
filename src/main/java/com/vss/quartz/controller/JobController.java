package com.vss.quartz.controller;

import com.vss.quartz.dto.JobRequest;
import com.vss.quartz.service.JobSchedulerService;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
public class JobController {
    private final JobSchedulerService jobSchedulerService;

    public JobController(JobSchedulerService jobSchedulerService) {
        this.jobSchedulerService = jobSchedulerService;
    }
    // Tạo job mới
    @PostMapping
    public String createJob(@RequestBody JobRequest jobRequest) throws SchedulerException {
        JobDetail jobDetail = jobSchedulerService.buildJobDetail(jobRequest);
        Trigger trigger = jobSchedulerService.buildJobTrigger(jobRequest);
        jobSchedulerService.scheduleJob(jobDetail, trigger);
        return "Job scheduled successfully";
    }

    // Tạm dừng một job
    @PutMapping("/{jobName}/pause")
    public String pauseJob(@PathVariable String jobName) throws SchedulerException {
        jobSchedulerService.pauseJob(jobName);
        return "Job paused successfully";
    }
    // Tiếp tục job đã tạm dừng
    @PutMapping("/{jobName}/resume")
    public String resumeJob(@PathVariable String jobName) throws SchedulerException {
        jobSchedulerService.resumeJob(jobName);
        return "Job resumed successfully";
    }

    // Xóa một job
    @DeleteMapping("/{jobName}")
    public String deleteJob(@PathVariable String jobName) throws SchedulerException {
        jobSchedulerService.deleteJob(jobName);
        return "Job deleted successfully";
    }
}
