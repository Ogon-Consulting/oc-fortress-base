package com.ogon.springbootcontroller.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ogon.apidatamapper.TestCaseDataMapper;
import com.ogon.entity.TestCasesEntity;
import com.ogon.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class TestCaseAPIController {

    @Autowired
    private TestCaseService testCaseService;

    @PostMapping("/gettestcasebyid")
    public String getTestCaseByID(@RequestParam TestCaseDataMapper testCaseInputData) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        String carrierId = testCaseInputData.getCarrierId();
        String stateCd = testCaseInputData.getStateCd();
        String lob = testCaseInputData.getLob();
        try {
            ArrayNode testCaseArrayNode = objectMapper.createArrayNode();
            List<TestCasesEntity> testCasesEntities = testCaseService.getAllTestCasesForCarrierStateLOB(carrierId,stateCd,lob);
            for(TestCasesEntity testCasesEntity : testCasesEntities) {
                ObjectNode testCaseNode = objectMapper.createObjectNode();
                testCaseNode.put("testCaseId", testCasesEntity.getTestCaseId());
                testCaseNode.put("testCaseName", testCasesEntity.getTestCaseName());
                testCaseNode.put("driverName", testCasesEntity.getTestDriver());
                testCaseNode.put("lastRunDate", testCasesEntity.getLastRunDate());
                testCaseNode.put("lastJobId", testCasesEntity.getLastJobID());
                testCaseNode.put("lastRunStatus", testCasesEntity.getLastRunStatus());
                testCaseNode.put("lastRunBy", testCasesEntity.getLastRunBy());
                testCaseNode.put("lastRunResult", testCasesEntity.getLastRunOutcome());
                testCaseArrayNode.add(testCaseNode);
            }
            response.set("testCaseDetails", testCaseArrayNode);
            response.put("status", "success");
            response.put("message", "TestCases retrieved successfully for " +carrierId +" "+ stateCd+" "+lob);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("status", "failed");
            response.put("message", "TestCases fetch unsuccessful for " +carrierId +" "+ stateCd+" "+lob);
        }
        return response.toString();
    }
}
