package com.ogon.apidatamapper;

public class BundleDataMapper {

    private String carrierId;
    private String stateCd;
    private String lob;
    private String bundleName;
    private String description;

    private BundledTestCases[] testCaseBundle;

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

    public BundledTestCases[] getTestCaseBundle() {
        return testCaseBundle;
    }

    public void setTestCaseBundle(BundledTestCases[] testCaseBundle) {
        this.testCaseBundle = testCaseBundle;
    }

}
