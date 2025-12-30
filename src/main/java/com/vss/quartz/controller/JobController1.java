package com.vss.quartz.controller;

import com.vss.quartz.dto.JobRequest;
import com.vss.quartz.service.JobSchedulerService1;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/jobs")
public class JobController1 {

    @Autowired
    private JobSchedulerService1 jobSchedulerService;

    @PostMapping("/cron")
    public ResponseEntity<?> scheduleCronJob(@RequestBody JobRequest request) throws SchedulerException {
        jobSchedulerService.scheduleCronJob(request);
        return ResponseEntity.ok("Job scheduled successfully");
    }


    @PostMapping("/{group}/{name}/pause")
    public ResponseEntity<Map<String, String>> pauseJob(
            @PathVariable String group,
            @PathVariable String name) {
        Map<String, String> response = new HashMap<>();
        try {
            jobSchedulerService.pauseJob(name, group);
            response.put("status", "success");
            response.put("message", "Job paused: " + group + "." + name);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/{group}/{name}/resume")
    public ResponseEntity<Map<String, String>> resumeJob(
            @PathVariable String group,
            @PathVariable String name) {
        Map<String, String> response = new HashMap<>();
        try {
            jobSchedulerService.resumeJob(name, group);
            response.put("status", "success");
            response.put("message", "Job resumed: " + group + "." + name);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @DeleteMapping("/{group}/{name}")
    public ResponseEntity<Map<String, String>> deleteJob(
            @PathVariable String group,
            @PathVariable String name) {
        Map<String, String> response = new HashMap<>();
        try {
            jobSchedulerService.deleteJob(name, group);
            response.put("status", "success");
            response.put("message", "Job deleted: " + group + "." + name);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }


    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "ok");
        response.put("message", "Job management API is running");
        return ResponseEntity.ok(response);
    }
}