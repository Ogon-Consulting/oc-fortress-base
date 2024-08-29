package com.ogon.quartz.scheduler.payload;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class SchedulerInputData {
    private String carrierId;
    private String stateCd;
    private String lob;
    private String category;
    private LocalDateTime scheduleDate;
    private ZoneId zoneTime;
    private String[] scheduleItemsList;
    private String executedBy;
    private String isCronscheduler;
    private String cronschedulerConfig;
    private String loginUserId;
    private String loginPassword;

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

    public LocalDateTime getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDateTime scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public ZoneId getZoneTime() {
        return zoneTime;
    }

    public void setZoneTime(ZoneId zoneTime) {
        this.zoneTime = zoneTime;
    }

    public String[] getScheduleItemsList() {
        return scheduleItemsList;
    }

    public void setScheduleItemsList(String[] scheduleItemsList) {
        this.scheduleItemsList = scheduleItemsList;
    }

    public String getExecutedBy() {
        return executedBy;
    }

    public void setExecutedBy(String executedBy) {
        this.executedBy = executedBy;
    }

    public String getIsCronscheduler() {
        return isCronscheduler;
    }

    public void setIsCronscheduler(String isCronscheduler) {
        this.isCronscheduler = isCronscheduler;
    }

    public String getCronschedulerConfig() {
        return cronschedulerConfig;
    }

    public void setCronschedulerConfig(String cronschedulerConfig) {
        this.cronschedulerConfig = cronschedulerConfig;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
}
