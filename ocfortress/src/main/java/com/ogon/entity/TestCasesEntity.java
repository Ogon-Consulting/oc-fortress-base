package com.ogon.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="testcases")
@Data
public class TestCasesEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "carrierid")
    private String carrierId;
    @Column(name = "statecd")
    private String stateCd;
    @Column(name = "lob")
    private String lob;
    @Column(name = "testCaseId")
    private String testCaseId;
    @Column(name = "testdriver")
    private String testDriver;
    @Column(name = "testcasename")
    private String testCaseName;
    @Column(name = "lastrunstatus")
    private String lastRunStatus;
    @Column(name = "Lastrundate")
    private String lastRunDate;
    @Column(name = "Lastrunby")
    private String lastRunBy;
    @Column(name = "Lastrunoutcome")
    private String lastRunOutcome;
    @Column(name = "lastjobid")
    private String lastJobID;

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

    public String getTestDriver() {
        return testDriver;
    }

    public void setTestDriver(String testDriver) {
        this.testDriver = testDriver;
    }

    public String getTestCaseName() {
        return testCaseName;
    }

    public void setTestCaseName(String testCaseName) {
        this.testCaseName = testCaseName;
    }

    public String getLastRunStatus() {
        return lastRunStatus;
    }

    public void setLastRunStatus(String lastRunStatus) {
        this.lastRunStatus = lastRunStatus;
    }

    public String getLastRunDate() {
        return lastRunDate;
    }

    public void setLastRunDate(String lastRunDate) {
        this.lastRunDate = lastRunDate;
    }

    public String getLastRunBy() {
        return lastRunBy;
    }

    public void setLastRunBy(String lastRunBy) {
        this.lastRunBy = lastRunBy;
    }

    public String getLastRunOutcome() {
        return lastRunOutcome;
    }

    public void setLastRunOutcome(String lastRunOutcome) {
        this.lastRunOutcome = lastRunOutcome;
    }

    public String getLastJobID() {
        return lastJobID;
    }

    public void setLastJobID(String lastJobID) {
        this.lastJobID = lastJobID;
    }
}
