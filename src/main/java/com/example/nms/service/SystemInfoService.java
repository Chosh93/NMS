package com.example.nms.service;

import com.example.nms.entity.NmsCpu;
import com.example.nms.entity.NmsMemory;
import com.example.nms.repository.NmsCpuRepository;
import com.example.nms.repository.NmsDiskRepository;
import com.example.nms.repository.NmsMemoryRepository;
import com.example.nms.repository.NmsNetworkRepository;
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
    private final NmsMemoryRepository nmsMemoryRepository;
    private final NmsDiskRepository nmsDiskRepository;
    private final NmsNetworkRepository nmsNetworkRepository;

    public Map<String, Double> startMonitoring() {
        Map<String, Double> systemInfo = new HashMap<>();

        try {
            // CPU 정보 수집
            OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
            double cpuUsage = Double.parseDouble(String.format("%.2f", osBean.getSystemCpuLoad() * 100 * Runtime.getRuntime().availableProcessors() / 2)); // 현재 pc 6코어, 12 논리 프로세스
            double memoryTotal = Double.parseDouble((String.format("%.2f", (double)osBean.getTotalMemorySize()/1024/1024/1024)));
            double memoryFree = Double.parseDouble((String.format("%.2f", (double)osBean.getFreeMemorySize()/1024/1024/1024)));
            double memoryUsage = 100 - Math.round(memoryFree/memoryTotal*100);
            double diskTotal = (double) (new File("/").getTotalSpace()) / Math.pow(1024,3);
            double diskUsable =  (double) (new File("/").getUsableSpace()) / Math.pow(1024,3);
            double diskUsage = Math.round((diskTotal-diskUsable)/diskTotal*100);

            systemInfo.put("cpuUsage", cpuUsage);
            systemInfo.put("memoryTotal", memoryTotal);
            systemInfo.put("memoryFree", memoryFree);
            systemInfo.put("memoryUsage", memoryUsage);
            systemInfo.put("diskTotal", diskTotal);
            systemInfo.put("diskUsable", diskUsable);
            systemInfo.put("diskUsage", diskUsage);

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

    public List<NmsCpu> issueMonitoringList(){
        return nmsCpuRepository.findAll();
    }

    public Map<String, Long> issueCount() {
        Map<String, Long> issueCntList = new HashMap<>();
        issueCntList.put("issueCpuCount", nmsCpuRepository.count());
        issueCntList.put("issueMemoryCount", nmsMemoryRepository.count());
        issueCntList.put("issueDiskCount", nmsDiskRepository.count());
        issueCntList.put("issueNetworkCount", nmsNetworkRepository.count());

        return issueCntList;
    }
}
