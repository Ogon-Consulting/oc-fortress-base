package com.ogon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseQueryDTO {
    private String testCaseId;
    private String testCaseName;
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
