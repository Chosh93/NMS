package com.example.nms.repository;

import com.example.nms.entity.NmsCpu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NmsCpuRepository extends JpaRepository<NmsCpu, Long> {

}
