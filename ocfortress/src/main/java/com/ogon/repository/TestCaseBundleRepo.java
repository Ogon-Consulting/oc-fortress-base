package com.ogon.repository;

import com.ogon.dto.TestCaseBundleDetailsDTO;
import com.ogon.dto.TestCaseBundleIDDTO;
import com.ogon.entity.TestCaseBundleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestCaseBundleRepo extends JpaRepository<TestCaseBundleEntity, Integer> {

    @Query("Select new com.ogon.dto.TestCaseBundleIDDTO(IFNULL(max(bundleMapperId),0) as bundleMapperId) from TestCaseBundleEntity")
    TestCaseBundleIDDTO getMaxBundleMapperId();

    @Query("Select new com.ogon.dto.TestCaseBundleDetailsDTO(testCaseId, testCaseName) from TestCaseBundleEntity WHERE bundleMapperId = :bundleMapperId")
    List<TestCaseBundleDetailsDTO> getTestCaseDetailsByBundleMapperId(@Param("bundleMapperId") int bundleMapperId);


}
