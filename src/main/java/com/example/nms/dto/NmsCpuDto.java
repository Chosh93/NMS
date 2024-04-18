package com.example.nms.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NmsCpuDto {
    private Long cpuNo;
    private LocalDateTime cpuDay;
    private double cpuCost;
}
