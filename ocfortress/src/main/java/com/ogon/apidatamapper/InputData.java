package com.ogon.apidatamapper;


public class InputData {

    private String product="";
    private String effectDt="";
    private String stateCd="";
    private String executedBy;
    private String executedOn;
    private String nextJobId;
    private String carrierId;
    private String testData;
    private String category;
    private String loginUserId;
    private String loginPassword;
    private String[] testCaseList;

    public String getProduct() {
        return product;
    }
    public String getEffectDt() {
        return effectDt;
    }
    public void setProduct(String product) {
        this.product = product;
    }
    public void setEffectDt(String effectDt) {
        this.effectDt = effectDt;
    }
    public String getStateCd() {
        return stateCd;
    }
    public void setStateCd(String stateCd) {
        this.stateCd = stateCd;
    }
    public String[] getTestCaseList() {
        return testCaseList;
    }
    public void setTestCaseList(String[] testCaseList) {
        this.testCaseList = testCaseList;
    }
    public String getExecutedBy() {
        return executedBy;
    }
    public void setExecutedBy(String executedBy) {
        this.executedBy = executedBy;
    }
    public String getExecutedOn() {
        return executedOn;
    }
    public void setExecutedOn(String executedOn) {
        this.executedOn = executedOn;
    }
    public String getNextJobId() {
        return nextJobId;
    }
    public void setNextJobId(String nextJobId) {
        this.nextJobId = nextJobId;
    }
    public String getCarrierId() {
        return carrierId;
    }
    public void setCarrierId(String carrierId) {
        this.carrierId = carrierId;
    }
    public String getTestData() {
        return testData;
    }
    public void setTestData(String testData) {
        this.testData = testData;
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
