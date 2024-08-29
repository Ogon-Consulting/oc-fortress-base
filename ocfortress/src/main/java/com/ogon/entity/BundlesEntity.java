package com.ogon.entity;

import jakarta.persistence.*;

@Entity
@Table(name="bundles")
public class BundlesEntity {
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
    @Column(name = "bundlename")
    private String bundleName;
    @Column(name = "description")
    private String description;
    @Column(name = "bundlemapperid")
    private int bundleMapperId;
    @Column(name = "statuscd")
    private String statusCd;
    @Column(name = "recentjobid")
    private String recentJobId;
    @Column(name="lastexecutedon")
    private String lastExecutedOn;
    @Column(name="lastexecutionresult")
    private String lastExecutionResult;

    @Column(name="lastexecutedby")
    private String lastExecutedBy;

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

    public String getBundleName() {
        return bundleName;
    }

    public void setBundleName(String bundleName) {
        this.bundleName = bundleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBundleMapperId() {
        return bundleMapperId;
    }

    public void setBundleMapperId(int bundleMapperId) {
        this.bundleMapperId = bundleMapperId;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public String getRecentJobId() {
        return recentJobId;
    }

    public void setRecentJobId(String recentJobId) {
        this.recentJobId = recentJobId;
    }

    public String getLastExecutedOn() {
        return lastExecutedOn;
    }

    public void setLastExecutedOn(String lastExecutedOn) {
        this.lastExecutedOn = lastExecutedOn;
    }

    public String getLastExecutedBy() {
        return lastExecutedBy;
    }

    public void setLastExecutedBy(String lastExecutedBy) {
        this.lastExecutedBy = lastExecutedBy;
    }

    public String getLastExecutionResult() {
        return lastExecutionResult;
    }

    public void setLastExecutionResult(String lastExecutionResult) {
        this.lastExecutionResult = lastExecutionResult;
    }
}
