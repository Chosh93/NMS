package com.example.nms.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NmsCpuDto {
    private Long cpuNo;
    private Date cpuDay;
    private double cpuCost;
}
