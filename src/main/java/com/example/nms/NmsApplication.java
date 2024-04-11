package com.example.nms;

import com.sun.management.OperatingSystemMXBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.management.ManagementFactory;

@SpringBootApplication
public class NmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NmsApplication.class, args);
		try {

			OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

			for(int i=0; i < 100; i++){
				System.out.println("===================");
				System.out.println("CPU Usage : " + String.format("%.2f", osBean.getSystemCpuLoad() * 100 ));
				System.out.println("Memory Free Space : " + String.format("%.2f", (double)osBean.getFreePhysicalMemorySize()/1024/1024/1024));
				System.out.println("Memory Total Space : " + String.format("%.2f", (double)osBean.getTotalPhysicalMemorySize()/1024/1024/1024  ));

				Thread.sleep(1000);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
