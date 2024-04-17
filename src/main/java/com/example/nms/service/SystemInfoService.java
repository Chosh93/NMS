package com.example.nms.service;

import com.example.nms.NmsApplication;
import com.example.nms.entity.NmsCpu;
import com.example.nms.repository.NmsCpuRepository;
import com.sun.management.OperatingSystemMXBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class SystemInfoService {
    private final NmsCpuRepository nmsCpuRepository;

    public void saveCpu() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(NmsApplication::printSystemInfo, 0, 5, TimeUnit.SECONDS);

        try {
            OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

            System.out.println("===================");
            System.out.println("CPU Usage : " + String.format("%.2f", osBean.getSystemCpuLoad() * 100));
            // cpu 사용량은 0~1사이의 값으로 가져오므로 * 100 으로 백분율로 출력
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
