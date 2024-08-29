package com.ogon.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UIFieldValueDataMapDTO {

    private String testCaseId;
    private String uiName;
    private int uiSequence;
    private String fieldName;
    private String fieldId;
    private int fieldSequence;
    private String defaultValue;
    private String readOnly;

    public String getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(String testCaseId) {
        this.testCaseId = testCaseId;
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

    public int getFieldSequence() {
        return fieldSequence;
    }

    public void setFieldSequence(int fieldSequence) {
        this.fieldSequence = fieldSequence;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(String readOnly) {
        this.readOnly = readOnly;
    }
}
