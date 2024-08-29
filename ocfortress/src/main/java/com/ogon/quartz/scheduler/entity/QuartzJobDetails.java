package com.ogon.quartz.scheduler.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "QRTZ_JOB_DETAILS")
@IdClass(QuartzJobDetailsId.class)
public class QuartzJobDetails {
    @Id
    private String schedName;
    @Id
    private String jobName;
    @Id
    private String jobGroup;
    private String description;
    private String jobClassName;
    private Boolean isDurable;
    private Boolean isNonconcurrent;
    private Boolean isUpdateData;
    private Boolean requestsRecovery;

    public String getSchedName() {
        return schedName;
    }

    public void setSchedName(String schedName) {
        this.schedName = schedName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public Boolean getDurable() {
        return isDurable;
    }

    public void setDurable(Boolean durable) {
        isDurable = durable;
    }

    public Boolean getNonconcurrent() {
        return isNonconcurrent;
    }

    public void setNonconcurrent(Boolean nonconcurrent) {
        isNonconcurrent = nonconcurrent;
    }

    public Boolean getUpdateData() {
        return isUpdateData;
    }

    public void setUpdateData(Boolean updateData) {
        isUpdateData = updateData;
    }

    public Boolean getRequestsRecovery() {
        return requestsRecovery;
    }

    public void setRequestsRecovery(Boolean requestsRecovery) {
        this.requestsRecovery = requestsRecovery;
    }
}

