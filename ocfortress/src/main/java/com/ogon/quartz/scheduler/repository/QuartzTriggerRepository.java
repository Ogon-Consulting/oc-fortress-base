package com.ogon.quartz.scheduler.repository;

import com.ogon.quartz.scheduler.entity.QuartzTriggerDetails;
import com.ogon.quartz.scheduler.entity.TriggerDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuartzTriggerRepository extends JpaRepository<QuartzTriggerDetails, TriggerDetailsId> {
    List<QuartzTriggerDetails> findByTriggerState(String triggerState);
    @Query("SELECT t FROM QuartzTriggerDetails t WHERE t.triggerState = 'WAITING' OR t.triggerState = 'ACQUIRED'")
    List<QuartzTriggerDetails> findAllPendingTriggers();
    QuartzTriggerDetails findByJobName(String jobName);
}
