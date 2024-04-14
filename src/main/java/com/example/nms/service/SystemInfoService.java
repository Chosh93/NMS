package com.example.nms.service;

import com.example.nms.entity.SystemInfoEntity;
import com.example.nms.repository.SystemInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SystemInfoService {
    private final SystemInfoRepository systemInfoRepository;

    public void saveCpu(SystemInfoEntity systemInfoEntity) {
        systemInfoRepository.save(systemInfoEntity);
    }
}
