package com.ogon.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestResultQueryDTO {
    private String testCaseId;
    public String getTestCaseId() {
        return testCaseId;
    }
    public void setTestCaseId(String testCaseId) {
        this.testCaseId = testCaseId;
    }
    public String getTestCaseName() {
        return testCaseName;
    }
    public void setTestCaseName(String testCaseName) {
        this.testCaseName = testCaseName;
    }
    public String getTransactionCd() {
        return transactionCd;
    }
    public void setTransactionCd(String transactionCd) {
        this.transactionCd = transactionCd;
    }
    public String getReferenceNumber() {
        return referenceNumber;
    }
    public void setReferenceNumber(String referenceNumber) {
        referenceNumber = referenceNumber;
    }
    public String getTransactionNumber() {
        return transactionNumber;
    }
    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }
    public String getTestStatus() {
        return testStatus;
    }
    public void setTestStatus(String testStatus) {
        this.testStatus = testStatus;
    }
    public String getExecutedOn() {
        return executedOn;
    }
    public void setExecutedOn(String executedOn) {
        this.executedOn = executedOn;
    }
    public String getExecutedBy() {
        return executedBy;
    }
    public void setExecutedBy(String executedBy) {
        this.executedBy = executedBy;
    }
    private String carrierId;
    private String stateCd;
    private String lob;
    private String testCaseName;
    private String transactionCd;
    private String referenceNumber;
    private String transactionNumber;
    private String testStatus;
    private String executedOn;
    private String executedBy;

    private String testResult;

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    private String jobId;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(String carrierId) {
        this.carrierId = carrierId;
    }

    public String getStateCd() {
        return stateCd;
    }

    public void setStateCd(String stateCd) {
        this.stateCd = stateCd;
    }

    public String getLob() {
        return lob;
    }

    public void setLob(String lob) {
        this.lob = lob;
    }
}
