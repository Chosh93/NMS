package com.example.nms.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NmsMemoryDto {
    private Long memoryNo;
    private Date memoryDay;
    private double memoryCost;
}
