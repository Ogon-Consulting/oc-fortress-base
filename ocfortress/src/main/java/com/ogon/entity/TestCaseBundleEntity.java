package com.ogon.entity;

import jakarta.persistence.*;

@Entity
@Table(name="bundledtestcases")
public class TestCaseBundleEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "bundlemapperId")
    private int bundleMapperId;
    @Column(name = "testcaseid")
    private String testCaseId;
    @Column(name = "testcasename")
    private String testCaseName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBundleMapperId() {
        return bundleMapperId;
    }

    public void setBundleMapperId(int bundleMapperId) {
        this.bundleMapperId = bundleMapperId;
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
}
