package com.ogon.quartz.scheduler.service;

import com.ogon.quartz.scheduler.dto.SchedulerIDDTO;
import com.ogon.quartz.scheduler.entity.QuartzJobDetails;
import com.ogon.quartz.scheduler.entity.QuartzTriggerDetails;
import com.ogon.quartz.scheduler.entity.SchedulerEntity;
import com.ogon.quartz.scheduler.repository.QuartzJobDetailsRepository;
import com.ogon.quartz.scheduler.repository.QuartzTriggerRepository;
import com.ogon.quartz.scheduler.repository.SchedulerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SchedulerService {

    @Autowired
    private SchedulerRepository schedulerRepository;

    @Autowired
    private QuartzJobDetailsRepository jobDetailsRepository;

    @Autowired
    private QuartzTriggerRepository triggersRepository;

    public int getNextJobId() {
        SchedulerIDDTO schedulerIDDTO = schedulerRepository.getMaxId();
        int nextJobId = schedulerIDDTO.getId();
        nextJobId++;
        return nextJobId;
    }

    public void insertJobRecord(SchedulerEntity schedulerEntity) {
        schedulerRepository.save(schedulerEntity);
    }

    public List<QuartzJobDetails> findAllJobs() {
        return jobDetailsRepository.findAll();
    }

    public List<SchedulerEntity> findAllScheduledJobs() {
        return schedulerRepository.findAll();
    }

    public List<SchedulerEntity> getAllActiveScheduledJobs() {
        return schedulerRepository.getAllActiveTriggers();
    }

    public QuartzTriggerDetails findTriggerByJobId(String jobId) {
        return triggersRepository.findByJobName(jobId);
    }

    @Transactional
    public void updateJobDetails(String prevFireTime, String nextFireTime, String triggerState, String jobName, String startTime, String endTime) {
        schedulerRepository.updateSchedulerJob(prevFireTime, nextFireTime, triggerState, jobName, startTime, endTime);
    }

    @Transactional
    public void updateSchedulerStatus(String jobName, String status) {
       schedulerRepository.updateSchedulerStatus(jobName, status);
    }

    @Transactional
    public void deleteScheduleJob(String jobName) {
        schedulerRepository.deleteSchedulerJob(jobName);
    }

    @Transactional
    public SchedulerEntity findSchedulerJob(String jobName) {
        return schedulerRepository.findByJobName(jobName);
    }

}
