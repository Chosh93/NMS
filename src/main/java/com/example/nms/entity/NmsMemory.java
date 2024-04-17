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
@Table(name = "nms_memory")
public class NmsMemory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMORY_NO")
    private Long memoryNo;

    @Column(name = "MEMORY_DAY")
    private Date memoryDay;

    @Column(name = "MEMORY_COST")
    private double memoryCost;
}
