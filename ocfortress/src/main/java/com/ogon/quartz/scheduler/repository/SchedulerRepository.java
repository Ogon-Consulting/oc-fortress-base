package com.ogon.quartz.scheduler.repository;

import com.ogon.quartz.scheduler.dto.SchedulerIDDTO;
import com.ogon.quartz.scheduler.entity.SchedulerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SchedulerRepository extends JpaRepository<SchedulerEntity, Integer> {

    @Query("SELECT new com.ogon.quartz.scheduler.dto.SchedulerIDDTO(ifnull(max(id),0) as id) FROM SchedulerEntity")
    SchedulerIDDTO getMaxId();

    @Modifying
    @Transactional
    @Query("UPDATE SchedulerEntity se " +
            "SET se.prevFireTime = :prevFireTime, se.nextFireTime = :nextFireTime, se.triggerState = :triggerState, se.startTime = :startTime, se.endTime = :endTime " +
            "WHERE se.jobName = :jobName")
    void updateSchedulerJob(@Param("prevFireTime") String prevFireTime,
                            @Param("nextFireTime") String nextFireTime,
                            @Param("triggerState") String triggerState,
                            @Param("jobName") String jobName,
                            @Param("startTime") String startTime,
                            @Param("endTime") String endTime);

    @Modifying
    @Transactional
    @Query("UPDATE SchedulerEntity se " +
            "SET se.triggerState = :triggerState " +
            "WHERE se.jobName = :jobName")
    void updateSchedulerStatus(@Param("jobName") String jobName,
                               @Param("triggerState") String triggerState);

    @Modifying
    @Transactional
    @Query("UPDATE SchedulerEntity se " +
            "SET se.triggerState = 'DELETED'" +
            "WHERE se.jobName = :jobName")
    void deleteSchedulerJob(@Param("jobName") String jobName);

    @Query("SELECT schedules FROM SchedulerEntity schedules WHERE triggerState IN ('WAITING','ACQUIRED','BLOCKED','TRIGGERED','NORMAL') ")
    List<SchedulerEntity> getAllActiveTriggers();

    SchedulerEntity findByJobName(String jobName);

}
