package com.example.nms.service;

import com.example.nms.entity.NmsCpu;
import com.example.nms.repository.NmsCpuRepository;
import com.sun.management.OperatingSystemMXBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class SystemInfoService {
    private final NmsCpuRepository nmsCpuRepository;

    public void startMonitoring() {
        // 초기화 작업 등 필요한 내용 추가 가능
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::saveSystemInfo, 0, 5, TimeUnit.SECONDS);
    }
    private void saveSystemInfo() {
        try {
            // CPU 정보 수집
            OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
            double cpuUsage = Double.parseDouble(String.format("%.2f", osBean.getSystemCpuLoad() * 100));
            System.out.println(cpuUsage);
            NmsCpu nmsCpu = new NmsCpu();
            Date now = new Date();
            nmsCpu.setCpuDay(now);
            nmsCpu.setCpuCost(cpuUsage);
            nmsCpuRepository.save(nmsCpu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
