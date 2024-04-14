package com.example.nms.repository;

import com.example.nms.entity.SystemInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemInfoRepository extends JpaRepository<SystemInfoEntity, Long> {

}
