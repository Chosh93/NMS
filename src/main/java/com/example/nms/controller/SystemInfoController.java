package com.example.nms.controller;

import com.example.nms.entity.NmsCpu;
import com.example.nms.entity.NmsMemory;
import com.example.nms.service.SystemInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
public class SystemInfoController {

    private final SystemInfoService systemInfoService;

    @GetMapping("/start-monitoring")
    public void startMonitoring() {
        systemInfoService.startMonitoring();
    }

}
