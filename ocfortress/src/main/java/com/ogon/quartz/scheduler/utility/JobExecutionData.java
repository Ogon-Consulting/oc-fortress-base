package com.ogon.quartz.scheduler.utility;

import java.util.Date;

public class JobExecutionData {
    private final String jobKey;
    private String status;
    private final Date startTime;
    private long completeTime;

    public JobExecutionData(String jobKey, String status, Date startTime, long completeTime) {
        this.jobKey = jobKey;
        this.status = status;
        this.startTime = startTime;
        this.completeTime = completeTime;
    }

    public String getJobKey() {
        return jobKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public long getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(long completeTime) {
        this.completeTime = completeTime;
    }
}
