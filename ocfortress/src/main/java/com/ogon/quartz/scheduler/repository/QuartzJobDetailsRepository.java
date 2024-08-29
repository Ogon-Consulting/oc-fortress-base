package com.ogon.quartz.scheduler.repository;

import com.ogon.quartz.scheduler.entity.QuartzJobDetails;
import com.ogon.quartz.scheduler.entity.QuartzJobDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuartzJobDetailsRepository extends JpaRepository<QuartzJobDetails, QuartzJobDetailsId> {
    List<QuartzJobDetails> findByJobName(String jobName);

}
