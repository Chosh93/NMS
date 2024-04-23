package com.example.nms.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NmsDisDto {
    private Long diskNo;
    private Date diskDay;
    private double diskCost;
}
