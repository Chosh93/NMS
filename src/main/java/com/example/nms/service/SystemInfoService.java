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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class SystemInfoService {
    private final NmsCpuRepository nmsCpuRepository;
    private final NmsMemoryRepository nmsMemoryRepository;
    private final NmsDiskRepository nmsDiskRepository;
    private final NmsNetworkRepository nmsNetworkRepository;
    private long prevInbound = 0L;
    private long prevOutbound = 0L;

    public Map<String, Object> startMonitoring() {
        Map<String, Object> systemInfo = new HashMap<>();

        try {
            // CPU 정보 수집
            collectCPUInfo(systemInfo);
            collectMemoryInfo(systemInfo);
            collectDiskInfo(systemInfo);
            collectTrafficInfo(systemInfo);

            if ((double) systemInfo.get("cpuUsage") >= 80) {
                NmsCpu nmsCpu = new NmsCpu();
                nmsCpu.setCpuDay(new Date());
                nmsCpu.setCpuCost((double)systemInfo.get("cpuUsage"));
                nmsCpuRepository.save(nmsCpu);
            }

            // Memory 사용량이 85%를 초과하면 DB에 저장
            if ((double) systemInfo.get("memoryUsage") >= 85) {
                NmsMemory nmsMemory = new NmsMemory();
                nmsMemory.setMemoryDay(new Date());
                nmsMemory.setMemoryCost((double) systemInfo.get("memoryUsage"));
                nmsMemoryRepository.save(nmsMemory);
            }
            collectNetworkInfo(systemInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return systemInfo;
    }

    // cpu 관련 이슈정보 가져오기
    public List<NmsCpu> issueMonitoringList(){
        return nmsCpuRepository.findAll();
    }


    // cpu, memory, disk, network 이슈 카운트 가져오기
    public Map<String, Long> issueCount() {
        Map<String, Long> issueCntList = new HashMap<>();
        issueCntList.put("issueCpuCount", nmsCpuRepository.count());
        issueCntList.put("issueMemoryCount", nmsMemoryRepository.count());
        issueCntList.put("issueDiskCount", nmsDiskRepository.count());
        issueCntList.put("issueNetworkCount", nmsNetworkRepository.count());

        return issueCntList;
    }

    // cpu 사용량
    private void collectCPUInfo(Map<String, Object> systemInfo) {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        double cpuUsage = Double.parseDouble(String.format("%.2f", osBean.getSystemCpuLoad() * 100 * Runtime.getRuntime().availableProcessors() / 2));
        systemInfo.put("cpuUsage", cpuUsage);
    }

    // memory사용량
    private void collectMemoryInfo(Map<String, Object> systemInfo) {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        double memoryTotal = Double.parseDouble((String.format("%.2f", (double)osBean.getTotalMemorySize()/1024/1024/1024)));
        double memoryFree = Double.parseDouble((String.format("%.2f", (double)osBean.getFreeMemorySize()/1024/1024/1024)));
        double memoryUsage = 100 - Math.round(memoryFree/memoryTotal*100);
        systemInfo.put("memoryTotal", memoryTotal);
        systemInfo.put("memoryFree", memoryFree);
        systemInfo.put("memoryUsage", memoryUsage);
    }

    // disk 사용량
    private void collectDiskInfo(Map<String, Object> systemInfo) {
        double diskTotal = (double) (new File("/").getTotalSpace()) / Math.pow(1024,3);
        double diskUsable =  (double) (new File("/").getUsableSpace()) / Math.pow(1024,3);
        double diskUsage = Math.round((diskTotal-diskUsable)/diskTotal*100);
        systemInfo.put("diskTotal", diskTotal);
        systemInfo.put("diskUsable", diskUsable);
        systemInfo.put("diskUsage", diskUsage);
    }

    // network 정보 및 사용량
    private void collectNetworkInfo(Map<String, Object> systemInfo){
        try {
            String myInetAddress = InetAddress.getLocalHost().getHostAddress();
            systemInfo.put("IpAddress", myInetAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            systemInfo.put("IpAddress", "Unknown");
        }
    }

    // 이전 인바운드와 아웃바운드 초기값 저장


    // collectTrafficInfo 메서드에서 현재 값과 차이를 계산하고 시스템 정보에 저장
    private void collectTrafficInfo(Map<String, Object> systemInfo) {
        try {
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "netstat -e");
            builder.redirectErrorStream(true);
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "EUC-KR"));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("바이트")) {
                    String[] parts = line.trim().split("\\s+");
                    if (parts.length >= 3) {
                        // 현재 요청에서 가져온 인바운드와 아웃바운드 값
                        long currentInbound = Long.parseLong(parts[1]);
                        long currentOutbound = Long.parseLong(parts[2]);

                        // 이전 값에서 현재 값 빼기
                        long inboundDiff = currentInbound - prevInbound;
                        long outboundDiff = currentOutbound - prevOutbound;
                        double networkUsage = (double)(inboundDiff+outboundDiff)*8/1000000000*100;

                        // 시스템 정보에 현재 값 및 차이 값 저장
                        systemInfo.put("inboundDiff", inboundDiff);
                        systemInfo.put("outboundDiff", outboundDiff);
                        systemInfo.put("networkUsage", networkUsage);

                        // 이전 값 업데이트
                        prevInbound = currentInbound;
                        prevOutbound = currentOutbound;
                        break; // 첫 번째 "바이트" 라인에서 정보를 가져온 후 더 이상 읽지 않음
                    }
                }
            }
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("CMD 실행 실패, 종료 코드: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
