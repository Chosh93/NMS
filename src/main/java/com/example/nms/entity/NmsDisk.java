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
@Table(name = "nms_disk")
public class NmsDisk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DISK_NO") // DISK_NO 컬럼
    private Long diskNo;

    @Column(name = "DISK_DAY") // DISK_DAY 컬럼
    private Date diskDay;

    @Column(name = "DISK_COST") // DISK_COST 컬럼
    private double diskCost;
}
