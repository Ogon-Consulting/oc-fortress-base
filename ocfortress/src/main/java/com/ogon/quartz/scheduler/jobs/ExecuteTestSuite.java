package com.ogon.quartz.scheduler.jobs;

import com.ogon.apidatamapper.BundleInputData;
import com.ogon.apidatamapper.InputData;
import com.ogon.common.utility.ApplicationContextUtils;
import com.ogon.entity.JobLogEntity;
import com.ogon.service.BundlesService;
import com.ogon.service.TestResultService;
import com.ogon.umv.controller.TestControllerSuite;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.tinylog.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExecuteTestSuite extends QuartzJobBean {
    @Override
    protected void executeInternal(@NonNull JobExecutionContext jobExecutionContext) {

        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
        TestResultService testResultService = appCtx.getBean("testResultService", TestResultService.class);
        String jobId = testResultService.nextJobIdInternal();
        String result;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss z");
        try {
            JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
            String lob = jobDataMap.getString("lob");
            String category = jobDataMap.getString("category");
            String executedBy = jobDataMap.getString("executedBy");
            String formattedDate = sdf.format(new Date());

            JobLogEntity jobLogEntity = new JobLogEntity();
            jobLogEntity.setJobId(jobId);
            jobLogEntity.setCarrierId(jobDataMap.getString("carrierId"));
            jobLogEntity.setStateCd(jobDataMap.getString("stateCd"));
            jobLogEntity.setProduct(lob);
            jobLogEntity.setInitiatedBy(executedBy);
            jobLogEntity.setInitiatedOn(formattedDate);
            jobLogEntity.setJobStatus("Running");
            testResultService.addJobLog(jobLogEntity);

            if ("Bundles".equalsIgnoreCase(category)) {
              result = executeBundles(jobDataMap, jobId);
            } else {
              result = executeTestCases(jobDataMap, jobId);
            }

            String jobStatus;
            if (result.contains("Failed")) {
                jobStatus = "Completed With Errors";
            } else {
                jobStatus = "Completed";
            }
            testResultService.updateJobCompletedOnAndStatus(sdf.format(new Date()), jobId, jobStatus);
            jobDataMap.put("jobId",jobId);
        } catch (Exception e) {
            testResultService.updateJobCompletedOnAndStatus(sdf.format(new Date()), jobId, "Completed With Exceptions");

        }
    }

    private String executeBundles(JobDataMap jobDataMap, String nextJobId){
        try {
            ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
            BundlesService bundlesService = appCtx.getBean("bundlesService", BundlesService.class);
            String carrierId = jobDataMap.getString("carrierId");
            String stateCd = jobDataMap.getString("stateCd");
            String product = jobDataMap.getString("lob");
            String executedBy = jobDataMap.getString("executedBy");
            String loginUserId = jobDataMap.getString("loginUserId");
            String loginPassword = jobDataMap.getString("loginPassword");
            String[] scheduledBundlesList = (String[]) jobDataMap.get("scheduledItems");
            int[] bundleIds = new int[scheduledBundlesList.length];
            for (int i = 0; i < scheduledBundlesList.length; i++) {
                bundleIds[i] = Integer.parseInt(scheduledBundlesList[i]);
            }

            BundleInputData bundleInputData = new BundleInputData();
            bundleInputData.setCarrierId(carrierId);
            bundleInputData.setStateCd(stateCd);
            bundleInputData.setProduct(product);
            if(executedBy.isEmpty()){
                executedBy = "admin";
            }
            bundleInputData.setExecutedBy(executedBy);
            bundleInputData.setNextJobId(nextJobId);
            bundleInputData.setBundleIds(bundleIds);
            bundleInputData.setLoginUserId(loginUserId);
            bundleInputData.setLoginPassword(loginPassword);

            return bundlesService.executeScheduledBundle(bundleInputData);

        } catch (Exception e) {
            Logger.error(e.getMessage());
            return "Completed With Exceptions";
        }
    }

    private String executeTestCases(JobDataMap jobDataMap, String nextJobId){
        try {
            String carrierId = jobDataMap.getString("carrierId");
            String stateCd = jobDataMap.getString("stateCd");
            String product = jobDataMap.getString("lob");
            String executedBy = jobDataMap.getString("executedBy");
            String loginUserId = jobDataMap.getString("loginUserId");
            String loginPassword = jobDataMap.getString("loginPassword");
            String[] scheduledTestCasesList = (String[]) jobDataMap.get("scheduledItems");

            InputData inputData = new InputData();
            inputData.setCarrierId(carrierId);
            inputData.setStateCd(stateCd);
            inputData.setProduct(product);
            inputData.setTestCaseList(scheduledTestCasesList);
            inputData.setExecutedOn("");
            inputData.setNextJobId(nextJobId);
            inputData.setExecutedBy(executedBy);
            inputData.setLoginUserId(loginUserId);
            inputData.setLoginPassword(loginPassword);
            if (inputData.getExecutedBy().isEmpty()) {
                inputData.setExecutedBy("admin");
            }
            return new TestControllerSuite().runTestSuite(inputData);
        } catch (Exception e) {
            Logger.error(e.getMessage());
            return "Completed With Exceptions";
        }
    }
}
