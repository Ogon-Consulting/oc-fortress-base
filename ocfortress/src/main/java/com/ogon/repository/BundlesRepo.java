package com.ogon.repository;

import com.ogon.entity.BundlesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BundlesRepo extends JpaRepository<BundlesEntity, Integer> {

    BundlesEntity findById(int id);

    @Modifying
    @Transactional
    @Query("UPDATE BundlesEntity b " +
            "SET b.statusCd = 'DELETED' " +
            "WHERE b.id = :id")
    int deleteBundle(@Param("id") int id);

    @Query("SELECT bundlesentity FROM BundlesEntity bundlesentity " +
            "where bundlesentity.carrierId = :carrierId " +
            "and bundlesentity.stateCd = :stateCd " +
            "and bundlesentity.lob = :lob ")
    List<BundlesEntity> getBundlesForCarrierStateProduct(@Param("carrierId") String carrierId,
                                                         @Param("stateCd") String stateCd,
                                                         @Param("lob") String lob);

    @Query("SELECT bundlesentity FROM BundlesEntity bundlesentity " +
            "where bundlesentity.carrierId = :carrierId " +
            "and bundlesentity.stateCd = :stateCd " +
            "and bundlesentity.lob = :lob " +
            "and bundlesentity.statusCd = 'ACTIVE'")
    List<BundlesEntity> getActiveBundlesForCarrierStateProduct(@Param("carrierId") String carrierId,
                                                               @Param("stateCd") String stateCd,
                                                               @Param("lob") String lob);

    @Modifying
    @Transactional
    @Query("UPDATE BundlesEntity b " +
            "SET b.recentJobId = :recentJobId,  b.lastExecutionResult = :result, b.lastExecutedOn = :executedOn, b.lastExecutedBy = :executedBy " +
            "WHERE b.id = :id")
    void updateBundleJobId(@Param("id") int id, @Param("recentJobId") String recentJobId
                        , @Param("result") String result, @Param("executedBy") String executedBy
                        , @Param("executedOn") String executedOn);

}