package com.ogon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "testresult")
@Data
@NoArgsConstructor
@AllArgsConstructor
    public class TestResultEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "carrierid")
    private String carrierId;
    @Column(name = "statecd")
    private String stateCd;
    @Column(name = "lob")
    private String lob;
    @Column(name = "testCaseId")
    private String testCaseId;
    @Column(name = "testcasename")
    private String testCaseName;
    @Column(name = "transactioncd")
    private String transactionCd;
    @Column(name = "referencenumber")
    private String referenceNumber;
    @Column(name = "transactionnumber")
    private String transactionNumber;
    @Column(name = "statuscd")
    private String testStatus;
    @Column(name = "executedon")
    private String executedOn;
    @Column(name = "executedby")
    private String executedBy;
    @Column(name = "testresult")
    private String testResult;
    @Column(name = "jobid")
    private String jobId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        this.referenceNumber = referenceNumber;
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

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
}
