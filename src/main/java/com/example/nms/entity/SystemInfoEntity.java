package com.example.nms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Table(name = "NMS.CPU")
public class SystemInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CPU_NO") // CPU_NO 컬럼
    private Long cpuNo;

    @Column(name = "CPU_DAY") // CPU_DAY 컬럼
    private Date cpuDay;

    @Column(name = "CPU_COST") // CPU_COST 컬럼
    private double cpuCost;
}
