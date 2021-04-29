package com.lepa.portal.service;


import com.lepa.portal.repository.CPURepo;
import com.lepa.portal.repository.ForumSectionRepo;
import com.lepa.portal.repository.ForumSubSectionRepo;
import com.lepa.portal.repository.GPURepo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Data
@Service
public class DataService {

    GPURepo gpuRepo;
    CPURepo cpuRepo;
    ForumSectionRepo forumSectionRepo;
    ForumSubSectionRepo forumSubSectionRepo;

    @Autowired
    public DataService(GPURepo gpuRepo, CPURepo cpuRepo, ForumSectionRepo forumSectionRepo, ForumSubSectionRepo forumSubSectionRepo) {
        this.gpuRepo = gpuRepo;
        this.cpuRepo = cpuRepo;
        this.forumSectionRepo = forumSectionRepo;
        this.forumSubSectionRepo = forumSubSectionRepo;
    }

    public void portalData() {
        log.info("data added to database");
    }
}
