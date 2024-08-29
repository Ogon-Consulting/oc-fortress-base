package com.ogon.quartz.scheduler.utility;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import java.util.concurrent.ConcurrentHashMap;

public class JobExecutionHelper implements JobListener {
    private ConcurrentHashMap<String, JobExecutionData> jobExecutionDetails = new ConcurrentHashMap<>();

    @Override
    public String getName() {
        return "GlobalJobListener";
    }
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        // Optional: handle if a job execution is vetoed
    }
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        String jobKey = context.getJobDetail().getKey().toString();
        jobExecutionDetails.put(jobKey, new JobExecutionData(jobKey, "RUNNING", context.getFireTime(), 0));
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        String jobKey = context.getJobDetail().getKey().toString();
        JobExecutionData detail = jobExecutionDetails.get(jobKey);
        if (detail != null) {
            detail.setStatus(jobException == null ? "COMPLETED" : "FAILED");
            detail.setCompleteTime(context.getJobRunTime());
        }
    }
    public ConcurrentHashMap<String, JobExecutionData> getJobExecutionDetails() {
        return jobExecutionDetails;
    }
}
