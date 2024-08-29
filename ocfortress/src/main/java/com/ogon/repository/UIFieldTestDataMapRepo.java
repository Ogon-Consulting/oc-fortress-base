package com.ogon.repository;

import com.ogon.dto.TestCaseDetailDTO;
import com.ogon.dto.TestCaseDriverDTO;
import com.ogon.dto.UIFieldValueDataMapDTO;
import com.ogon.entity.TestDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UIFieldTestDataMapRepo extends JpaRepository<TestDataEntity, Integer> {

    @Query("select new com.ogon.dto.UIFieldValueDataMapDTO(testCaseId, uiName, uiSequence, fieldName, fieldId, fieldSequence, defaultValue, readOnly) from TestDataEntity " +
            "where carrierId = :carrierId " +
            "and stateCd = :stateCd " +
            "and lob = :lob " +
            "and showField = 'Yes' " +
            "order by uiSequence, fieldSequence")
    List<UIFieldValueDataMapDTO> getAllUIFieldDataForCarrierStateLob(@Param("carrierId") String carrierId,
                                                                     @Param("stateCd") String stateCd,
                                                                     @Param("lob") String lob);

    @Query("select new com.ogon.dto.UIFieldValueDataMapDTO(testCaseId, uiName, uiSequence, fieldName, fieldId, fieldSequence, defaultValue, readOnly) from TestDataEntity " +
            "where carrierId = :carrierId " +
            "and stateCd = :stateCd " +
            "and lob = :lob " +
            "and testCaseId = :testCaseId " +
            "and showField = 'Yes' " +
            "order by uiSequence, fieldSequence")
    List<UIFieldValueDataMapDTO> getTestDataByTestCaseID(@Param("carrierId") String carrierId,
                                                         @Param("stateCd") String stateCd,
                                                         @Param("lob") String lob,
                                                         @Param("testCaseId") String testCaseId);

    List<TestDataEntity> findByTestCaseId(String testCaseId);

    List<TestDataEntity> findByTestCaseName(String testCaseName);

    @Query("SELECT testdata FROM TestDataEntity testdata " +
            "WHERE testdata.carrierId = :carrierId " +
            "AND testdata.stateCd = :stateCd " +
            "AND testdata.lob = :lob " +
            "AND testdata.testCaseId = :testCaseId " +
            "ORDER BY testdata.uiSequence, testdata.fieldSequence")
    List<TestDataEntity> getAllTestDataByTestCaseID(@Param("carrierId") String carrierId,
                                                    @Param("stateCd") String stateCd,
                                                    @Param("lob") String lob,
                                                    @Param("testCaseId") String testCaseId);

    @Query("SELECT DISTINCT new com.ogon.dto.TestCaseDetailDTO(testdata.testCaseId, testdata.testCaseName) FROM TestDataEntity testdata " +
            "WHERE testdata.carrierId = :carrierId " +
            "AND testdata.stateCd = :stateCd " +
            "AND testdata.lob = :lob ")
    List<TestCaseDetailDTO> getAllUniqueTestCaseID(@Param("carrierId") String carrierId,
                                                   @Param("stateCd") String stateCd,
                                                   @Param("lob") String lob);

    @Query("SELECT DISTINCT new com.ogon.dto.TestCaseDriverDTO(driverName, testCaseName) FROM TestDataEntity " +
            "WHERE testCaseId = :testCaseId ")
    TestCaseDriverDTO getDriverName(@Param("testCaseId") String testCaseId);

    @Query("delete FROM TestDataEntity testdata " +
            "WHERE testdata.carrierId = :carrierId " +
            "AND testdata.stateCd = :stateCd " +
            "AND testdata.lob = :lob " +
            "AND testdata.testCaseId = :testCaseId ")
    void deleteByTestCaseId(@Param("carrierId") String carrierId,
                            @Param("stateCd") String stateCd,
                            @Param("lob") String lob,
                            @Param("testCaseId") String testCaseId);

}
