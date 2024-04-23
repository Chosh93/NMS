package com.example.nms.service;

import com.example.nms.entity.NmsCpu;
import com.example.nms.entity.NmsMemory;
import com.example.nms.repository.NmsCpuRepository;
import com.sun.management.OperatingSystemMXBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class SystemInfoService {
    private final NmsCpuRepository nmsCpuRepository;

    public Map<String, Double> startMonitoring() {
        Map<String, Double> systemInfo = new HashMap<>();

        try {
            // CPU 정보 수집
            OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
            double cpuUsage = Double.parseDouble(String.format("%.2f", osBean.getSystemCpuLoad() * 100 * Runtime.getRuntime().availableProcessors() / 2)); // 현재 pc 6코어, 12 논리 프로세스
            double memoryTotal = Double.parseDouble((String.format("%.2f", (double)osBean.getTotalMemorySize()/1024/1024/1024)));
            double memoryFree = Double.parseDouble((String.format("%.2f", (double)osBean.getFreeMemorySize()/1024/1024/1024)));
            double diskTotal = (double) (new File("/").getTotalSpace()) / Math.pow(1024,3);
            double diskUsable =  (double) (new File("/").getUsableSpace()) / Math.pow(1024,3);

            systemInfo.put("cpuUsage", cpuUsage);
            systemInfo.put("memoryTotal", memoryTotal);
            systemInfo.put("memoryFree", memoryFree);
            systemInfo.put("diskTotal", diskTotal);
            systemInfo.put("diskUsable", diskUsable);

            if (cpuUsage >= 80) {
                NmsCpu nmsCpu = new NmsCpu();
                nmsCpu.setCpuDay(new Date());
                nmsCpu.setCpuCost(cpuUsage);
                nmsCpuRepository.save(nmsCpu);
            }

            if (100-(memoryFree/memoryTotal)*100 >= 85) {
                NmsMemory nmsMemory = new NmsMemory();
                nmsMemory.setMemoryDay(new Date());
                nmsMemory.setMemoryCost(100-(memoryFree/memoryTotal)*100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return systemInfo;
    }
}
