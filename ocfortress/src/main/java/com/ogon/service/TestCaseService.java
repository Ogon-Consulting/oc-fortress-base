package com.ogon.service;

import com.ogon.entity.TestCasesEntity;
import com.ogon.repository.TestCasesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestCaseService {

    @Autowired
    private TestCasesRepo testCasesRepo;

    public TestCasesEntity getTestCaseByID(String carrierId, String stateCd, String lob, String testCaseId){
        return testCasesRepo.getTestCaseByID(carrierId,stateCd,lob,testCaseId);
    }
    public List<TestCasesEntity> getAllTestCasesForCarrierStateLOB(String carrierId, String stateCd, String lob){
        return testCasesRepo.getAllTestCasesForCarrierStateLOB(carrierId, stateCd, lob);
    }
    public void deleteTestCase(String carrierId, String stateCd, String lob, String testCaseId){
        testCasesRepo.deleteTestCaseID(carrierId,stateCd,lob,testCaseId);
    }

    public void saveTestCase(TestCasesEntity testCasesEntity){
        testCasesRepo.save(testCasesEntity);
    }
}
