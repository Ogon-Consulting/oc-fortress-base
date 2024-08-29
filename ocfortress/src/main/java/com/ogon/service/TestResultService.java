package com.ogon.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ogon.dto.JobLogDTO;
import com.ogon.dto.TestCaseDetailDTO;
import com.ogon.dto.TestResultQueryDTO;
import com.ogon.entity.JobLogEntity;
import com.ogon.entity.TestResultEntity;
import com.ogon.repository.JobLogRepo;
import com.ogon.repository.TestResultRepo;
import com.ogon.repository.UIFieldTestDataMapRepo;
import com.ogon.umv.common.utility.HelperClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TestResultService {

    @Autowired
    private TestResultRepo testResultRepo;

    @Autowired
    private UIFieldTestDataMapRepo testCaseRepo;
    @Autowired
    private JobLogRepo jobLogRepo;

    public TestResultEntity saveTestResults(TestResultEntity testResultEntity) {
        return testResultRepo.save(testResultEntity);
    }

    public void clearAllTestResults() {
        testResultRepo.deleteAll();
    }

    public JobLogDTO getMaxId() {
        return jobLogRepo.getMaxId();
    }

    public JobLogEntity addJobLog(JobLogEntity jobLogEntity) {
        return jobLogRepo.save(jobLogEntity);
    }

    public void updateJobCompletedOnAndStatus(String timestamp, String jobId, String status) {
        List<JobLogEntity> jobLogEntities = jobLogRepo.findByJobId(jobId);
        if (jobLogEntities != null) {
            for(JobLogEntity jobLog : jobLogEntities) {
                jobLog.setJobStatus(status);
                jobLog.setCompletedOn(timestamp);
                jobLog.setExecutionTime(HelperClass.calculateTimeDuration(jobLog.getInitiatedOn(),timestamp));
                jobLogRepo.save(jobLog);
            }
        }
    }

    public String getTestHistory() {
        List<TestResultEntity> testResultQueryDTOS = testResultRepo.findAll();
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode finalDataArray = objectMapper.createArrayNode();
        ObjectNode response = objectMapper.createObjectNode();
        TreeMap<String, List<List<String>>> sortedResultMap = getStringListTreeMap(testResultQueryDTOS);
        for (Map.Entry<String, List<List<String>>> testHistoryMapEntry : sortedResultMap.entrySet()) {
            ObjectNode timeStampNode = objectMapper.createObjectNode();
            List<List<String>> tcHistList = testHistoryMapEntry.getValue();
            ArrayNode dataArray = objectMapper.createArrayNode();
            for (List<String> tcData : tcHistList) {
                ObjectNode dataNode = objectMapper.createObjectNode();
                dataNode.put("testCaseName", tcData.get(0));
                dataNode.put("executedOn", tcData.get(1));
                dataNode.put("testStatus", tcData.get(2));
                dataNode.put("referenceNumber", tcData.get(3));
                dataNode.put("transactionNumber", tcData.get(4));
                dataNode.put("executedBy", tcData.get(5));
                dataNode.put("product", tcData.get(8));
                dataNode.put("transactionCd", tcData.get(9));
                dataNode.put("testResult", tcData.get(10));
                dataNode.put("jobId", tcData.get(11));
                dataArray.add(dataNode);
            }
            timeStampNode.set(testHistoryMapEntry.getKey(), dataArray);
            finalDataArray.add(timeStampNode);
        }
        response.set("historydata", finalDataArray);
        return response.toString();
    }

    private static TreeMap<String, List<List<String>>> getStringListTreeMap(List<TestResultEntity> testResultQueryDTOS) {
        Map<String, List<List<String>>> testHistoryMap = new HashMap<>();
        for (TestResultEntity entity : testResultQueryDTOS) {
            List<List<String>> tcHistList;
            List<String> tcData = new ArrayList<>();
            String keyCd = entity.getJobId();
            if (testHistoryMap.containsKey(keyCd)) {
                tcHistList = testHistoryMap.get(keyCd);
            } else {
                tcHistList = new ArrayList<>();
            }
            tcData.add(entity.getTestCaseName());
            tcData.add(entity.getExecutedOn());
            tcData.add(entity.getTestStatus());
            tcData.add(entity.getReferenceNumber());
            tcData.add(entity.getTransactionNumber());
            tcData.add(entity.getExecutedBy());
            tcData.add(entity.getCarrierId());
            tcData.add(entity.getStateCd());
            tcData.add(entity.getLob());
            tcData.add(entity.getTransactionCd());
            tcData.add(entity.getTestResult());
            tcData.add(entity.getJobId());
            tcHistList.add(tcData);
            testHistoryMap.put(keyCd, tcHistList);
        }
        TreeMap<String, List<List<String>>> sortedResultMap = new TreeMap<>(Collections.reverseOrder());
        sortedResultMap.putAll(testHistoryMap);
        return sortedResultMap;
    }

    public String getAllJobLogs() {
        List<JobLogEntity> jobLogEntity = jobLogRepo.findAll();
        jobLogEntity.sort(Comparator.comparing(JobLogEntity::getInitiatedOn).reversed());
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode dataArray = objectMapper.createArrayNode();
        ObjectNode response = objectMapper.createObjectNode();
        for (JobLogEntity entity : jobLogEntity) {
            ObjectNode dataNode = objectMapper.createObjectNode();
            dataNode.put("jobId", entity.getJobId());
            dataNode.put("product", entity.getProduct());
            dataNode.put("jobStatus", entity.getJobStatus());
            dataNode.put("initiatedBy", entity.getInitiatedBy());
            dataNode.put("initiatedOn", entity.getInitiatedOn());
            dataNode.put("completedOn", entity.getCompletedOn());
            dataArray.add(dataNode);
        }
        response.set("joblog", dataArray);
        return response.toString();
    }
    public String getJobLog(String carrierId, String stateCd, String product) {
        List<JobLogEntity> jobLogEntity = jobLogRepo.getJobLogForCarrierStateProduct(carrierId,stateCd,product);
        jobLogEntity.sort(Comparator.comparing(JobLogEntity::getInitiatedOn).reversed());
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode dataArray = objectMapper.createArrayNode();
        ObjectNode response = objectMapper.createObjectNode();
        for (JobLogEntity entity : jobLogEntity) {
            ObjectNode dataNode = objectMapper.createObjectNode();
            dataNode.put("jobId", entity.getJobId());
            dataNode.put("product", entity.getProduct());
            dataNode.put("jobStatus", entity.getJobStatus());
            dataNode.put("initiatedBy", entity.getInitiatedBy());
            dataNode.put("initiatedOn", entity.getInitiatedOn());
            dataNode.put("completedOn", entity.getCompletedOn());
            dataArray.add(dataNode);
        }
        response.set("joblog", dataArray);
        return response.toString();
    }

    public String nextJobId() {
        JobLogDTO jobLogEntity = getMaxId();
        int maxJobId = jobLogEntity.getId();
        maxJobId++;
        String formattedJobId = String.format("%02d", maxJobId);
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode response = objectMapper.createObjectNode();
        ObjectNode dataNode = objectMapper.createObjectNode();
        dataNode.put("jobId", "JOBID-" + formattedJobId);

        response.set("nextjobid", dataNode);
        return response.toString();
    }

    public String nextJobIdInternal() {
        JobLogDTO jobLogEntity = getMaxId();
        int maxJobId = jobLogEntity.getId();
        maxJobId++;
        String formattedJobId = String.format("%02d", maxJobId);
        return "JOBID-" + formattedJobId;
    }

    public ObjectNode fetchLastRunTCResults(String carrierId, String stateCd, String product) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        List<TestCaseDetailDTO> testCasesList = testCaseRepo.getAllUniqueTestCaseID(carrierId, stateCd, product);
        ArrayNode dataArray = objectMapper.createArrayNode();
        if (testCasesList.isEmpty()) {
            response.put("message", "Test Data Table is empty for carrier : " + carrierId + ", state : " + stateCd + ", product : " + product);
        } else {
            response.put("message", "Test Cases retrieved");
        }
        for (TestCaseDetailDTO testCase : testCasesList) {
            List<TestResultQueryDTO> testResultQueryDTOS = testResultRepo.getFetchTestCaseByID(testCase.getTestCaseId());
            if (testResultQueryDTOS.isEmpty()) {
                ObjectNode dataNode = objectMapper.createObjectNode();
                dataNode.put("testCaseSelected", false);
                dataNode.put("testCaseId", testCase.getTestCaseId());
                dataNode.put("testCaseName", testCase.getTestCaseName());
                dataNode.put("executedOn", "");
                dataNode.put("testStatus", "");
                dataNode.put("referenceNumber", "");
                dataNode.put("transactionNumber", "");
                dataNode.put("executedBy", "");
                dataNode.put("testResult", "");
                dataNode.put("transactionCd", "");
                dataNode.put("jobId", "");
                dataArray.add(dataNode);
            } else {
                for (TestResultQueryDTO entity : testResultQueryDTOS) {
                    ObjectNode dataNode = objectMapper.createObjectNode();
                    dataNode.put("testCaseSelected", false);
                    dataNode.put("testCaseId", entity.getTestCaseId());
                    dataNode.put("testCaseName", entity.getTestCaseName());
                    dataNode.put("executedOn", entity.getExecutedOn());
                    dataNode.put("testStatus", entity.getTestStatus());
                    dataNode.put("referenceNumber", entity.getReferenceNumber());
                    dataNode.put("transactionNumber", entity.getTransactionNumber());
                    dataNode.put("executedBy", entity.getExecutedBy());
                    dataNode.put("testResult", entity.getTestResult());
                    dataNode.put("transactionCd", entity.getTransactionCd());
                    dataNode.put("jobId", entity.getJobId());
                    dataArray.add(dataNode);
                }
            }
        }
        response.set("recentruntestdata", dataArray);
        return response;
    }
}
