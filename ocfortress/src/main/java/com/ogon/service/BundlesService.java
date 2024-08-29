package com.ogon.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ogon.apidatamapper.BundleDataMapper;
import com.ogon.apidatamapper.BundleInputData;
import com.ogon.apidatamapper.BundledTestCases;
import com.ogon.apidatamapper.InputData;
import com.ogon.common.utility.ApplicationContextUtils;
import com.ogon.dto.TestCaseBundleDetailsDTO;
import com.ogon.dto.TestCaseBundleIDDTO;
import com.ogon.entity.BundlesEntity;
import com.ogon.entity.JobLogEntity;
import com.ogon.entity.TestCaseBundleEntity;
import com.ogon.repository.BundlesRepo;
import com.ogon.repository.TestCaseBundleRepo;
import com.ogon.umv.controller.TestControllerSuite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BundlesService {

    @Autowired
    private BundlesRepo bundlesRepo;

    @Autowired
    private TestCaseBundleRepo testCaseBundleRepo;

    public ResponseEntity<String> getAllBundlesForCarrierStateProduct(String carrierId, String stateCd, String lob) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            ArrayNode bundleDataArray = objectMapper.createArrayNode();
            List<BundlesEntity> bundlesEntities = bundlesRepo.getBundlesForCarrierStateProduct(carrierId, stateCd, lob);
            for (BundlesEntity bundlesEntity : bundlesEntities) {
                ObjectNode bundleDataNode = objectMapper.createObjectNode();
                bundleDataNode.put("bundleId", bundlesEntity.getId());
                bundleDataNode.put("bundleName", bundlesEntity.getBundleName());
                bundleDataNode.put("description", bundlesEntity.getDescription());
                bundleDataNode.put("statusCd", bundlesEntity.getStatusCd());
                ArrayNode testDataArray = objectMapper.createArrayNode();
                List<TestCaseBundleDetailsDTO> testCaseBundleDetailsDTOS = testCaseBundleRepo.getTestCaseDetailsByBundleMapperId(bundlesEntity.getBundleMapperId());
                for (TestCaseBundleDetailsDTO testCaseBundleDetailsDTO : testCaseBundleDetailsDTOS) {
                    ObjectNode testDataNode = objectMapper.createObjectNode();
                    testDataNode.put("testCaseId", testCaseBundleDetailsDTO.getTestCaseId());
                    testDataNode.put("testCaseName", testCaseBundleDetailsDTO.getTestCaseName());
                    testDataArray.add(testDataNode);
                }
                bundleDataNode.set("testCaseBundle", testDataArray);
                bundleDataArray.add(bundleDataNode);
            }
            response.set("bundles", bundleDataArray);
            response.put("status", "success");
            response.put("message", "Test Case Bundles retrieved successfully");
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", "failed");
            response.put("message", "Test Case Bundles fetch unsuccessful");
            return new ResponseEntity<>(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> getAllActiveBundlesForCarrierStateProduct(String carrierId, String stateCd, String lob) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            ArrayNode bundleDataArray = objectMapper.createArrayNode();
            List<BundlesEntity> bundlesEntities = bundlesRepo.getActiveBundlesForCarrierStateProduct(carrierId, stateCd, lob);
            for (BundlesEntity bundlesEntity : bundlesEntities) {
                ObjectNode bundleDataNode = objectMapper.createObjectNode();
                bundleDataNode.put("bundleId", bundlesEntity.getId());
                bundleDataNode.put("bundleName", bundlesEntity.getBundleName());
                bundleDataNode.put("description", bundlesEntity.getDescription());
                bundleDataNode.put("statusCd", bundlesEntity.getStatusCd());
                bundleDataNode.put("recentJobId", bundlesEntity.getRecentJobId());
                bundleDataNode.put("lastExecutionResult", bundlesEntity.getLastExecutionResult());
                bundleDataNode.put("lastExecutedBy", bundlesEntity.getLastExecutedBy());
                bundleDataNode.put("lastExecutedOn", bundlesEntity.getLastExecutedOn());
                ArrayNode testDataArray = objectMapper.createArrayNode();
                List<TestCaseBundleDetailsDTO> testCaseBundleDetailsDTOS = testCaseBundleRepo.getTestCaseDetailsByBundleMapperId(bundlesEntity.getBundleMapperId());
                for (TestCaseBundleDetailsDTO testCaseBundleDetailsDTO : testCaseBundleDetailsDTOS) {
                    ObjectNode testDataNode = objectMapper.createObjectNode();
                    testDataNode.put("testCaseId", testCaseBundleDetailsDTO.getTestCaseId());
                    testDataNode.put("testCaseName", testCaseBundleDetailsDTO.getTestCaseName());
                    testDataArray.add(testDataNode);
                }
                bundleDataNode.set("testCaseBundle", testDataArray);
                bundleDataArray.add(bundleDataNode);
            }
            response.set("bundles", bundleDataArray);
            response.put("status", "success");
            response.put("message", "Test Case Bundles retrieved successfully");
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", "failed");
            response.put("message", "Test Case Bundles fetch unsuccessful");
            return new ResponseEntity<>(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public BundlesEntity getBundleById(int id) {
        BundlesEntity bundlesEntity = new BundlesEntity();
        try {
            bundlesEntity = bundlesRepo.findById(id);
            return bundlesEntity;
        } catch (Exception e) {
            Logger.error(e);
        }
        return bundlesEntity;
    }

    //Future Implementation
    public ResponseEntity<String> updateBundleByID(String id, String testCaseBundle) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            JsonNode jsonNode = objectMapper.readTree(testCaseBundle);
            //int updateCount = bundlesRepo.updateBundle(Integer.parseInt(id),jsonNode.toString());
//            if(updateCount > 0) {
//                response.put("status", "success");
//                response.put("message", "Test Case Bundle updated successfully");
//            }else{
//                response.put("status", "failed");
//                response.put("message", "Test Case Bundle is not found");
//            }
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", "failed");
            response.put("message", "Test Case Bundle Update Failed");
            return new ResponseEntity<>(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteBundleById(int bundleId) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            String bundleName = bundlesRepo.findById(bundleId).getBundleName();
            int deleteBundleCount = bundlesRepo.deleteBundle(bundleId);
            if (deleteBundleCount > 0) {
                response.put("status", "success");
                response.put("message", "Bundle '" + bundleName + "' Deleted");
            } else {
                response.put("status", "failed");
                response.put("message", "Bundle '" + bundleName + "' not found. Possibly got delete already");
            }
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", "failed");
            response.put("message", "Bundle Delete Failed with Exceptions");
            return new ResponseEntity<>(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> addNewBundle(BundleDataMapper bundleDataMapper) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            BundlesEntity bundlesEntity = getBundlesEntity(bundleDataMapper);
            bundlesRepo.save(bundlesEntity);
            response.put("status", "success");
            response.put("message", "Bundle '" + bundlesEntity.getDescription() + "' Added");
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", "failed");
            response.put("message", "Bundle Add Failed");
            return new ResponseEntity<>(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private BundlesEntity getBundlesEntity(BundleDataMapper bundleDataMapper) {
        BundlesEntity bundlesEntity = new BundlesEntity();
        bundlesEntity.setCarrierId(bundleDataMapper.getCarrierId());
        bundlesEntity.setStateCd(bundleDataMapper.getStateCd());
        bundlesEntity.setLob(bundleDataMapper.getLob());
        bundlesEntity.setBundleName(bundleDataMapper.getBundleName());
        bundlesEntity.setDescription(bundleDataMapper.getDescription());
        bundlesEntity.setBundleMapperId(addTestCasesBundle(bundleDataMapper.getTestCaseBundle()));
        bundlesEntity.setStatusCd("ACTIVE");
        return bundlesEntity;
    }

    public int addTestCasesBundle(BundledTestCases[] testCases) {
        TestCaseBundleIDDTO testCaseBundleIDDTO = testCaseBundleRepo.getMaxBundleMapperId();
        int maxBundleMapperId = testCaseBundleIDDTO.getBundleMapperId();
        int nextBundleMapperId = ++maxBundleMapperId;
        for (BundledTestCases testCase : testCases) {
            TestCaseBundleEntity testCaseBundleEntity = new TestCaseBundleEntity();
            testCaseBundleEntity.setTestCaseId(testCase.getTestCaseId());
            testCaseBundleEntity.setTestCaseName(testCase.getTestCaseName());
            testCaseBundleEntity.setBundleMapperId(nextBundleMapperId);
            testCaseBundleRepo.save(testCaseBundleEntity);
        }
        return nextBundleMapperId;
    }

    public String executeScheduledBundle(BundleInputData bundleInputData) {
        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
        TestResultService testResultService = appCtx.getBean("testResultService", TestResultService.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss z");
        String jobId = bundleInputData.getNextJobId();
        String executedBy = bundleInputData.getExecutedBy();

        try {
            String formattedDate = sdf.format(new Date());
            InputData inputData = new InputData();
            inputData.setNextJobId(jobId);
            inputData.setCarrierId(bundleInputData.getCarrierId());
            inputData.setProduct(bundleInputData.getProduct());
            inputData.setStateCd(bundleInputData.getStateCd());
            inputData.setExecutedBy(executedBy);
            inputData.setLoginUserId(bundleInputData.getLoginUserId());
            inputData.setLoginPassword(bundleInputData.getLoginPassword());
            if (inputData.getExecutedBy().isEmpty()) {
                inputData.setExecutedBy("admin");
            }
            inputData.setExecutedOn(formattedDate);
            String jobStatus = "";

            int[] bundleIds = bundleInputData.getBundleIds();
            for (int bundleId : bundleIds) {
                BundlesEntity bundlesEntity = getBundleById(bundleId);
                List<TestCaseBundleDetailsDTO> testCaseBundleDetailsDTOS = testCaseBundleRepo.getTestCaseDetailsByBundleMapperId(bundlesEntity.getBundleMapperId());
                List<String> testCaseList = new ArrayList<>();
                for (TestCaseBundleDetailsDTO testCaseBundleDetailsDTO : testCaseBundleDetailsDTOS) {
                    testCaseList.add(testCaseBundleDetailsDTO.getTestCaseId());
                }
                inputData.setTestCaseList(testCaseList.toArray(new String[0]));
                String result = new TestControllerSuite().runTestSuite(inputData);
                if (result.contains("Failed") && !("Completed With Errors").equalsIgnoreCase(jobStatus)) {
                    jobStatus = "Completed With Errors";
                } else {
                    jobStatus = "Completed";
                }
                updateBundleJobId(bundleId, jobId, result, executedBy, formattedDate);
            }
            return jobStatus;
        } catch (Exception e) {
            Logger.error(e);
            return "Completed With Exceptions";
        }
    }

    public ResponseEntity<String> executeBundleById(BundleInputData bundleInputData) {
        ObjectNode response;
        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
        TestResultService testResultService = appCtx.getBean("testResultService", TestResultService.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss z");
        String jobId = bundleInputData.getNextJobId();
        String executedBy = bundleInputData.getExecutedBy();
        int currentBundleId;
        try {
            JobLogEntity jobLogEntity = new JobLogEntity();
            String formattedDate = sdf.format(new Date());
            jobLogEntity.setJobId(jobId);
            jobLogEntity.setCarrierId(bundleInputData.getCarrierId());
            jobLogEntity.setStateCd(bundleInputData.getStateCd());
            jobLogEntity.setProduct(bundleInputData.getProduct());
            if (executedBy.isEmpty()) {
                executedBy = "admin";
            }
            jobLogEntity.setInitiatedBy(executedBy);
            jobLogEntity.setInitiatedOn(formattedDate);
            jobLogEntity.setJobStatus("Running");
            testResultService.addJobLog(jobLogEntity);
            InputData inputData = new InputData();
            inputData.setNextJobId(jobId);
            inputData.setCarrierId(bundleInputData.getCarrierId());
            inputData.setProduct(bundleInputData.getProduct());
            inputData.setStateCd(bundleInputData.getStateCd());
            inputData.setExecutedBy(executedBy);
            inputData.setExecutedOn(formattedDate);
            String jobStatus = "";

            int[] bundleIds = bundleInputData.getBundleIds();
            for (int bundleId : bundleIds) {
                currentBundleId = bundleId;
                BundlesEntity bundlesEntity = getBundleById(bundleId);
                List<TestCaseBundleDetailsDTO> testCaseBundleDetailsDTOS = testCaseBundleRepo.getTestCaseDetailsByBundleMapperId(bundlesEntity.getBundleMapperId());
                List<String> testCaseList = new ArrayList<>();
                for (TestCaseBundleDetailsDTO testCaseBundleDetailsDTO : testCaseBundleDetailsDTOS) {
                    testCaseList.add(testCaseBundleDetailsDTO.getTestCaseId());
                }
                inputData.setTestCaseList(testCaseList.toArray(new String[0]));

                String result = new TestControllerSuite().runTestSuite(inputData);
                if (result.contains("Failed") && !("Completed With Errors").equalsIgnoreCase(jobStatus)) {
                    jobStatus = "Completed With Errors";
                } else {
                    jobStatus = "Completed";
                }
                updateBundleJobId(currentBundleId, jobId, jobStatus, executedBy, formattedDate);
            }
            testResultService.updateJobCompletedOnAndStatus(sdf.format(new Date()), jobId, jobStatus);
            response = testResultService.fetchLastRunTCResults(inputData.getCarrierId(), inputData.getStateCd(), inputData.getProduct());
            response.put("status", "Job '" + jobId + "' " + jobStatus);
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception e) {
            Logger.error(e);
            response = testResultService.fetchLastRunTCResults(bundleInputData.getCarrierId(), bundleInputData.getStateCd(), bundleInputData.getProduct());
            response.put("status", "Job '" + jobId + "' " + "Completed With Exceptions");
            String jsonResponse = response.toString();
            testResultService.updateJobCompletedOnAndStatus(sdf.format(new Date()), jobId, "Completed With Exceptions");
            return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void updateBundleJobId(int bundleId, String jobId, String result, String executedBy, String executedOn) {
        try {
            if (bundleId != 0) {
                bundlesRepo.updateBundleJobId(bundleId, jobId, result, executedBy, executedOn);
            }
        } catch (Exception e) {
            Logger.error(e);
        }
    }
}
