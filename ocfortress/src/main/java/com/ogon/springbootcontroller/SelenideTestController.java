package com.ogon.springbootcontroller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ogon.common.utility.ApplicationContextUtils;
import com.ogon.service.UIFieldTestDataMapService;
import com.ogon.service.UserInfoService;
import com.ogon.umv.controller.TestControllerSuite;
import com.ogon.apidatamapper.InputData;
import com.ogon.entity.JobLogEntity;
import com.ogon.service.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(path = "/api/v1")
public class SelenideTestController {
    @Autowired
    private TestResultService testResultService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UIFieldTestDataMapService uiFieldTestDataMapService;
    @PostMapping("/initiate-test")
    public ResponseEntity<?> initiateTest(@RequestBody InputData inputData) {
        String result;
        String jobId = inputData.getNextJobId();
        JobLogEntity jobLogEntity = new JobLogEntity();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss z");
        try {
            String formattedDate = sdf.format(new Date());
            ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
            TestResultService testResultService = appCtx.getBean("testResultService", TestResultService.class);
            inputData.setExecutedOn(formattedDate);
            jobLogEntity.setCarrierId(inputData.getCarrierId());
            jobLogEntity.setStateCd(inputData.getStateCd());
            jobLogEntity.setProduct(inputData.getProduct());
            jobLogEntity.setJobId(jobId);
            if(inputData.getExecutedBy().isEmpty()){
                inputData.setExecutedBy("admin");
            }
            jobLogEntity.setInitiatedBy(inputData.getExecutedBy());
            jobLogEntity.setInitiatedOn(formattedDate);
            jobLogEntity.setJobStatus("Running");
            testResultService.addJobLog(jobLogEntity);

            result = new TestControllerSuite().runTestSuite(inputData);

            String jobStatus;
            if(result.contains("Failed")){
                jobStatus = "Completed With Errors";
            }else{
                jobStatus = "Completed";
            }

            testResultService.updateJobCompletedOnAndStatus(sdf.format(new Date()), jobId, jobStatus);

            ObjectNode response = testResultService.fetchLastRunTCResults(inputData.getCarrierId(), inputData.getStateCd(), inputData.getProduct());
            response.put("status", jobStatus);
            String jsonResponse = response.toString();
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }catch (Exception e){
            Logger.error(e);
            ObjectNode response = testResultService.fetchLastRunTCResults(inputData.getCarrierId(), inputData.getStateCd(), inputData.getProduct());
            response.put("status", "Completed With Exceptions");
            String jsonResponse = response.toString();
            testResultService.updateJobCompletedOnAndStatus(sdf.format(new Date()),jobId,"Completed With Exceptions");
            return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/gettestcases")
    public ResponseEntity<?> getTestCases(@RequestBody InputData inputData) {
        try {
            ObjectNode response = testResultService.fetchLastRunTCResults(inputData.getCarrierId(), inputData.getStateCd(), inputData.getProduct());
            response.put("status", "success");
            String jsonResponse = response.toString();
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }catch (Exception e){
            Logger.error(e);
            ObjectNode response = testResultService.fetchLastRunTCResults(inputData.getCarrierId(), inputData.getStateCd(), inputData.getProduct());
            response.put("status", "Completed With Exceptions");
            String jsonResponse = response.toString();
            return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
