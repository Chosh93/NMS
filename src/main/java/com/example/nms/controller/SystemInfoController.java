package com.example.nms.controller;

import com.example.nms.entity.SystemInfoEntity;
import com.example.nms.service.SystemInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
public class SystemInfoController {

    private final SystemInfoService systemInfoService;
    private final SystemInfoEntity systemInfoEntity;

    @GetMapping("/start-monitoring")
    public void startMonitoring() {
        systemInfoService.saveCpu(systemInfoEntity);
    }
}
