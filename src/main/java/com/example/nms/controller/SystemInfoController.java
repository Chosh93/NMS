package com.example.nms.controller;

import com.example.nms.entity.NmsCpu;
import com.example.nms.repository.NmsCpuRepository;
import com.example.nms.repository.NmsMemoryRepository;
import com.example.nms.service.SystemInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
public class SystemInfoController {

    private final SystemInfoService systemInfoService;

    @GetMapping("/start-monitoring")
    public ResponseEntity<Map<String, Double>> startMonitoring() {
        Map<String, Double> systemInfo = systemInfoService.startMonitoring();
        System.out.println(systemInfo);
        return ResponseEntity.ok(systemInfo);
    }

    @GetMapping("/system-issue")
    public Map<String, Long> systemIssue() {
        Map<String, Long> systemIssue = systemInfoService.issueCount();
        return systemIssue;
    }
}
