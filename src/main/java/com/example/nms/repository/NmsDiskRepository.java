package com.example.nms.repository;

import com.example.nms.entity.NmsNetwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NmsDiskRepository extends JpaRepository<NmsNetwork, Long> {
}
