package com.ogon.common.utility;

import java.util.HashMap;
import java.util.Map;

public class TestCaseList {
    private Map<String,String> testCasesMap = new HashMap<>();
    public TestCaseList() {
        testCasesMap.put(AppVariables.ADD_CUSTOMER,"Add Customer");
        testCasesMap.put(AppVariables.ADD_PRODUCER,"Add Producer");
        testCasesMap.put(AppVariables.ADD_QUOTE,"Add Quote");
        testCasesMap.put(AppVariables.NEW_BUSINESS,"New Business");
        testCasesMap.put(AppVariables.ADD_RISK_ENDORSEMENT,"Endorsement - Add Risk");
        testCasesMap.put(AppVariables.INSURED_CANCELLATION,"Cancellation Insured");
        testCasesMap.put(AppVariables.REINSTATEMENT,"Reinstatement");
        testCasesMap.put(AppVariables.PAY_PLAN_CHANGE,"PayPlan Change");
        testCasesMap.put(AppVariables.MAKE_PAYMENT,"Make Payment");
        //testCasesMap.put("TC_ENDO_ADD_COV","Endorsement - Add Coverage");
        //testCasesMap.put("TC_RENEWAL_MANUAL","Renewal - Manual");
    }

    public Map<String, String> getTestCasesMap() {
        return testCasesMap;
    }

    public void setTestCasesMap(Map<String, String> testCasesMap) {
        this.testCasesMap = testCasesMap;
    }
}
