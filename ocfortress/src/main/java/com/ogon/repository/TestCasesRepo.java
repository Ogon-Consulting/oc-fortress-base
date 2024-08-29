package com.ogon.repository;

import com.ogon.entity.TestCasesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TestCasesRepo extends JpaRepository<TestCasesEntity, Integer> {

    @Query("SELECT testcaseentity FROM TestCasesEntity testcaseentity " +
            "where testcaseentity.carrierId = :carrierId " +
            "and testcaseentity.stateCd = :stateCd " +
            "and testcaseentity.lob = :lob " +
            "and testcaseentity.testCaseId = :testCaseId")
    TestCasesEntity getTestCaseByID(@Param("carrierId") String carrierId,
                                    @Param("stateCd") String stateCd,
                                    @Param("lob") String lob,
                                    @Param("testCaseId") String testCaseId);

    @Query("SELECT testcaseentity FROM TestCasesEntity testcaseentity " +
            "where testcaseentity.carrierId = :carrierId " +
            "and testcaseentity.stateCd = :stateCd " +
            "and testcaseentity.lob = :lob ")
    List<TestCasesEntity> getAllTestCasesForCarrierStateLOB(@Param("carrierId") String carrierId,
                                                            @Param("stateCd") String stateCd,
                                                            @Param("lob") String lob);
    @Modifying
    @Transactional
    @Query("DELETE FROM TestCasesEntity " +
            "where carrierId = :carrierId " +
            "and stateCd = :stateCd " +
            "and lob = :lob " +
            "and testCaseId = :testCaseId")
    void deleteTestCaseID(@Param("carrierId") String carrierId,
                          @Param("stateCd") String stateCd,
                          @Param("lob") String lob,
                          @Param("testCaseId") String testCaseId);
}
