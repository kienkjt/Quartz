//package com.vss.quartz.controller;
//
//import com.vss.quartz.dto.JobRequest;
//import com.vss.quartz.service.JobSchedulerService;
//import org.quartz.JobDetail;
//import org.quartz.SchedulerException;
//import org.quartz.Trigger;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/jobs")
//public class JobController {
//    private final JobSchedulerService jobSchedulerService;
//
//    public JobController(JobSchedulerService jobSchedulerService) {
//        this.jobSchedulerService = jobSchedulerService;
//    }
//
//    // Tạo job mới
//    @PostMapping
//    public String createJob(@RequestBody JobRequest jobRequest) throws SchedulerException {
//        JobDetail jobDetail = jobSchedulerService.buildJobDetail(jobRequest);
//        Trigger trigger = jobSchedulerService.buildJobTrigger(jobRequest);
//        jobSchedulerService.scheduleJob(jobDetail, trigger);
//        return "Job scheduled successfully";
//    }
//
//    // Tạm dừng một job
//    @PutMapping("/{jobGroup}/{jobName}/pause")
//    public String pauseJob(@PathVariable String jobName, @PathVariable String jobGroup) throws SchedulerException {
//        jobSchedulerService.pauseJob(jobName, jobGroup);
//        return "Job paused successfully";
//    }
//
//    // Tiếp tục job đã tạm dừng
//    @PutMapping("/{jobGroup}/{jobName}/resume")
//    public String resumeJob(@PathVariable String jobName, @PathVariable String jobGroup) throws SchedulerException {
//        jobSchedulerService.resumeJob(jobName, jobGroup);
//        return "Job resumed successfully";
//    }
//
//    // Xóa một job
//    @DeleteMapping("/{jobGroup}/{jobName}")
//    public String deleteJob(@PathVariable String jobName, @PathVariable String jobGroup) throws SchedulerException {
//        jobSchedulerService.deleteJob(jobName, jobGroup);
//        return "Job deleted successfully";
//    }
//}
