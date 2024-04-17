package com.example.nms.repository;

import com.example.nms.entity.NmsMemory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NmsMemoryRepository extends JpaRepository<NmsMemory, Long> {
}
