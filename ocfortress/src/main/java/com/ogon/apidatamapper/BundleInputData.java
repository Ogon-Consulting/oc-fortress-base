package com.ogon.apidatamapper;


public class BundleInputData {
    private String carrierId;
    private String product="";
    private String stateCd="";
    private String executedBy;
    private String loginUserId;
    private String loginPassword;
    private String nextJobId;
    private int[] bundleIds;

    public String getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(String carrierId) {
        this.carrierId = carrierId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getStateCd() {
        return stateCd;
    }

    public void setStateCd(String stateCd) {
        this.stateCd = stateCd;
    }

    public String getExecutedBy() {
        return executedBy;
    }

    public void setExecutedBy(String executedBy) {
        this.executedBy = executedBy;
    }

    public String getNextJobId() {
        return nextJobId;
    }

    public void setNextJobId(String nextJobId) {
        this.nextJobId = nextJobId;
    }

    public int[] getBundleIds() {
        return bundleIds;
    }

    public void setBundleIds(int[] bundleIds) {
        this.bundleIds = bundleIds;
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
