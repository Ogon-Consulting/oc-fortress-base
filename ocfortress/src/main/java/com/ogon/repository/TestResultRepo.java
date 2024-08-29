package com.ogon.repository;

import com.ogon.dto.TestCaseQueryDTO;
import com.ogon.dto.TestResultQueryDTO;
import com.ogon.entity.TestResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestResultRepo extends JpaRepository<TestResultEntity, Integer> {
   @Query("SELECT new com.ogon.dto.TestResultQueryDTO(a.testCaseId, a.carrierId, a.stateCd, a.lob, a.testCaseName, a.transactionCd, a.referenceNumber, a.transactionNumber, a.testStatus, a.executedOn, a.executedBy, a.testResult, a.jobId) FROM TestResultEntity a, (SELECT MAX(T.executedOn) AS maxExecutedOn, T.testCaseId AS testCaseId FROM TestResultEntity T GROUP BY T.testCaseId) AS b WHERE a.testCaseId = b.testCaseId AND a.executedOn = b.maxExecutedOn")
    List<TestResultQueryDTO> getFetchTestCases();

 @Query("SELECT new com.ogon.dto.TestResultQueryDTO(a.testCaseId, a.carrierId, a.stateCd, a.lob, a.testCaseName, a.transactionCd, a.referenceNumber, a.transactionNumber, a.testStatus, a.executedOn, a.executedBy, a.testResult, a.jobId) FROM TestResultEntity a, (SELECT MAX(T.executedOn) AS maxExecutedOn, T.testCaseId AS testCaseId FROM TestResultEntity T GROUP BY T.testCaseId) AS b WHERE a.testCaseId = :testCaseId AND  a.testCaseId = b.testCaseId AND a.executedOn = b.maxExecutedOn")
 List<TestResultQueryDTO> getFetchTestCaseByID(@Param("testCaseId") String testCaseId);

    @Query("SELECT DISTINCT new com.ogon.dto.TestCaseQueryDTO(a.testCaseId, a.testCaseName) FROM TestResultEntity a " +
            "WHERE a.carrierId = :carrierId " +
            "AND a.stateCd = :stateCd " +
            "AND a.lob = :lob ")
    List<TestCaseQueryDTO> getAllTestDataByTestCaseID(@Param("carrierId") String carrierId,
                                                      @Param("stateCd") String stateCd,
                                                      @Param("lob") String lob);

}
