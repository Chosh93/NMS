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

    // cpu, memory, disk, network 사용량 조회
    @GetMapping("/start-monitoring")
    public ResponseEntity<Map<String, Object>> startMonitoring() {
        Map<String, Object> systemInfo = systemInfoService.startMonitoring();
        System.out.println(systemInfo);
        return ResponseEntity.ok(systemInfo);
    }

    // 각 issue count map으로 반환
    @GetMapping("/system-issue")
    public Map<String, Long> systemIssue() {
        Map<String, Long> systemIssue = systemInfoService.issueCount();
        return systemIssue;
    }

    // 각 이슈 관련 정보 리스트 반환

}
