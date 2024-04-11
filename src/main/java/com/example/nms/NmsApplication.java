package com.example.nms;

import com.sun.management.OperatingSystemMXBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.management.ManagementFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class NmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NmsApplication.class, args);

		// 5초마다 CPU 및 메모리 정보 가져오기
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(NmsApplication::printSystemInfo, 0, 5, TimeUnit.SECONDS);
	}

	private static void printSystemInfo() {
		try {
			OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

			System.out.println("===================");
			System.out.println("CPU Usage : " + String.format("%.2f", osBean.getSystemCpuLoad() * 100));
			// cpu 사용량은 0~1사이의 값으로 가져오므로 * 100 으로 백분율로 출력
			System.out.println("Memory Free Space : " + String.format("%.2f", (double) osBean.getFreePhysicalMemorySize() / 1024 / 1024 / 1024));
			System.out.println("Memory Total Space : " + String.format("%.2f", (double) osBean.getTotalPhysicalMemorySize() / 1024 / 1024 / 1024));
			// 메모리를 가져올때 바이트 단위로 가져오기 때문에 1024로 세번 나눠 킬로 -> 메가 -> 기가로 변환
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
