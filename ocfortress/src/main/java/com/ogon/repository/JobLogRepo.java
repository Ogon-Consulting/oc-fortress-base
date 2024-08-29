package com.ogon.repository;

import com.ogon.dto.JobLogDTO;
import com.ogon.entity.JobLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobLogRepo extends JpaRepository<JobLogEntity, Integer> {

    @Query("SELECT new com.ogon.dto.JobLogDTO(ifnull(max(id),0) as id) FROM JobLogEntity")
    JobLogDTO getMaxId();

    List<JobLogEntity> findByJobId(String jobId);

    @Query("SELECT je FROM JobLogEntity je " +
            "where je.carrierId = :carrierId " +
            "and je.stateCd = :stateCd " +
            "and je.product = :product")
    List<JobLogEntity> getJobLogForCarrierStateProduct(@Param("carrierId") String carrierId,
                                                       @Param("stateCd") String stateCd,
                                                       @Param("product") String product);

}