package com.ogon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "uifieldtestdatamapentity")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDataEntity {

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
    @Column(name = "testcaseid")
    private String testCaseId;
    @Column(name = "testcasename")
    private String testCaseName;
    @Column(name = "uiname")
    private String uiName;
    @Column(name = "uisequence")
    private int uiSequence;
    @Column(name = "fieldsequence")
    private int fieldSequence;
    @Column(name = "fieldname")
    private String fieldName;
    @Column(name = "fieldid")
    private String fieldId;
    @Column(name = "fieldtype")
    private String fieldType;
    @Column(name = "defaultvalue")
    private String defaultValue;
    @Column(name = "showfield")
    private String showField;
    @Column(name = "readonly")
    private String readOnly;
    @Column(name = "validations")
    private String validations;
    @Column(name = "issleepneeded")
    private String isSleepNeeded;
    @Column(name = "sleeptime")
    private String sleepTime;
    @Column(name = "shouldwaitforcompletion")
    private String shouldWaitForCompletion;
    @Column(name = "shouldwaitforaddrverification")
    private String shouldWaitForAddrVerification;
    @Column(name = "addrverificationimgfield")
    private String addrVerificationImgField;
    @Column(name = "shouldpresstab")
    private String shouldPressTab;
    @Column(name = "drivername")
    private String driverName;

    @Column(name = "linkedfieldid")
    private String linkedFieldId;

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

    public String getUiName() {
        return uiName;
    }

    public void setUiName(String uiName) {
        this.uiName = uiName;
    }

    public int getUiSequence() {
        return uiSequence;
    }

    public void setUiSequence(int uiSequence) {
        this.uiSequence = uiSequence;
    }

    public int getFieldSequence() {
        return fieldSequence;
    }

    public void setFieldSequence(int fieldSequence) {
        this.fieldSequence = fieldSequence;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getShowField() {
        return showField;
    }

    public void setShowField(String showField) {
        this.showField = showField;
    }

    public String getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(String readOnly) {
        this.readOnly = readOnly;
    }

    public String getValidations() {
        return validations;
    }

    public void setValidations(String validations) {
        this.validations = validations;
    }

    public String getIsSleepNeeded() {
        return isSleepNeeded;
    }

    public void setIsSleepNeeded(String isSleepNeeded) {
        this.isSleepNeeded = isSleepNeeded;
    }

    public String getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(String sleepTime) {
        this.sleepTime = sleepTime;
    }

    public String getShouldWaitForCompletion() {
        return shouldWaitForCompletion;
    }

    public void setShouldWaitForCompletion(String shouldWaitForCompletion) {
        this.shouldWaitForCompletion = shouldWaitForCompletion;
    }

    public String getShouldWaitForAddrVerification() {
        return shouldWaitForAddrVerification;
    }

    public void setShouldWaitForAddrVerification(String shouldWaitForAddrVerification) {
        this.shouldWaitForAddrVerification = shouldWaitForAddrVerification;
    }

    public String getAddrVerificationImgField() {
        return addrVerificationImgField;
    }

    public void setAddrVerificationImgField(String addrVerificationImgField) {
        this.addrVerificationImgField = addrVerificationImgField;
    }

    public String getShouldPressTab() {
        return shouldPressTab;
    }

    public void setShouldPressTab(String shouldPressTab) {
        this.shouldPressTab = shouldPressTab;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getLinkedFieldId() {
        return linkedFieldId;
    }

    public void setLinkedFieldId(String linkedFieldId) {
        this.linkedFieldId = linkedFieldId;
    }
}
