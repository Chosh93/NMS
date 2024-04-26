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
@Table(name = "nms_network")
public class NmsNetwork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NETWORK_NO") // NETWORK_NO 컬럼
    private Long networkNo;

    @Column(name = "NETWORK_DAY") // NETWORK_DAY 컬럼
    private Date networkDay;

    @Column(name = "NETWORK_COST") // NETWORK_COST 컬럼
    private double networkCost;
}
